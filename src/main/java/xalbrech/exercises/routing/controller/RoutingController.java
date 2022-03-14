package xalbrech.exercises.routing.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RoutingController {

    @GetMapping("/routing/{origin}/{destination}")
    public RoutingResult routing(@PathVariable String origin, @PathVariable String destination) {
        return null;
    }

}
