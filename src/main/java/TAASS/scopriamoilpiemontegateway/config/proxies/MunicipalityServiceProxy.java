package TAASS.scopriamoilpiemontegateway.config.proxies;

import TAASS.scopriamoilpiemontegateway.config.municipalities.MunicipalityDestination;
import TAASS.scopriamoilpiemontegateway.dto.Comune;
import TAASS.scopriamoilpiemontegateway.dto.Utente;
import TAASS.scopriamoilpiemontegateway.exceptions.ComuneNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.ClientResponse;
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

    public Mono<Comune> findMunicipalityById(long municipalityId, String role) {
        Mono<Comune> response = webClientBuilder.build()
                .get()
                .uri(municipalityDestination.getMunicipalityServiceUrl() + "/api/v1/comune/info-comune/{id}", municipalityId)
                .header("X-auth-user-role", role)
                .retrieve()
                .bodyToMono(Comune.class);
        return response;
    }
}
