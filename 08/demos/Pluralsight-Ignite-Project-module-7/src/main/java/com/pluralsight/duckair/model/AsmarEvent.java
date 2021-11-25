package com.pluralsight.duckair.model;

import com.pluralsight.duckair.key.AsmarEventKey;
import org.apache.ignite.cache.query.annotations.QuerySqlField;

import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Objects;
import java.util.UUID;

public class AsmarEvent {

    @QuerySqlField
    private AsmarEventKey key;

    @QuerySqlField(index = true)
    private String eventId;

    @QuerySqlField(index = true, descending = true)
    private Timestamp timeStamp;

    @QuerySqlField(index = true)
    private String aircraftId;

    @QuerySqlField
    private String flightNumber;

    @QuerySqlField
    private String eventType;

    public AsmarEvent(String aircraftId, String flightNumber, String eventType) {
        this.aircraftId = aircraftId;
        this.flightNumber = flightNumber;
        this.eventType = eventType;
        this.eventId = UUID.randomUUID().toString();
        this.timeStamp = new Timestamp(Calendar.getInstance().getTime().getTime());

        // The telemetry cache key is the aircraft id
        this.key = new AsmarEventKey(eventId, aircraftId);
    }

    public AsmarEventKey getKey() {
        return key;
    }

    public String getEventId() {
        return eventId;
    }

    public Timestamp getTimeStamp() {
        return timeStamp;
    }

    public String getFlightNumber() {
        return flightNumber;
    }

    public String getAircraftId() {
        return aircraftId;
    }

    public String getEventType() {
        return eventType;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AsmarEvent that = (AsmarEvent) o;
        return Objects.equals(key, that.key) &&
                Objects.equals(eventId, that.eventId) &&
                Objects.equals(timeStamp, that.timeStamp) &&
                Objects.equals(flightNumber, that.flightNumber) &&
                Objects.equals(aircraftId, that.aircraftId) &&
                Objects.equals(eventType, that.eventType);
    }

    @Override
    public int hashCode() {
        int res = key != null ? key.hashCode() : 0;
        res = 31 * res + (eventId != null ? eventId.hashCode() : 0);
        res = 31 * res + (timeStamp != null ? timeStamp.hashCode() : 0);
        res = 31 * res + (flightNumber != null ? flightNumber.hashCode() : 0);
        res = 31 * res + (aircraftId != null ? aircraftId.hashCode() : 0);
        res = 31 * res + (eventType != null ? eventType.hashCode() : 0);
        return res;
    }

    @Override
    public String toString() {
        return "AsmarEvent{ " +
                "key= " + key +
                ", eventId=' " + eventId + '\'' +
                ", timeStamp= " + timeStamp +
                ", flightNumber= " + flightNumber +
                ", aircraftId= '" + aircraftId + '\'' +
                ", eventType= '" + eventType + '\'' +
                '}';
    }
}

