package xalbrech.exercises.routing.map.json;

import java.util.ArrayList;
import java.util.Set;

/**
 * Helper used in JSON mapping / ObjectMapper only
 */
public class CountryMapping {
    private Set<String> borders;
    private String cca3;

    public Set<String> getBorders() {
        return borders;
    }

    public void setBorders(Set<String> borders) {
        this.borders = borders;
    }

    public String getCca3() {
        return cca3;
    }

    public void setCca3(String cca3) {
        this.cca3 = cca3;
    }

    @Override
    public String toString() {
        return "{" +
                "borders=" + borders +
                ", cca3='" + cca3 + '\'' +
                '}';
    }

}


