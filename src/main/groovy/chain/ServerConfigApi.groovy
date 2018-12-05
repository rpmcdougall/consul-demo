package chain

import com.google.inject.Inject
import ratpack.groovy.handling.GroovyChainAction
import service.ServerConfigService

class ServerConfigApi extends GroovyChainAction {

    private final ServerConfigService serverConfigService

    @Inject
    ServerConfigApi(ServerConfigService serverConfigService) {
        this.serverConfigService = serverConfigService
    }


    @Override
    void execute() throws Exception {

        path("/config") {

            byMethod {
                get {
                    serverConfigService.createServerConfig()
                }
                post {
                    serverConfigService.createServerConfig()
                }
            }

        }


    }


}
