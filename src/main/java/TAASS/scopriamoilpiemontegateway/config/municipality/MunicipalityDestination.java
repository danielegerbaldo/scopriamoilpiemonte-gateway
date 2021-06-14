package TAASS.scopriamoilpiemontegateway.config.municipality;

import org.springframework.boot.context.properties.ConfigurationProperties;

import javax.validation.constraints.NotNull;
@ConfigurationProperties(prefix = "municipality.destinations")

public class MunicipalityDestination {

    @NotNull
    private String municipalityServiceUrl;

    public String getMunicipalityServiceUrl() {
        return municipalityServiceUrl;
    }

    public void setMunicipalityServiceUrl(String municipalityServiceUrl) {
        this.municipalityServiceUrl = municipalityServiceUrl;
    }
}
