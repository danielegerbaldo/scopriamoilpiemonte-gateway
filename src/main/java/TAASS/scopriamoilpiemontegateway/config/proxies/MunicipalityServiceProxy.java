package TAASS.scopriamoilpiemontegateway.config.proxies;

import TAASS.scopriamoilpiemontegateway.config.municipalities.MunicipalityDestination;
import TAASS.scopriamoilpiemontegateway.dto.Comune;
import TAASS.scopriamoilpiemontegateway.dto.UserDto;
import TAASS.scopriamoilpiemontegateway.dto.Utente;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.List;

@Service
public class MunicipalityServiceProxy {
    private MunicipalityDestination municipalityDestination;

    private final WebClient.Builder webClientBuilder;

    private RestTemplate restTemplate;

    public MunicipalityServiceProxy(MunicipalityDestination municipalityDestination, WebClient.Builder webClientBuilder) {
        this.municipalityDestination = municipalityDestination;
        this.webClientBuilder = webClientBuilder;
    }

    public Mono<Comune> findMunicipalityById(long municipalityId) {
        Mono<Comune> response = webClientBuilder.build()
                .get()
                .uri(municipalityDestination.getMunicipalityServiceUrl() + "/api/v1/comune/info-comune/{id}", municipalityId)
                .retrieve()
                /*.onStatus(
                        status -> status.value() == 404,
                        clientResponse -> Mono.empty()
                )*/
                .bodyToMono(Comune.class);
        return response;
    }

    public Mono<List<Comune>> findMunicipalityByIds(List<Long> municipalityIds) {
        Mono<List<Comune>> response = webClientBuilder.build()
                .post()
                .uri(municipalityDestination.getMunicipalityServiceUrl() + "/api/v1/comune/info-comune/getComuniByIdList")
                .body(Mono.just(municipalityIds), new ParameterizedTypeReference<>() {
                })
                .retrieve()
                /*.onStatus(
                        status -> status.value() == 404,
                        clientResponse -> Mono.empty()
                )*/
                .bodyToMono(new ParameterizedTypeReference<>() {
                });
        return response;
    }
}
