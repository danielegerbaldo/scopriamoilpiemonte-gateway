package com.example.scopriamoilpiemontegateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class ScopriamoIlPiemonteGatewayApplication {

	public static void main(String[] args) {
		SpringApplication.run(ScopriamoIlPiemonteGatewayApplication.class, args);
	}

	@Bean
	public RouteLocator customRouteLocator(RouteLocatorBuilder builder)  {
		return builder.routes()
				.route("path_route", r -> r.path("/api/v1/evento/**").and().method("POST", "PUT", "DELETE","GET")
						.filters(f -> f.addResponseHeader("Cache-Control", "max-age=300")) //Keep items in cache for 5 minutes
						.uri("http://localhost:8080"))
				//.route("path_route", r -> r.path("/v1/utente").and().method("POST", "PUT", "DELETE","GET")
				//		.uri("http://servizio-utenti:8081"))
				//.route("path_route", r -> r.path("/v1/sondaggio").and().method("POST", "PUT", "DELETE","GET")
				//		.uri("http://servizio-sondaggi:8082"))
				.build();
	}

}
