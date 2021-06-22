package TAASS.scopriamoilpiemontegateway.config.proxies;

import TAASS.scopriamoilpiemontegateway.config.events.EventDestination;
import TAASS.scopriamoilpiemontegateway.dto.Evento;
import TAASS.scopriamoilpiemontegateway.dto.UserDto;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Service
public class EventServiceProxy {
    private EventDestination eventDestinations;
    private final WebClient.Builder webClientBuilder;

    private RestTemplate restTemplate;

    public EventServiceProxy(EventDestination eventDestinations, WebClient.Builder webClientBuilder) {
        this.eventDestinations = eventDestinations;
        this.webClientBuilder = webClientBuilder;
    }

    public Mono<Evento> findEventById(long eventId, UserDto user) {
        Mono<Evento> response = webClientBuilder.build()
                .get()
                .uri(eventDestinations.getEventServiceUrl() + "/api/v1/evento/info-evento/{id}", eventId)
                .header("X-auth-user-role",user.getRole())
                .header("X-auth-user-id",String.valueOf(user.getId()))
                .retrieve()
                .bodyToMono(Evento.class);

        return response;

    }

}
