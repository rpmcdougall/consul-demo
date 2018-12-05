package chain

import com.google.inject.Inject
import ratpack.groovy.handling.GroovyChainAction
import service.ServerConfigService
import static ratpack.jackson.Jackson.json


class ServerConfigApi extends GroovyChainAction {

    private final ServerConfigService serverConfigService

    @Inject
    ServerConfigApi(ServerConfigService serverConfigService) {
        this.serverConfigService = serverConfigService
    }


    @Override
    void execute() throws Exception {

        path("server") {

            byMethod {
                get {

                    render json(serverConfigService.createServerConfig())
                }
                post {
                    render json(serverConfigService.createServerConfig())
                }
            }

        }


    }


}
