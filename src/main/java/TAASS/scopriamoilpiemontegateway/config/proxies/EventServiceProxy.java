package TAASS.scopriamoilpiemontegateway.config.proxies;

import TAASS.scopriamoilpiemontegateway.config.events.EventDestination;
import TAASS.scopriamoilpiemontegateway.dto.Evento;
import TAASS.scopriamoilpiemontegateway.exceptions.EventNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.ClientResponse;
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

    public Mono<Evento> findEventById(long eventId, String role) {
        /*Mono<ClientResponse> response = client
                .get()
                .uri(eventDestinations.getEventServiceUrl() + "/api/v1/evento/info-evento/{id}", eventId)
                .exchange();*/

        Mono<Evento> response = webClientBuilder.build()
                .get()
                .uri(eventDestinations.getEventServiceUrl() + "/api/v1/evento/info-evento/{id}", eventId)
                .header("X-auth-user-role",role)
                .retrieve()
                .bodyToMono(Evento.class);

        return response;

        /*return response.flatMap(resp -> {
            switch (resp.statusCode()) {
                case OK:
                    return resp.bodyToMono(Evento.class);
                case NOT_FOUND:
                    return Mono.error(new EventNotFoundException());
                default:
                    return Mono.error(new RuntimeException("Unknown" + resp.statusCode()));
            }
        });*/
    }


    /*
    TODO: cancellare?

    public Evento serialFindEventById(long eventId){
        //ci permette di fare una richiesta sincrona per avere un evento dato l'id
        String url = eventDestinations.getEventServiceUrl() + "/api/v1/evento/info-evento/{" +  eventId + "}";
        ResponseEntity<Evento> response = this.restTemplate.getForEntity(url, Evento.class);
        if(response.getStatusCode() == HttpStatus.OK) {
            return response.getBody();
        } else {
            return null;
        }

    }*/
}
