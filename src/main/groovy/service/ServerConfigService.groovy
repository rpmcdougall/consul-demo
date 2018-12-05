package service

import com.ecwid.consul.v1.ConsulClient
import com.ecwid.consul.v1.kv.model.GetValue
import groovy.json.JsonOutput
import ratpack.jackson.Jackson
import com.google.inject.Inject
import model.ServerConfig
import ratpack.exec.Promise

class ServerConfigService {


    private ConsulClientService consulClientService = new ConsulClientService()

    @Inject
    ServerConfigService(ConsulClientService consulClientService) {
        this.consulClientService = consulClientService
    }

    private ConsulClient consulClient = this.consulClientService.getConsulClient(8500)


    List<ServerConfig> getAllServerConfigs() {

        List<ServerConfig> allServers = new ArrayList<>()

        return allServers
    }


    String getServerConfig(String fqdn) {

        def kv = consulClient.getKVValue(fqdn).value

        return kv.getDecodedValue()
    }

    Boolean deleteServerConfig(String fqdn) {
        return true
    }

    ServerConfig createServerConfig(ServerConfig req) {

        consulClient.setKVValue("config/server/${req.fqdn}", JsonOutput.toJson(req).toString())

        return req
    }


    ServerConfig updateServerConfig(String fqdn) {
        return new ServerConfig(fqdn: "test.testing.com", ip: "10.1.1.1", role: "test server", ports: [80, 443])
    }


}
