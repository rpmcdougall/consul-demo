package service

import com.ecwid.consul.v1.ConsulClient
import com.google.inject.Inject

class ConsulClientService {

    ConsulClient getConsulClient() {
        Integer clientPort = System.properties["consul.clientPort"] as Integer ?: 8500
        ConsulClient consulClient = new ConsulClient("localhost", clientPort)
        return consulClient
    }

    void setConsulClient(ConsulClient consulClient) {
        this.consulClient = consulClient
    }
}
