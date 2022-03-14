package xalbrech.exercises.routing.controller;

import java.util.Collection;

/**
 * Mapping of the response of '/routing'
 */
public class RoutingResult {

    Collection<String> route;

    public RoutingResult(Collection<String> route) {
        this.route = route;
    }

    public Collection<String> getRoute() {
        return route;
    }
}
