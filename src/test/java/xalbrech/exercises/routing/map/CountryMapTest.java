package xalbrech.exercises.routing.map;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.junit.jupiter.api.Assertions.assertNotNull;

// @RestClientTest
@SpringBootTest
class CountryMapTest {

    @Autowired
    CountryMap countryMap;

    @Test
    public void countryMapPopulated() throws JsonProcessingException {
        assertNotNull(countryMap.getCountry("CZE"));
    }

    @Test
    public void svkHasTheRightBorders() {
        Country svk =  countryMap.getCountry("SVK");
        Set<Country> borders = svk.getBorders();
        List<String> borderNames = borders.stream().map(Country::getCca3).collect(Collectors.toList());
        assertThat(borderNames, containsInAnyOrder("POL", "HUN", "CZE", "UKR", "AUT"));
    }

}