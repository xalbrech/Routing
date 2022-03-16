package xalbrech.exercises.routing.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;
import xalbrech.exercises.routing.calculation.CountryNotFoundException;
import xalbrech.exercises.routing.calculation.RouteCalculator;
import xalbrech.exercises.routing.calculation.RouteNotFoundException;

import java.util.Arrays;

/**
 * Controller, defines the /routing/origin/destination endpoint of the application.
 */
@RestController
public class RoutingController {

    @Autowired
    private RouteCalculator routeCalculator;

    @ExceptionHandler({RouteNotFoundException.class, CountryNotFoundException.class})
    public ResponseEntity<Object> handleEntityNotFound(Exception e) {
        return ResponseEntity.badRequest().body(new RoutingError(e.getMessage()));
    }

    @GetMapping("/routing/{origin}/{destination}")
    public RoutingResult routing(@PathVariable String origin, @PathVariable String destination) throws CountryNotFoundException, RouteNotFoundException {
            return new RoutingResult(routeCalculator.routing(origin, destination));
    }

}
