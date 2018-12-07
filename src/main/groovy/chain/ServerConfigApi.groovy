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

        //Server Root
        path("server") {
            byMethod {
                get {
                    if(request.queryParams.containsKey("fqdn")) {
                        render json(serverConfigService.getServerConfig(request.queryParams["fqdn"]))
                    }

                    render json(serverConfigService.getAllServerConfigs())
                }
                post {


                    Promise<ServerConfig> req =  parse(Jackson.fromJson(ServerConfig))
                    req.then { srv ->
                        render json(serverConfigService.createServerConfig(srv))

                    }
                }
            }

        }

        //fqdn Path Token
        path("server?:fqdn") {
            String fqdn = request.getQueryParams()['fqdn']
            byMethod {
                get {
                    render json(serverConfigService.getServerConfig(fqdn))
                }
            }

        }


    }


}
