package TAASS.scopriamoilpiemontegateway.config.proxies;

import TAASS.scopriamoilpiemontegateway.config.users.UserDestinations;
import TAASS.scopriamoilpiemontegateway.dto.Utente;
import TAASS.scopriamoilpiemontegateway.exceptions.UtenteNotFoundException;
import org.springframework.web.reactive.function.client.ClientResponse;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
public class UserServiceProxy {
    private UserDestinations userDestinations;

    private WebClient client;

    public UserServiceProxy(UserDestinations userDestinations, WebClient client) {
        this.userDestinations = userDestinations;
        this.client = client;
    }

    public Mono<Utente> findUserById(String userId) {
        Mono<ClientResponse> response = client
                .get()
                .uri(userDestinations.getUserServiceUrl() + "/api/v1/utente/{id}", userId)
                .exchange();
        return response.flatMap(resp -> {
            switch (resp.statusCode()) {
                case OK:
                    return resp.bodyToMono(Utente.class);
                case NOT_FOUND:
                    return Mono.error(new UtenteNotFoundException());
                default:
                    return Mono.error(new RuntimeException("Unknown" + resp.statusCode()));
            }
        });
    }
}
