package TAASS.scopriamoilpiemontegateway.config.users;


import TAASS.scopriamoilpiemontegateway.config.events.EventHandlers;
import TAASS.scopriamoilpiemontegateway.config.proxies.EventServiceProxy;
import TAASS.scopriamoilpiemontegateway.config.proxies.MunicipalityServiceProxy;
import TAASS.scopriamoilpiemontegateway.config.proxies.UserServiceProxy;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.web.reactive.function.server.RequestPredicates.GET;


@Configuration
@EnableConfigurationProperties(UserDestinations.class)
public class UserConfiguration {

    @Bean
    public RouterFunction<ServerResponse> userHandlerRouting(UserHandlers userHandlers) {
        return RouterFunctions.route(GET("/api/v1/utente-composed/info-utente/{id}"), userHandlers::getUserDetails);
    }

    @Bean
    public UserHandlers userHandlers(UserServiceProxy userService, MunicipalityServiceProxy municipalitiesService) {
        return new UserHandlers(userService, municipalitiesService);
    }

}

