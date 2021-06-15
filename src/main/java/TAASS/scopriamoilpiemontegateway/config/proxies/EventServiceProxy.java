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

    private WebClient client;

    private RestTemplate restTemplate;

    public EventServiceProxy(EventDestination eventDestinations, WebClient client) {
        this.eventDestinations = eventDestinations;
        this.client = client;
    }

    public Mono<Evento> findEventById(long eventId) {
        /*Mono<ClientResponse> response = client
                .get()
                .uri(eventDestinations.getEventServiceUrl() + "/api/v1/evento/info-evento/{id}", eventId)
                .exchange();*/

        Mono<ClientResponse> response = client
                .get()
                .uri(eventDestinations.getEventServiceUrl() + "/api/v1/evento/info-evento/{id}", eventId)
                .retrieve()
                .bodyToMono(ClientResponse.class);

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
