package xalbrech.exercises.routing.map;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

/**
 * Initializing bean - populates the country map.
 */
@Component
public class CountryMapInitializingBean implements InitializingBean {

    @Autowired
    CountryMap countryMap;

    @Override
    public void afterPropertiesSet() throws Exception {
        countryMap.initMap();
    }
}
