package chain

import com.ecwid.consul.v1.ConsulClient
import com.pszymczyk.consul.ConsulProcess
import com.pszymczyk.consul.ConsulStarterBuilder
import groovy.json.JsonBuilder
import groovy.json.JsonOutput
import groovy.json.JsonSlurper
import ratpack.groovy.test.GroovyRatpackMainApplicationUnderTest
import ratpack.test.ApplicationUnderTest
import service.ConsulClientService
import spock.lang.AutoCleanup
import spock.lang.Shared
import spock.lang.Specification
import ratpack.test.http.TestHttpClient



class ServerConfigApiTest extends  Specification{

    // Declare objects created for each test
    ConsulProcess consul
    ApplicationUnderTest aut
    TestHttpClient client
    ConsulClient testConsulClient

    def setup() {

        //Create new app for each test so consul client port is reset
        aut = new GroovyRatpackMainApplicationUnderTest()
        client = aut.httpClient


        //Build consul embedded server and client
        consul = ConsulStarterBuilder.consulStarter().build().start()
        System.properties["consul.clientPort"] = consul.httpPort
        println System.properties["consul.clientPort"]
        testConsulClient = new ConsulClient("localhost", consul.httpPort)
    }

    def cleanup() {
        consul.close()
    }


    def "will get kv pair"() {

        given:
        println "running on port ${System.properties['consul.clientPort']}"

        testConsulClient.setKVValue("config/server/test.testing.com", JsonOutput.toJson([fqdn:"test.testing.com",ports:[514,8080] , role:"test", ip :"10.12.1.2"]))

        when:
        def result = new JsonSlurper().parseText(client.get("/v1/config/server?fqdn=test.testing.com").body.text)
        then:

        assert result.fqdn == "test.testing.com"
    }

    def "will create kv pair"() {

        given:
        Map obj = [fqdn:"test-create.testing.com",ports:[514,8080] , role:"test", ip :"10.12.1.2"]
        def req = new JsonBuilder(obj).toPrettyString()

        when:

        client.requestSpec {
            it.body.text req
            it.body.type"application/json"
        }

        def res = new JsonSlurper().parseText(client.post("/v1/config/server").body.text)


        then:

        assert res.fqdn == "test-create.testing.com"
    }


}
