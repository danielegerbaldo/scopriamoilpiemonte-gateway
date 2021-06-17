package TAASS.scopriamoilpiemontegateway.config.users;

import TAASS.scopriamoilpiemontegateway.config.proxies.MunicipalityServiceProxy;
import TAASS.scopriamoilpiemontegateway.config.proxies.UserServiceProxy;
import TAASS.scopriamoilpiemontegateway.dto.*;
import TAASS.scopriamoilpiemontegateway.exceptions.EventNotFoundException;
import TAASS.scopriamoilpiemontegateway.exceptions.UtenteNotFoundException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;
import reactor.util.function.Tuple2;
import reactor.util.function.Tuple3;

import java.util.Optional;
import java.util.concurrent.atomic.AtomicReference;

import static org.springframework.web.reactive.function.BodyInserters.fromObject;

public class UserHandlers {

    private UserServiceProxy userService;
    private MunicipalityServiceProxy municipalityService;

    public UserHandlers(UserServiceProxy userService, MunicipalityServiceProxy municipalityService) {
        this.userService = userService;
        this.municipalityService = municipalityService;
    }


    public Mono<ServerResponse> getUserDetails(ServerRequest serverRequest) {
        long userId = Long.parseLong(serverRequest.pathVariable("id"));
        AtomicReference<String> role = new AtomicReference<String>();

        String token = serverRequest.headers().header(HttpHeaders.AUTHORIZATION).get(0);

        Mono<UserDto> user = userService.validateUserToken(token);

        Mono<Utente> utente = user.flatMap(us -> {
            role.set(us.getRole());
            return userService.findUserById(userId,us.getRole());
        });


        Mono<Comune> comuneRes = utente
                .flatMap(
                        us -> municipalityService
                                .findMunicipalityById(us.getComuneResidenza(),role.get())
                );

        Mono<Optional<Comune>> comuneDip = utente
                .flatMap(
                        us -> municipalityService
                                .findMunicipalityById(us.getDipendenteDiComune(),role.get())
                                .map(Optional::of)
                                .onErrorReturn(Optional.empty())
                );


        Mono<Tuple3<Utente, Comune, Optional<Comune>>> combined = Mono.zip(utente, comuneRes, comuneDip);

        Mono<UtenteResponse> utenteResponse = combined.map(UtenteResponse::makeUtenteResponse);

        return utenteResponse.flatMap(er -> ServerResponse.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(fromObject(er)))
                .onErrorResume(UtenteNotFoundException.class, e -> ServerResponse.notFound().build());
    }
}
