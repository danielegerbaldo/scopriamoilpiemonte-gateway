package TAASS.scopriamoilpiemontegateway.config.events;


import TAASS.scopriamoilpiemontegateway.config.proxies.EventServiceProxy;
import TAASS.scopriamoilpiemontegateway.config.proxies.UserServiceProxy;
import io.netty.resolver.DefaultAddressResolverGroup;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.reactive.ClientHttpConnector;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.netty.http.client.HttpClient;

import static org.springframework.web.reactive.function.server.RequestPredicates.GET;


@Configuration
@EnableConfigurationProperties(EventDestination.class)
public class EventConfiguration {
    /*@Bean
    public RouteLocator eventProxyRouting(RouteLocatorBuilder builder, EventDestination eventDestinations) {
        return builder.routes()
                //.route(r -> r.path("/consumers").and().method("POST").uri(eventDestinations.getConsumerServiceUrl()))
                //.route(r -> r.path("/consumers").and().method("PUT").uri(eventDestinations.getConsumerServiceUrl()))
                .route(r -> r.path("/api/v1/evento/**")
                        .and().method("POST", "PUT", "DELETE","GET")
                        .uri(eventDestinations.getEventServiceUrl()))
                .build();
    }*/

    @Bean
    public RouterFunction<ServerResponse> eventHandlerRouting(EventHandlers eventHandlers) {
        return RouterFunctions.route(GET("/api/v1/evento/info-evento/{id}"), eventHandlers::getEventDetails);
    }

    @Bean
    public EventHandlers eventHandlers(EventServiceProxy eventService, UserServiceProxy userService) {
        return new EventHandlers(userService, eventService);
    }

    @Bean
    @LoadBalanced
    public WebClient webClient() {
        HttpClient client =  HttpClient.create().resolver(DefaultAddressResolverGroup.INSTANCE);
        ClientHttpConnector connector = new ReactorClientHttpConnector(client);
        return WebClient.builder().clientConnector(connector).build();
    }
}

