package xalbrech.exercises.routing.calculation;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import xalbrech.exercises.routing.map.Country;
import xalbrech.exercises.routing.map.CountryMap;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.contains;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@SpringBootTest
class RouteCalculatorTest {

    @MockBean
    CountryMap countryMap;

    @Autowired
    RouteCalculator routeCalculator;

    private Country mockCountry(String cca3) {
        Country country = Mockito.mock(Country.class);
        when(country.getCca3()).thenReturn(cca3);
        when(countryMap.getCountry(cca3)).thenReturn(country);
        return country;
    }

    private Set<Country> countrySet(Country... countries) {
        return new HashSet<>(Arrays.asList(countries));
    }

    @BeforeEach
    public void setUpCountryMap() {
        Country cze = mockCountry("CZE");
        Country svk = mockCountry("SVK");
        Country ukr = mockCountry("UKR");
        Country aut = mockCountry("AUT");
        Country ita = mockCountry("ITA");
        Country usa = mockCountry("USA");

        when(cze.getBorders()).thenReturn(countrySet(svk, aut));
        when(svk.getBorders()).thenReturn(countrySet(ukr, cze, aut));
        when(ukr.getBorders()).thenReturn(countrySet(svk));
        when(aut.getBorders()).thenReturn(countrySet(cze, svk, ita));
        when(ita.getBorders()).thenReturn(countrySet(aut));
    }

    @Test
    public void czeToCze() throws RouteNotFoundException, CountryNotFoundException {
        Collection<String> result = routeCalculator.routing("CZE", "CZE");
        assertThat(result, contains("CZE"));
    }

    @Test
    public void czeToIta() throws RouteNotFoundException, CountryNotFoundException {
        Collection<String> result = routeCalculator.routing("CZE", "ITA");
        assertThat(result, contains("CZE", "AUT", "ITA"));
    }

    @Test
    public void ukrToSvk() throws RouteNotFoundException, CountryNotFoundException {
        Collection<String> result = routeCalculator.routing("UKR", "SVK");
        assertThat(result, contains("UKR", "SVK"));
    }

    @Test
    public void ukrToIta() throws RouteNotFoundException, CountryNotFoundException {
        Collection<String> result = routeCalculator.routing("UKR", "ITA");
        assertThat(result, contains("UKR", "SVK", "AUT", "ITA"));
    }

    @Test
    public void usaToCze() {
        assertThrows(RouteNotFoundException.class, () -> {
            routeCalculator.routing("USA", "CZE");
        });
    }

    @Test
    public void usaToNowhere() {
        assertThrows(CountryNotFoundException.class, () -> {
            routeCalculator.routing("USA", "Nowhere");
        });
    }

}