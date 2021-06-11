package TAASS.scopriamoilpiemontegateway.config.events;

import org.springframework.boot.context.properties.ConfigurationProperties;

import javax.validation.constraints.NotNull;

@ConfigurationProperties(prefix = "event.destinations")
public class EventDestination {
    @NotNull
    private String eventServiceUrl;

    public String getEventServiceUrl() {
        return eventServiceUrl;
    }

    public void setEventServiceUrl(String eventServiceUrl) {
        this.eventServiceUrl = eventServiceUrl;
    }
}

