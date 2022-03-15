package xalbrech.exercises.routing.calculation;

/**
 * Exception to signal that either origin or destination country code was not found.
 */
public class CountryNotFoundException extends Exception {
    public CountryNotFoundException(String message) {
        super(message);
    }
}
