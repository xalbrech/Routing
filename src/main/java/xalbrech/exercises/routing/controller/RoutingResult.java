package xalbrech.exercises.routing.controller;

import java.util.Collection;

/**
 * Mapping of the response of '/routing'
 */
public class RoutingResult {

    Collection<String> route;

    public Collection<String> getRoute() {
        return route;
    }

    public void setRoute(Collection<String> route) {
        this.route = route;
    }

}
