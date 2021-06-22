package TAASS.scopriamoilpiemontegateway.config.users;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import javax.validation.constraints.NotNull;

//@PropertySource("classpath:urlprops.properties")
@ConfigurationProperties(prefix = "user")
public class UserDestinations {
    @NotNull
    private String userServiceUrl;

    public String getUserServiceUrl() {
        return userServiceUrl;
    }

    public void setUserServiceUrl(String userServiceUrl) {
        this.userServiceUrl = userServiceUrl;
    }
}

