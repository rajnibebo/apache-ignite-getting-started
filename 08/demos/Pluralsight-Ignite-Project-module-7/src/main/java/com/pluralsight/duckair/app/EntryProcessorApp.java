package com.pluralsight.duckair.app;

import com.pluralsight.duckair.model.FlightPlans;
import com.pluralsight.duckair.query.FlightPlanQueries;
import org.apache.ignite.Ignite;
import org.apache.ignite.IgniteCache;
import org.apache.ignite.Ignition;
import org.apache.ignite.cache.CacheEntry;

import javax.cache.processor.EntryProcessor;

public class EntryProcessorApp {
    public static void main(String[] args) {
        try(Ignite ignite = Ignition.start("DuckAirlines-server.xml")) {
            IgniteCache<String, FlightPlans> cache = ignite.getOrCreateCache("FlightPlansCache");
            FlightPlanQueries flightPlanQueries = new FlightPlanQueries(ignite);
            CacheEntry<String, FlightPlans> flightPlan = flightPlanQueries.getRandomFlightPlan();

            System.out.println("\nHave flight plan with key: " + flightPlan.getKey());
            System.out.println("and value: " + flightPlan.getValue() + "\n");

            cache.invoke(flightPlan.getKey(), (EntryProcessor<String, FlightPlans, Object>) (entry, arguments) -> {
                FlightPlans flightPlans = entry.getValue();
                flightPlans.setFlightStatus("Enroute");
                entry.setValue(flightPlans);
                return flightPlans;
            });
        }
    }
}
