package service

import com.ecwid.consul.v1.ConsulClient
import com.google.inject.Inject

class ConsulClientService {

    Integer port

    ConsulClient getConsulClient(Integer port) {
        ConsulClient consulClient = new ConsulClient("localhost", port )
        return consulClient
    }

    void setConsulClient(ConsulClient consulClient) {
        this.consulClient = consulClient
    }
}
