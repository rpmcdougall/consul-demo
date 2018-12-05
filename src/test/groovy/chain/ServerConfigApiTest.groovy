package chain

import com.ecwid.consul.v1.ConsulClient
import com.pszymczyk.consul.ConsulProcess
import com.pszymczyk.consul.ConsulStarterBuilder
import groovy.json.JsonBuilder
import groovy.json.JsonOutput
import groovy.json.JsonSlurper
import org.omg.CORBA.portable.Delegate
import ratpack.groovy.test.GroovyRatpackMainApplicationUnderTest
import ratpack.test.ApplicationUnderTest
import service.ConsulClientService
import spock.lang.Shared
import spock.lang.Specification
import ratpack.test.http.TestHttpClient
import ratpack.http.client.RequestSpec

class ServerConfigApiTest extends Specification {

    private ConsulProcess consul
    private ConsulClientService consulClientService
    private ConsulClient consulClient

    @Shared
    ApplicationUnderTest aut = new GroovyRatpackMainApplicationUnderTest()


    TestHttpClient client = aut.httpClient



    def setup() {
        consul = ConsulStarterBuilder.consulStarter().withHttpPort(8500).build().start()
        consulClientService = new ConsulClientService()
        consulClient = consulClientService.getConsulClient()
    }

    def cleanup() {
        consul.close()
    }


    def "will get kv pair"() {

        given:
        consulClient.setKVValue("config/server/test.testing.com", JsonOutput.toJson([fqdn:"test.testing.com",ports:[514,8080] , role:"test", ip :"10.12.1.2"]))

        when:
        def result = new JsonSlurper().parseText(client.get("/v1/config/server").body.text)
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
