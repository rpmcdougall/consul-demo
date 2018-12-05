package service

import com.ecwid.consul.v1.ConsulClient
import com.google.inject.Inject

class ConsulClientService {

    ConsulClient getConsulClient() {
        ConsulClient consulClient = new ConsulClient("localhost" )
        return consulClient
    }

    void setConsulClient(ConsulClient consulClient) {
        this.consulClient = consulClient
    }
}
