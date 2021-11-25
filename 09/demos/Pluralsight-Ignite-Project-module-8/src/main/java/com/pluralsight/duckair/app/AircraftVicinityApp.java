package com.pluralsight.duckair.app;

import com.pluralsight.duckair.geocalc.Coordinate;
import com.pluralsight.duckair.geocalc.DegreeCoordinate;
import com.pluralsight.duckair.geocalc.GeoCalc;
import com.pluralsight.duckair.geocalc.Point;
import com.pluralsight.duckair.model.Telemetry;
import com.pluralsight.duckair.query.TelemetryQueries;
import org.apache.ignite.Ignite;
import org.apache.ignite.IgniteCache;
import org.apache.ignite.Ignition;
import org.apache.ignite.cache.query.QueryCursor;
import org.apache.ignite.cache.query.ScanQuery;

import javax.cache.Cache;

public class AircraftVicinityApp {
    public static void main(String[] args) {
        Point kcle = Point.at(Coordinate.fromDegrees(41.4058), Coordinate.fromDegrees(-81.8539));
        Ignition.setClientMode(true);
        try(Ignite ignite = Ignition.start("DuckAirlines-server.xml")) {
            TelemetryQueries.insertDummyData(ignite);
            IgniteCache<String, Telemetry> telemetryCache = ignite.getOrCreateCache("TelemetryCache");
            ScanQuery<String, Telemetry> query = new ScanQuery<>();
            try (QueryCursor<Cache.Entry<String, Telemetry>> cursor = telemetryCache.query(query)) {
                for(Cache.Entry<String, Telemetry> entry : cursor) {
                    ignite.compute().affinityRun("TelemetryCache", entry.getKey(), () -> {
                        DegreeCoordinate latitude = Coordinate.fromDegrees(entry.getValue().getPositionLatitude().doubleValue());
                        DegreeCoordinate longitude = Coordinate.fromDegrees(entry.getValue().getPositionLongitude().doubleValue());
                        Point currentAircraftPosition = Point.at(latitude, longitude);
                        double distance = GeoCalc.harvesineDistance(kcle, currentAircraftPosition) / 1000;
                        if(distance < 500) {
                            System.out.println(entry.getValue() + "      Distance is: " + distance);
                        }
                    });
                }
            }
            TelemetryQueries.removeTelemetryData(ignite);
        }
    }
}
