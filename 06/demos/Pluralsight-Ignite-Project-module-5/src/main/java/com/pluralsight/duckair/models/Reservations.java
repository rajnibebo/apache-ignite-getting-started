package com.pluralsight.duckair.models;

import java.io.Serializable;

/**
 * Reservations definition.
 * 
 * This file was generated by Ignite Web Console (12/23/2018, 15:53)
 **/
public class Reservations implements Serializable {
    /** */
    private static final long serialVersionUID = 0L;

    /** Value for customerId. */
    private String customerId;

    /** Value for flightId. */
    private String flightId;

    /** Value for seat. */
    private String seat;

    /** Empty constructor. **/
    public Reservations() {
        // No-op.
    }

    /** Full constructor. **/
    public Reservations(String customerId,
        String flightId,
        String seat) {
        this.customerId = customerId;
        this.flightId = flightId;
        this.seat = seat;
    }

    /**
     * Gets customerId
     * 
     * @return Value for customerId.
     **/
    public String getCustomerId() {
        return customerId;
    }

    /**
     * Sets customerId
     * 
     * @param customerId New value for customerId.
     **/
    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    /**
     * Gets flightId
     * 
     * @return Value for flightId.
     **/
    public String getFlightId() {
        return flightId;
    }

    /**
     * Sets flightId
     * 
     * @param flightId New value for flightId.
     **/
    public void setFlightId(String flightId) {
        this.flightId = flightId;
    }

    /**
     * Gets seat
     * 
     * @return Value for seat.
     **/
    public String getSeat() {
        return seat;
    }

    /**
     * Sets seat
     * 
     * @param seat New value for seat.
     **/
    public void setSeat(String seat) {
        this.seat = seat;
    }

    /** {@inheritDoc} **/
    @Override public boolean equals(Object o) {
        if (this == o)
            return true;
        
        if (!(o instanceof Reservations))
            return false;
        
        Reservations that = (Reservations)o;

        if (customerId != null ? !customerId.equals(that.customerId) : that.customerId != null)
            return false;
        

        if (flightId != null ? !flightId.equals(that.flightId) : that.flightId != null)
            return false;
        

        if (seat != null ? !seat.equals(that.seat) : that.seat != null)
            return false;
        
        return true;
    }

    /** {@inheritDoc} **/
    @Override public int hashCode() {
        int res = customerId != null ? customerId.hashCode() : 0;

        res = 31 * res + (flightId != null ? flightId.hashCode() : 0);

        res = 31 * res + (seat != null ? seat.hashCode() : 0);

        return res;
    }

    /** {@inheritDoc} **/
    @Override public String toString() {
        return "Reservations [" + 
            "customerId=" + customerId + ", " + 
            "flightId=" + flightId + ", " + 
            "seat=" + seat +
        "]";
    }
}