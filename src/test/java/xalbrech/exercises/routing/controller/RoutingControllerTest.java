package xalbrech.exercises.routing.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import xalbrech.exercises.routing.calculation.CountryNotFoundException;
import xalbrech.exercises.routing.calculation.RouteCalculator;
import xalbrech.exercises.routing.calculation.RouteNotFoundException;

import java.util.Arrays;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(classes = RoutingController.class)
@EnableWebMvc
@AutoConfigureMockMvc
class RoutingControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    RouteCalculator routeCalculator;

    @Test
    public void routingCallsRoutingCalulationBean() throws Exception {
        mockMvc.perform(get("/routing/origin/destination"))
                .andExpect(status().isOk());

        verify(routeCalculator).routing("origin", "destination");
    }

    @Test
    public void routingReturnsExpectedJsonObject() throws Exception {
        when(routeCalculator.routing("CZE", "UKR")).thenReturn(Arrays.asList("CZE", "ABC", "UKR"));

        mockMvc.perform(get("/routing/CZE/UKR"))
                .andExpect(status().isOk())
                .andExpect(content().json("{\"route\" : [\"CZE\", \"ABC\", \"UKR\"]}"));
    }

    @Test
    public void routingHandlesRouteNotFound() throws Exception {
        when(routeCalculator.routing("USA", "CZE")).thenThrow(new RouteNotFoundException("no routing found"));

        mockMvc.perform(get("/routing/USA/CZE"))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void routingHandlesUnknnownCode() throws Exception {
        when(routeCalculator.routing("UKR", "Narnia")).thenThrow(new CountryNotFoundException("No country found"));

        mockMvc.perform(get("/routing/UKR/Narnia"))
                .andExpect(status().isBadRequest());
    }

}