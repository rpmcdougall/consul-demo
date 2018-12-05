package service

import model.ServerConfig

class ServerConfigService {


    List<ServerConfig> getAllServerConfigs() {



        List<ServerConfig> allServers = new ArrayList<>()

        return allServers
    }


    ServerConfig getServerConfig(String fqdn) {


        return new ServerConfig(fqdn: "test.testing.com", ip: "10.1.1.1", role: "test server", ports: [80, 443])
    }

    Boolean deleteServerConfig(String fqdn) {
        return true
    }

    ServerConfig createServerConfig() {
        return new ServerConfig(fqdn: "test.testing.com", ip: "10.1.1.1", role: "test server", ports: [80, 443])
    }


    ServerConfig updateServerConfig(String fqdn) {
        return new ServerConfig(fqdn: "test.testing.com", ip: "10.1.1.1", role: "test server", ports: [80, 443])
    }


}
