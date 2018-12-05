import chain.ServerConfigApi
import module.ServerConfigModule

import static ratpack.groovy.Groovy.ratpack

ratpack {
  bindings {
    module ServerConfigModule
  }

  handlers {

      get{
          render "ok"
      }

      prefix("v1/config") {
          all chain(registry.get(ServerConfigApi))
      }
  }
}
