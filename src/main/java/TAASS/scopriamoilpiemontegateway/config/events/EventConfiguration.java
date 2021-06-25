package TAASS.scopriamoilpiemontegateway.config.events;


import TAASS.scopriamoilpiemontegateway.config.proxies.EventServiceProxy;
import TAASS.scopriamoilpiemontegateway.config.proxies.MunicipalityServiceProxy;
import TAASS.scopriamoilpiemontegateway.config.proxies.UserServiceProxy;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.web.reactive.function.server.RequestPredicates.GET;


@Configuration
@EnableConfigurationProperties(EventDestination.class)
public class EventConfiguration {

    @Bean
    public RouterFunction<ServerResponse> eventHandlerRouting(WebClient.Builder webClientBuilder, EventHandlers eventHandlers) {
        return RouterFunctions.route(GET("/api/v1/evento-composed/info-evento/{id}"), eventHandlers::getEventDetails)
                .andRoute(GET("/api/v1/public/evento-composed/getAllEvent"), eventHandlers::getPublicEvents);
    }


    @Bean
    public EventHandlers eventHandlers(EventServiceProxy eventService, UserServiceProxy userService, MunicipalityServiceProxy municipalityService) {
        return new EventHandlers(userService, eventService, municipalityService);
    }

    @Bean
    @LoadBalanced
    public WebClient webClient() {
        return WebClient.create();
    }
}

