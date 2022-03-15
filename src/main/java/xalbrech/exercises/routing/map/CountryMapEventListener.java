package xalbrech.exercises.routing.map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

/**
 * Event listener that loads the map data.
 */
@Component
public class CountryMapEventListener {

    @Autowired
    CountryMap countryMap;

    @EventListener(ApplicationReadyEvent.class)
    public void loadMap() throws Exception {
        countryMap.initMap();
    }

}
