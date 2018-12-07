package service

import com.ecwid.consul.ConsulException
import com.ecwid.consul.v1.ConsulClient
import com.ecwid.consul.v1.QueryParams
import com.ecwid.consul.v1.Response
import com.ecwid.consul.v1.kv.model.GetValue
import groovy.json.JsonOutput
import groovy.json.JsonSlurper
import model.ServerConfigKV
import ratpack.jackson.Jackson
import com.google.inject.Inject
import model.ServerConfig
import ratpack.exec.Promise

class ServerConfigService {


    private final ConsulClientService consulClientService

    @Inject
    ServerConfigService(ConsulClientService consulClientService) {
        this.consulClientService = consulClientService
    }

    def getAllServerConfigs() {

        Response<List<GetValue>> allServers = consulClientService.consulClient.getKVValues("config/server")

        List<ServerConfigKV> cleanedConfigs = new ArrayList<>()

        allServers.value.each { kv ->
            ServerConfigKV svkv = new ServerConfigKV()
            svkv.key = kv.key
            svkv.configDetail = new JsonSlurper().parseText(kv.decodedValue) as Map<String, Object>
            cleanedConfigs.add(svkv)
        }

        return cleanedConfigs
    }


    def getServerConfig(String fqdn) {

        def kv = consulClientService.consulClient.getKVValue("config/server/$fqdn").value

        return new JsonSlurper().parseText(kv.getDecodedValue())
    }

    Boolean deleteServerConfig(String fqdn) {
        try {
            consulClientService.consulClient.deleteKVValue("config/server/$fqdn")
        } catch(ConsulException e) {
            return false
        }

        return true
    }

    ServerConfig createServerConfig(ServerConfig req) {
        consulClientService.consulClient.setKVValue("config/server/${req.fqdn}", JsonOutput.toJson(req).toString())

        return req
    }


    ServerConfig updateServerConfig(String fqdn) {
        return new ServerConfig(fqdn: "test.testing.com", ip: "10.1.1.1", role: "test server", ports: [80, 443])
    }


}
