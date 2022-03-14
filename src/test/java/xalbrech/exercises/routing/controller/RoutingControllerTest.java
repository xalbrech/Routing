package xalbrech.exercises.routing.controller;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import xalbrech.exercises.routing.calculation.RouteCalculator;

import java.util.Arrays;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class RoutingControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    RouteCalculator routeCalculator;

    @InjectMocks
    RoutingController routingController;

    @Test
    public void routingCallsRoutingCalulationBean() throws Exception {
        mockMvc.perform(get("/routing/origin/destination"))
                .andExpect(status().isOk());

        verify(routeCalculator).routing("origin", "destination");
    }

    @Test
    public void routingReturnsExpectedJsonObject() throws Exception {
        when(routeCalculator.routing("CZE", "UKR")).thenReturn(Arrays.asList("CZE", "SVK", "UKR"));

        mockMvc.perform(get("/routing/CZE/UKR"))
                .andExpect(status().isOk())
                .andExpect(content().json("{\"route\" : [\"CZE\", \"SVK\", \"UKR\"]}"));
    }


}