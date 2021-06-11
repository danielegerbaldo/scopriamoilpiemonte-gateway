package TAASS.scopriamoilpiemontegateway.config.users;


import org.springframework.boot.context.properties.EnableConfigurationProperties;
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
   /* @Bean
    public RouteLocator consumerProxyRouting(RouteLocatorBuilder builder, EventDestinations eventDestinations) {
        return builder.routes()
                //.route(r -> r.path("/consumers").and().method("POST").uri(eventDestinations.getConsumerServiceUrl()))
                //.route(r -> r.path("/consumers").and().method("PUT").uri(eventDestinations.getConsumerServiceUrl()))
                .route(r -> r.path("/api/v1/evento/**")
                        .and().method("POST", "PUT", "DELETE","GET")
                        .uri(eventDestinations.getEventServiceUrl())
                .build();
    }*/

    @Bean
    public RouterFunction<ServerResponse> eventHandlerRouting(EventHandlers eventHandlers) {
        return RouterFunctions.route(GET("/info-evento/{id}"), eventHandlers::getOrderDetails);
    }

    @Bean
    public EventHandlers orderHandlers(EventServiceProxy eventService, UserService userService,
                                       MunicipalitiesService municipalitiesService) {
        return new EventHandlers(eventService, userService, municipalitiesService);
    }

    @Bean
    public WebClient webClient() {
        return WebClient.create();
    }
}

