package xalbrech.exercises.routing.map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Initializing bean - populates the country map.
 */
@Component
public class CountryMapInitializingBean implements InitializingBean {

    private static final Logger log = LoggerFactory.getLogger(CountryMapInitializingBean.class);

    @Autowired
    private CountryMap countryMap;

    @Override
    public void afterPropertiesSet() throws Exception {
        log.info("Populating map of countries...");
        countryMap.initMap();
        log.info("Map of countries populated.");
    }
}
