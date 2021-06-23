package TAASS.scopriamoilpiemontegateway.config.proxies;

import TAASS.scopriamoilpiemontegateway.config.municipalities.MunicipalityDestination;
import TAASS.scopriamoilpiemontegateway.dto.Comune;
import TAASS.scopriamoilpiemontegateway.dto.UserDto;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Service
public class MunicipalityServiceProxy {
    private MunicipalityDestination municipalityDestination;

    private final WebClient.Builder webClientBuilder;

    private RestTemplate restTemplate;

    public MunicipalityServiceProxy(MunicipalityDestination municipalityDestination, WebClient.Builder webClientBuilder) {
        this.municipalityDestination = municipalityDestination;
        this.webClientBuilder = webClientBuilder;
    }

    public Mono<Comune> findMunicipalityById(long municipalityId, UserDto user) {
        Mono<Comune> response = webClientBuilder.build()
                .get()
                .uri(municipalityDestination.getMunicipalityServiceUrl() + "/api/v1/comune/info-comune/{id}", municipalityId)
                .header("X-auth-user-role",user.getRole())
                .header("X-auth-user-id",String.valueOf(user.getId()))
                .retrieve()
                /*.onStatus(
                        status -> status.value() == 404,
                        clientResponse -> Mono.empty()
                )*/
                .bodyToMono(Comune.class);
        return response;
    }
}
