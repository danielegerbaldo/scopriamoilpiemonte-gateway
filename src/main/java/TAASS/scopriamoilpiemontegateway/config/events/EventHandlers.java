package TAASS.scopriamoilpiemontegateway.config.events;

import TAASS.scopriamoilpiemontegateway.config.proxies.EventServiceProxy;
import TAASS.scopriamoilpiemontegateway.config.proxies.MunicipalityServiceProxy;
import TAASS.scopriamoilpiemontegateway.config.proxies.UserServiceProxy;
import TAASS.scopriamoilpiemontegateway.dto.*;
import TAASS.scopriamoilpiemontegateway.exceptions.EventNotFoundException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;
import reactor.util.function.Tuple2;
import reactor.util.function.Tuple3;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

import static org.springframework.web.reactive.function.BodyInserters.fromObject;

public class EventHandlers {
    private UserServiceProxy userService;
    private EventServiceProxy eventService;
    private MunicipalityServiceProxy municipalityService;

    public EventHandlers(UserServiceProxy userService, EventServiceProxy eventService, MunicipalityServiceProxy municipalityService) {
        this.userService = userService;
        this.eventService = eventService;
        this.municipalityService = municipalityService;
    }

    public Mono<ServerResponse> getEventDetails (ServerRequest serverRequest){
        long eventId = Long.parseLong(serverRequest.pathVariable("id"));

        AtomicReference<UserDto> utente = new AtomicReference<UserDto>();

        String token = serverRequest.headers().header(HttpHeaders.AUTHORIZATION).get(0);

        Mono<UserDto> user = userService.validateUserToken(token);

        Mono<Evento> evento = user.flatMap(us -> {
            utente.set(us);
            return eventService.findEventById(eventId, us);
        });

        Mono<Utente> proprietario = evento
                .flatMap(ev -> userService
                .findUserById(ev.getProprietario(),utente.get()));

        Mono<List<Utente>> iscritti = evento
                .flatMap(ev -> {
                    ArrayList<Long> idList = new ArrayList<Long>(ev.getIscritti());
                    return userService.findUsersByIds(idList,utente.get());
                });

        Mono<Tuple3<Evento, Utente, List<Utente>>> combined = Mono.zip(evento, proprietario, iscritti);

        Mono<EventoResponse> eventoResponse = combined.map(EventoResponse::makeEventoResponse);

        return eventoResponse.flatMap(er -> ServerResponse.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(fromObject(er)))
                .onErrorResume(EventNotFoundException.class, e -> ServerResponse.notFound().build());
    }

    public Mono<ServerResponse> getPublicEvents (ServerRequest serverRequest){

        Mono<List<Evento>> evento = eventService.getNotExpiredEvents();


        Mono<List<Comune>> comune = evento
                .flatMap(ev -> {

                    ArrayList<Long> idList = new ArrayList<Long>();

                    for (Evento e:ev) {
                        idList.add(e.getComune());
                    }

                    return municipalityService.findMunicipalityByIds(idList);
                });


        Mono<Tuple2<List<Evento>, List<Comune>>> combined = Mono.zip(evento, comune);

        Mono<List<EventoResponse>> eventoResponse = combined.map(EventoResponse::makeEventoResponse);

        return eventoResponse.flatMap(er -> ServerResponse.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(fromObject(er)))
                .onErrorResume(EventNotFoundException.class, e -> ServerResponse.notFound().build());
    }

}
