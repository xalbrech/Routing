package xalbrech.exercises.routing.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import xalbrech.exercises.routing.calculation.RouteCalculator;

@RestController
public class RoutingController {

    @Autowired
    RouteCalculator routeCalculator;

    @GetMapping("/routing/{origin}/{destination}")
    public RoutingResult routing(@PathVariable String origin, @PathVariable String destination) {
        return new RoutingResult(routeCalculator.routing(origin, destination));
    }

}
