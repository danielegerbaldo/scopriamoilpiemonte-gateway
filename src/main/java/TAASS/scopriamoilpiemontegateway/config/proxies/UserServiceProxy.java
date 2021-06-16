package TAASS.scopriamoilpiemontegateway.config.proxies;

import TAASS.scopriamoilpiemontegateway.config.users.UserDestinations;
import TAASS.scopriamoilpiemontegateway.dto.Evento;
import TAASS.scopriamoilpiemontegateway.dto.Utente;
import TAASS.scopriamoilpiemontegateway.exceptions.UtenteNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.ClientResponse;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Service
public class UserServiceProxy {
    private UserDestinations userDestinations;

    private final WebClient.Builder webClientBuilder;

    public UserServiceProxy(UserDestinations userDestinations, WebClient.Builder webClientBuilder) {
        this.userDestinations = userDestinations;
        this.webClientBuilder = webClientBuilder;
    }

    public Mono<Utente> findUserById(long userId) {
        Mono<Utente> response = webClientBuilder.build()
                .get()
                .uri(userDestinations.getUserServiceUrl() + "/api/v1/utente/getUser/{id}", userId)
                .retrieve()
                .bodyToMono(Utente.class);
        return response;
    }
}
