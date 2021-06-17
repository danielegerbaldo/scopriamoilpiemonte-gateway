package TAASS.scopriamoilpiemontegateway.config.proxies;

import TAASS.scopriamoilpiemontegateway.config.users.UserDestinations;
import TAASS.scopriamoilpiemontegateway.dto.Evento;
import TAASS.scopriamoilpiemontegateway.dto.UserDto;
import TAASS.scopriamoilpiemontegateway.dto.Utente;
import TAASS.scopriamoilpiemontegateway.exceptions.UtenteNotFoundException;
import org.springframework.http.HttpHeaders;
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

    public Mono<Utente> findUserById(long userId, String role) {
        Mono<Utente> response = webClientBuilder.build()
                .get()
                .uri(userDestinations.getUserServiceUrl() + "/api/v1/utente/getUser/{id}", userId)
                .header("X-auth-user-role",role)
                .retrieve()
                .bodyToMono(Utente.class);
        return response;
    }


    public Mono<UserDto> validateUserToken(String token) {

        if (token!=null && token.equals("")) {
            throw new RuntimeException("Missing authorization information");
        }

        String[] parts = token.split(" ");

        if (parts.length != 2 || !"Bearer".equals(parts[0])) {
            throw new RuntimeException("Incorrect authorization structure");
        }

        Mono<UserDto> response = webClientBuilder.build()
                .get()
                .uri("lb://USER-SERVICE/api/v1/validateToken?token=" + parts[1])
                .retrieve().bodyToMono(UserDto.class);

        return response;
    }



}
