package TAASS.scopriamoilpiemontegateway.config.municipalities;

import TAASS.scopriamoilpiemontegateway.config.events.EventDestination;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties(MunicipalityDestination.class)
public class MunicipalityConfiguration {

}
