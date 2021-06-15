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
    public RouteLocator userProxyRouting(RouteLocatorBuilder builder, UserDestinations userDestinations) {
        return builder.routes()
                //.route(r -> r.path("/consumers").and().method("POST").uri(eventDestinations.getConsumerServiceUrl()))
                //.route(r -> r.path("/consumers").and().method("PUT").uri(eventDestinations.getConsumerServiceUrl()))
                .route(r -> r.path("/api/v1/utente/**")
                        .and().method("POST", "PUT", "DELETE","GET")
                        .uri(userDestinations.getUserServiceUrl()))
                .build();
    }

    @Bean
    public RouterFunction<ServerResponse> userHandlerRouting(UserHandlers userHandlers) {
        return RouterFunctions.route(GET("/info-utente/{id}"), userHandlers::getUserDetails);
    }

    @Bean
    public UserHandlers userHandlers(UserServiceProxy userService, MunicipalityServiceProxy municipalitiesService) {
        return new UserHandlers(userService, municipalitiesService);
    }

}

