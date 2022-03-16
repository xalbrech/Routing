package xalbrech.exercises.routing.calculation;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import xalbrech.exercises.routing.map.Country;
import xalbrech.exercises.routing.map.CountryMap;

import java.text.MessageFormat;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Calculates the route between two countries.
 * Implements as a bfs over the graph of countries.
 */
@Component
public class RouteCalculator {

    private static final Logger log = LoggerFactory.getLogger(RouteCalculator.class);

    @Autowired
    private CountryMap countryMap;

    /**
     * Calculates route between two countries
     * @param originString cca3 code of originating country
     * @param destinationString cca3 code of destination country
     * @return Route as a collection of cca3 codes, origin and destination included
     * @throws RouteNotFoundException if route between origin and destination was not found.
     * @throws CountryNotFoundException if either origin or destination country code was not found
     */
    public Collection<String> routing(String originString, String destinationString) throws RouteNotFoundException, CountryNotFoundException {
        Country originCountry = countryMap.getCountry(originString);
        if (originCountry == null) {
            throw new CountryNotFoundException("Origin country code was not found");
        }

        Country destinationCountry = countryMap.getCountry(destinationString);
        if (destinationCountry == null) {
            throw new CountryNotFoundException("Destination country code was not found");
        }

        Collection<Country> result = routing(originCountry, destinationCountry);
        return result.stream().map(Country::getCca3).collect(Collectors.toList());
    }

    private Collection<Country> routing(Country origin, Country destination) throws RouteNotFoundException {
        Queue<Country> queue = new LinkedList<>();
        queue.add(origin);

        Set<Country> discovered = new HashSet<>();
        Map<Country, Country> parents = new HashMap<>();
        parents.put(origin, origin);

        do {
            Country country = queue.poll();
            log.debug("Country {}", country);
            if (country.equals(destination)) {
                break;
            }
            Set<Country> borders = country.getBorders();
            borders.forEach(border -> {
                if (!discovered.contains(border)) {
                    log.debug("Border {}", border);
                    queue.add(border);
                    discovered.add(border);
                    parents.put(border, country);
                }
            });
        } while (queue.size() > 0);

        if (parents.get(destination) == null) {
            String errorText = MessageFormat.format("Route between {0} and {1} was not found", origin, destination);
            log.debug(errorText);
            throw new RouteNotFoundException(errorText);
        }

        ArrayList<Country> result = new ArrayList<Country>();
        result.add(destination);

        Country child = destination;
        while (child != origin) {
            child = parents.get(child);
            result.add(child);
        }

        Collections.reverse(result);
        return result;
    }


}
