package xalbrech.exercises.routing.map;

import java.util.Objects;
import java.util.Set;

public class Country {

    private Set<Country> borders;
    private String cca3;

    public Country(String cca3) {
        this.cca3 = cca3;
    }

    public void setBorders(Set<Country> borders) {
        this.borders = borders;
    }

    public Set<Country> getBorders() {
        return borders;
    }

    public String getCca3() {
        return cca3;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Country country = (Country) o;
        return cca3.equals(country.cca3);
    }

    @Override
    public int hashCode() {
        return Objects.hash(cca3);
    }

    @Override
    public String toString() {
        return "\"" + cca3 + "\"";
    }
}
