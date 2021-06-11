package TAASS.scopriamoilpiemontegateway.config.events;

import TAASS.scopriamoilpiemontegateway.config.proxies.EventServiceProxy;
import TAASS.scopriamoilpiemontegateway.config.proxies.UserServiceProxy;
import TAASS.scopriamoilpiemontegateway.dto.Evento;
import TAASS.scopriamoilpiemontegateway.dto.Utente;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;
import reactor.util.function.Tuple4;

import java.util.Optional;

import static org.springframework.web.reactive.function.BodyInserters.fromObject;

public class EventHandlers {
    private UserServiceProxy userService;
    private EventServiceProxy eventService;


    public EventHandlers(UserServiceProxy userService,
                         EventServiceProxy eventService
                         ) {
        this.userService = userService;
        this.eventService = eventService;
    }

    public Mono<ServerResponse> getEventDetails(ServerRequest serverRequest) {
        String eventId = serverRequest.pathVariable("eventId");

        Mono<Evento> evento = eventService.findEventById(eventId);

        Mono<Optional<Utente>> utente = userService
                .findUserById(evento.toFuture().get().getProprietario())
                .map(Optional::of)
                .onErrorReturn(Optional.empty());


        Mono<Tuple4<Evento, Optional<Utente>, Optional<BillInfo>>> combined =
                Mono.zip(evento, utente);

        Mono<Evento> orderDetails = combined.map(Evento::makeEventoDetails);

        return orderDetails.flatMap(od -> ServerResponse.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(fromObject(od)))
                .onErrorResume(OrderNotFoundException.class, e -> ServerResponse.notFound().build());
    }

}
