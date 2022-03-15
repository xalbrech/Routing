package xalbrech.exercises.routing.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;
import xalbrech.exercises.routing.calculation.RouteCalculator;
import xalbrech.exercises.routing.calculation.RouteNotFoundException;

import java.text.MessageFormat;

/**
 * Defines the /routing/origin/destination endpoint
 */
@RestController
public class RoutingController {

    @Autowired
    RouteCalculator routeCalculator;

    @GetMapping("/routing/{origin}/{destination}")
    public RoutingResult routing(@PathVariable String origin, @PathVariable String destination) {
        try {
            return new RoutingResult(routeCalculator.routing(origin, destination));
        } catch (RouteNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Route between \"" + origin + "\" and  \"" + destination + "\" was not found.", e);
        }
    }

}
