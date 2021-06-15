package TAASS.scopriamoilpiemontegateway.config.events;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;

import javax.validation.constraints.NotNull;

//@PropertySource("classpath:urlprops.properties")
@ConfigurationProperties(prefix = "event")
public class EventDestination {
    @NotNull
    private String eventServiceUrl;

    public String getEventServiceUrl() {
        System.out.println("SYSTEM PROPERTY: " + eventServiceUrl);
        return eventServiceUrl;
    }

    public void setEventServiceUrl(String eventServiceUrl) {
        this.eventServiceUrl = eventServiceUrl;
    }
}

