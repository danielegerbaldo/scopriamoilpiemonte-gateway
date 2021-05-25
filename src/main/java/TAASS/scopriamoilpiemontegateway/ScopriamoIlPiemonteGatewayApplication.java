package TAASS.scopriamoilpiemontegateway;

import TAASS.scopriamoilpiemontegateway.filters.AuthFilter;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;

@EnableDiscoveryClient
@SpringBootApplication
public class ScopriamoIlPiemonteGatewayApplication {

	public static void main(String[] args) {
		SpringApplication.run(ScopriamoIlPiemonteGatewayApplication.class, args);
	}

	@Bean
	public RouteLocator customRouteLocator(RouteLocatorBuilder builder)  {
		return builder.routes()
				//.route("registry-service", r -> r.path("/api/v1/signUp").and().method("POST", "PUT", "DELETE","GET")
				//		.uri("lb://USER-SERVICE"))
				//.route("event-service", r -> r.path("/api/v1/evento/**").and().method("POST", "PUT", "DELETE","GET")
				//		.filters(f -> f.addResponseHeader("Cache-Control", "max-age=300")) //Keep items in cache for 5 minutes
				//		.uri("lb://EVENT-SERVICE"))
				//.route("user-service", r -> r.path("/api/v1/utente/**").and().method("POST", "PUT", "DELETE","GET")
				//		.filters(f -> f.filter(AuthFilter.class,0))
				//		.uri("lb://USER-SERVICE"))
				//.route("municipality-service", r -> r.path("/api/v1/comune/**").and().method("POST", "PUT", "DELETE","GET")
				//		.uri("lb://MUNICIPALITY-SERVICE"))
				//.route("registry-service", r -> r.path("").and().method("POST", "PUT", "DELETE","GET")
				//		.uri("http://servizio-registry:8010"))
				.build();
	}

}
