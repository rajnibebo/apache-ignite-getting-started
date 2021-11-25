package com.pluralsight.duckair.key;

import java.util.Date;
import java.util.Objects;
import org.apache.ignite.cache.affinity.AffinityKeyMapped;

public class FlightKey {

    @AffinityKeyMapped
    private String flightNumber;
    private Date flightDate;
    private String origin;
    private String destination;

    public String getFlightNumber() {
        return flightNumber;
    }

    public void setFlightNumber(String flightNumber) {
        this.flightNumber = flightNumber;
    }

    public Date getFlightDate() {
        return flightDate;
    }

    public void setFlightDate(Date flightDate) {
        this.flightDate = flightDate;
    }

    public String getOrigin() {
        return origin;
    }

    public void setOrigin(String origin) {
        this.origin = origin;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        FlightKey flightKey = (FlightKey) o;
        return Objects.equals(flightNumber, flightKey.flightNumber) &&
                Objects.equals(flightDate, flightKey.flightDate) &&
                Objects.equals(origin, flightKey.origin) &&
                Objects.equals(destination, flightKey.destination);
    }

    @Override
    public int hashCode() {
        return Objects.hash(flightNumber, flightDate, origin, destination);
    }
}
