package chain

import com.google.inject.Inject
import groovy.json.JsonSlurper
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

                delete {
                    if (request.queryParams.containsKey("fqdn")) {
                        Boolean deleteStatus = serverConfigService.deleteServerConfig(request.queryParams["fqdn"])
                        if (!deleteStatus) {
                            response.status(400).send("application/json",  '{"error": "Failed to delete KV")')

                        }
                    }
                    else
                        response.status(400).send("application/json",  '{"error": "Missing required FQDN as query parameter")')
                }

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


    }


}
