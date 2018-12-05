package chain

import com.google.inject.Inject
import model.ServerConfig
import ratpack.exec.Promise
import ratpack.groovy.handling.GroovyChainAction
import ratpack.jackson.Jackson
import service.ServerConfigService
import static ratpack.jackson.Jackson.json
import static ratpack.jackson.Jackson.jsonNode


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
                    render serverConfigService.getServerConfig("config/server/test.testing.com")
                }
                post {


                    Promise<ServerConfig> req =  parse(Jackson.fromJson(ServerConfig))
                    req.then { srv ->
                        render json(serverConfigService.createServerConfig(srv))

                    }
                }
            }

        }


    }


}
