package xalbrech.exercises.routing.map;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.client.RestClientTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.core.io.Resource;
import org.springframework.core.metrics.ApplicationStartup;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.client.ExpectedCount;
import org.springframework.test.web.client.MockRestServiceServer;
import org.springframework.web.client.RestTemplate;

import javax.swing.*;
import java.net.URI;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withStatus;

@RestClientTest(value = CountryMap.class)
class CountryMapTest {

    @Autowired
    private MockRestServiceServer mockRestServiceServer;

    @Value("${routing.country-json-url}")
    private String countryJsonUrl;

    @Value("classpath:/TestCountryMap.json")
    private Resource testCountryMapJson;

    @Autowired
    private CountryMap countryMap;

    @BeforeEach
    public void setUpTestJson() throws JsonProcessingException {
        mockRestServiceServer.expect(ExpectedCount.once(), requestTo(URI.create(countryJsonUrl)))
                .andRespond(withStatus(HttpStatus.OK)
                        .contentType(MediaType.TEXT_PLAIN)
                        .body(testCountryMapJson));
        countryMap.initMap();
    }

    @Test
    public void svkHasTheRightBorders() {
        Country svk = countryMap.getCountry("SVK");
        Set<Country> borders = svk.getBorders();
        List<String> borderNames = borders.stream().map(Country::getCca3).collect(Collectors.toList());
        assertThat(borderNames, containsInAnyOrder( "CZE", "UKR"));
    }

    @Test
    public void countryMapPopulated() throws JsonProcessingException {
        assertNotNull(countryMap.getCountry("CZE"));
    }


}