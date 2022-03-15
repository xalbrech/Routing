package xalbrech.exercises.routing.calculation;

/**
 * Thrown by route calculation engine to signal no route has been found.
 */
public class RouteNotFoundException extends Exception {
    public RouteNotFoundException(String message) {
        super(message);
    }
}
