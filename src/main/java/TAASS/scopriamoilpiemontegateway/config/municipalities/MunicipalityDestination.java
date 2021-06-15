package TAASS.scopriamoilpiemontegateway.config.municipalities;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import javax.validation.constraints.NotNull;

//@PropertySource("classpath:urlprops.properties")
@ConfigurationProperties(prefix = "municipality")
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
