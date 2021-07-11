package TAASS.scopriamoilpiemontegateway.config.events;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;

import javax.validation.constraints.NotNull;

@ConfigurationProperties(prefix = "event")
public class EventDestination {
    @NotNull
    private String eventServiceUrl;

    public String getEventServiceUrl() { return eventServiceUrl; }

    public void setEventServiceUrl(String eventServiceUrl) {
        this.eventServiceUrl = eventServiceUrl;
    }
}

