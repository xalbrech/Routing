package xalbrech.exercises.routing.map;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import xalbrech.exercises.routing.map.json.CountryMapping;
import xalbrech.exercises.routing.map.json.CountryMappingList;

import java.util.ArrayList;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * Maintains the map of countries and provides m
 * country data to the search algorithm (the {@link xalbrech.exercises.routing.calculation.RouteCalculator})
 */
@Component
public class CountryMap {

    @Value("${routing.country-json-url}")
    private String countryJsonUrl;

    @Autowired
    private RestTemplate restTemplate;

    private Map<String, Country> countries;

    /**
     * Load country mapping upon initialization of the application. Is called by an event listener.
     *
     * @throws JsonProcessingException if Json processing fails
     */
    public void initMap() throws JsonProcessingException {
        ResponseEntity<String> response =
                restTemplate.exchange(RequestEntity.get(countryJsonUrl).build(),
                        String.class);

        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        CountryMappingList countryMappings = objectMapper.readValue(response.getBody(),
                CountryMappingList.class);

        countries = countryMappings
                .stream()
                .map(mapping -> new Country(mapping.getCca3()))
                .collect(Collectors.toMap(Country::getCca3, Function.identity()));

        countryMappings.forEach(mapping -> {
            Set<Country> borders = mapping.getBorders().stream().map(countries::get).filter(Objects::nonNull).collect(Collectors.toSet());
                    countries.get(mapping.getCca3()).setBorders(borders);
                }
        );
    }

    /**
     * @param cca3 country code
     * @return Country instance for the code
     */
    public Country getCountry(String cca3) {
        return this.countries.get(cca3);
    }
}
