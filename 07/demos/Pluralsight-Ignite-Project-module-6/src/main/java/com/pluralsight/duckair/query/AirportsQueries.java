package com.pluralsight.duckair.query;

import com.pluralsight.duckair.model.Airports;
import org.apache.ignite.Ignite;
import org.apache.ignite.IgniteCache;
import org.apache.ignite.Ignition;
import org.apache.ignite.cache.query.QueryCursor;
import org.apache.ignite.cache.query.SqlFieldsQuery;
import org.apache.ignite.cache.query.SqlQuery;

import javax.cache.Cache;
import java.util.List;

public class AirportsQueries {
    private IgniteCache<String, Airports> airportsCache;

    public void runQuery() {
        Ignition.setClientMode(true);
        try(Ignite ignite = Ignition.start("DuckAirlines-server.xml")) {
            airportsCache = ignite.getOrCreateCache("AirportsCache");
            queryBySqlFields();
            queryBySql();
        }
    }

    public void queryBySqlFields() {
        SqlFieldsQuery query = new SqlFieldsQuery(
                "SELECT FacilityName, Longitude, Latitude " +
                        "FROM airports WHERE ICAO='KCLE'");

        try(QueryCursor<List<?>> cursor = airportsCache.query(query)) {
            for (List<?> entry : cursor) {
                System.out.println("\nAirport name: " +
                        (String) entry.get(0) + " Latitude: " +
                        entry.get(1) + " Longitude " + entry.get(2) + "\n");
            }
        }
    }

    public void queryBySql() {
        SqlQuery query = new SqlQuery(Airports.class, "ICAO='KCLE'");
        try(QueryCursor<Cache.Entry<String, Airports>> cursor = airportsCache.query(query)) {
            for(Cache.Entry<String, Airports> entry : cursor) {
                System.out.println("\n" + entry.getValue().toString() + "\n");
            }
        }
    }
}
