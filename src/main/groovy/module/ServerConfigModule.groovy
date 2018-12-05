package module

import chain.ServerConfigApi
import com.google.inject.AbstractModule;
import com.google.inject.Scopes
import service.ServerConfigService;

 class ServerConfigModule extends AbstractModule {

     @Override
     protected void configure() {
         bind(ServerConfigService.class).in(Scopes.SINGLETON)
         bind(ServerConfigApi).in(Scopes.SINGLETON)
     }

 }