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

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

@Component
public class CountryMap {

    @Value("${routing.country-json-url}")
    private String countryJsonUrl;

    @Autowired
    private RestTemplate restTemplate;
    private Map<String, Country> countries;

    /**
     * Load country mapping upon initialization of the application.
     *
     * @throws JsonProcessingException
     */
    @PostConstruct
    public void loadCountryMap() throws JsonProcessingException {
        ResponseEntity<String> response =
                restTemplate.exchange(RequestEntity.get(countryJsonUrl).build(),
                                String.class);

        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        ArrayList<CountryMapping> countryMappings = objectMapper.readValue(response.getBody(),
                CountryMappingList.class);

        countries = countryMappings
                .stream()
                .map(mapping -> new Country(mapping.getCca3()))
                .collect(Collectors.toMap(Country::getCca3, Function.identity()));

        countryMappings.forEach(mapping -> {
                    Set<Country> borders = mapping.getBorders().stream().map(countries::get).collect(Collectors.toSet());
                    countries.get(mapping.getCca3()).setBorders(borders);
                }
        );
    }

    public Country getCountry(String cca3) {
        return this.countries.get(cca3);
    }

}
