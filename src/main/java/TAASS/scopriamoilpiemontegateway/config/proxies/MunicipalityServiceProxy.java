package TAASS.scopriamoilpiemontegateway.config.proxies;

import TAASS.scopriamoilpiemontegateway.config.events.EventDestination;
import TAASS.scopriamoilpiemontegateway.config.municipality.MunicipalityDestination;
import TAASS.scopriamoilpiemontegateway.dto.Comune;
import TAASS.scopriamoilpiemontegateway.dto.Evento;
import TAASS.scopriamoilpiemontegateway.exceptions.ComuneNotFoundException;
import TAASS.scopriamoilpiemontegateway.exceptions.EventNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.ClientResponse;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Service
public class MunicipalityServiceProxy {
    private MunicipalityDestination municipalityDestination;

    private WebClient client;

    private RestTemplate restTemplate;

    public MunicipalityServiceProxy(MunicipalityDestination municipalityDestination, WebClient client) {
        this.municipalityDestination = municipalityDestination;
        this.client = client;
    }

    public Mono<Comune> findMunicipalityById(long municipalityId) {
        Mono<ClientResponse> response = client
                .get()
                .uri(municipalityDestination.getMunicipalityServiceUrl() + "/api/v1/evento/info-evento/{id}", municipalityId)
                .exchange();
        return response.flatMap(resp -> {
            switch (resp.statusCode()) {
                case OK:
                    return resp.bodyToMono(Comune.class);
                case NOT_FOUND:
                    return Mono.error(new ComuneNotFoundException());
                default:
                    return Mono.error(new RuntimeException("Unknown" + resp.statusCode()));
            }
        });
    }
}
