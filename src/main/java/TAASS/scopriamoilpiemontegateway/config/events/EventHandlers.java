package TAASS.scopriamoilpiemontegateway.config.events;

import TAASS.scopriamoilpiemontegateway.config.proxies.EventServiceProxy;
import TAASS.scopriamoilpiemontegateway.config.proxies.UserServiceProxy;
import TAASS.scopriamoilpiemontegateway.dto.Evento;
import TAASS.scopriamoilpiemontegateway.dto.EventoResponse;
import TAASS.scopriamoilpiemontegateway.dto.UserDto;
import TAASS.scopriamoilpiemontegateway.dto.Utente;
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


    public EventHandlers(UserServiceProxy userService, EventServiceProxy eventService) {
        this.userService = userService;
        this.eventService = eventService;
    }

    public Mono<ServerResponse> getEventDetails (ServerRequest serverRequest){
        long eventId = Long.parseLong(serverRequest.pathVariable("id"));
        AtomicReference<String> role = new AtomicReference<String>();

        String token = serverRequest.headers().header(HttpHeaders.AUTHORIZATION).get(0);

        Mono<UserDto> user = userService.validateUserToken(token);

        Mono<Evento> evento = user.flatMap(us -> {
            role.set(us.getRole());
            return eventService.findEventById(eventId, us.getRole());
        });

        Mono<Utente> proprietario = evento
                .flatMap(ev -> userService
                .findUserById(ev.getProprietario(),role.get()));

        Mono<List<Utente>> iscritti = evento
                .flatMap(ev -> {
                    ArrayList<Long> idList = new ArrayList<Long>(ev.getIscritti());
                    return userService.findUsersByIds(idList,role.get());
                });

        Mono<Tuple3<Evento, Utente, List<Utente>>> combined = Mono.zip(evento, proprietario, iscritti);

        Mono<EventoResponse> eventoResponse = combined.map(EventoResponse::makeEventoResponse);

        return eventoResponse.flatMap(er -> ServerResponse.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(fromObject(er)))
                .onErrorResume(EventNotFoundException.class, e -> ServerResponse.notFound().build());
    }

    public Mono<ServerResponse> getEventDetails2(ServerRequest serverRequest) {
        //long eventId = Long.parseLong(serverRequest.pathVariable("eventId"));

        /*Mono<Evento> evento = eventService.findEventById(eventId);
        Mono<Utente> proprietario;

        Mono<Optional<Utente>> utente = userService
                .findUserById(evento.toFuture().get().getProprietario())
                .map(Optional::of)
                .onErrorReturn(Optional.empty());

        evento.doOnSuccess(new Consumer<Evento>() {
            @Override
            public void accept(Evento evento) {
                Mono<Utente> proprietario = userService.findUserById(evento.getProprietario());

            }
        });*/

        /*Evento evento = eventService.serialFindEventById(eventId);


        Mono<Optional<Utente>> proprietario = userService
                .findUserById(evento.getProprietario())
                .map(Optional::of)
                .onErrorReturn(Optional.empty());*/

        /*proprietario.doOnSuccess(new Consumer<Optional<Utente>>() {
            @Override
            public void accept(Optional<Utente> utente) {
                if(utente.isPresent()){
                    eventoResponse.setProprietario(utente.get());
                }
            }
        });*/

        /*Tuple2<Evento, Utente> tuple2;

        Mono<Tuple2<Evento, Utente>> combined =  Mono.zip(evento, proprietario);

        Mono<Optional<EventoResponse>> eventoResponse = proprietario.map(EventoResponse::makeEventoResponse);

        //restituisce un mono una volta finito il mapping
        return eventoResponse.flatMap(er -> ServerResponse.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(fromObject(er)))
                .onErrorResume(EventNotFoundException.class, e -> ServerResponse.notFound().build());*/

        /*Mono<Tuple4<Evento, Optional<Utente>, Optional<BillInfo>>> combined =
                Mono.zip(evento, utente);

        Mono<Evento> orderDetails = combined.map(Evento::makeEventoDetails);

        return orderDetails.flatMap(od -> ServerResponse.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(fromObject(od)))
                .onErrorResume(OrderNotFoundException.class, e -> ServerResponse.notFound().build());*/
        return null;
    }



}
