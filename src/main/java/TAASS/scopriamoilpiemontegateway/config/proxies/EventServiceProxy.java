package TAASS.scopriamoilpiemontegateway.config.proxies;

import TAASS.scopriamoilpiemontegateway.config.events.EventDestination;
import TAASS.scopriamoilpiemontegateway.dto.Evento;
import TAASS.scopriamoilpiemontegateway.exceptions.EventNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.ClientResponse;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Service
public class EventServiceProxy {
    private EventDestination eventDestinations;

    private WebClient client;

    public EventServiceProxy(EventDestination eventDestinations, WebClient client) {
        this.eventDestinations = eventDestinations;
        this.client = client;
    }

    public Mono<Evento> findEventById(String eventId) {
        Mono<ClientResponse> response = client
                .get()
                .uri(eventDestinations.getEventServiceUrl() + "/api/v1/evento/info-evento/{id}", eventId)
                .exchange();
        return response.flatMap(resp -> {
            switch (resp.statusCode()) {
                case OK:
                    return resp.bodyToMono(Evento.class);
                case NOT_FOUND:
                    return Mono.error(new EventNotFoundException());
                default:
                    return Mono.error(new RuntimeException("Unknown" + resp.statusCode()));
            }
        });
    }
}
