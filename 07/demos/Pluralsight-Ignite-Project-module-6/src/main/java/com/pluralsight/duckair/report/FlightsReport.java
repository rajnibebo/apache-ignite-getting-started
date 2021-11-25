package com.pluralsight.duckair.report;

import com.pluralsight.duckair.model.FlightPlans;
import com.pluralsight.duckair.query.FlightPlanQueries;
import com.pluralsight.duckair.query.ReservationQueries;
import org.apache.ignite.Ignite;
import org.apache.ignite.cache.query.FieldsQueryCursor;

import java.util.List;

public class FlightsReport {

    private Ignite ignite;
    private FlightPlanQueries flightPlanQueries;

    public FlightsReport(Ignite ignite) {
        this.ignite = ignite;
        this.flightPlanQueries = new FlightPlanQueries(ignite);
    }

    public void showFlightsEnroute() {
        List<FlightPlans> enrouteFlights = flightPlanQueries.getEnRouteFlights();
        System.out.println("\nFlights Enroute:");
        for(FlightPlans flight : enrouteFlights) {
            System.out.println(flight.toString());
        }
    }

    public void showFlightManifest() {
        ReservationQueries reservationQueries = new ReservationQueries(ignite);
        FieldsQueryCursor<List<?>> resultSet = reservationQueries.getFlightManifest("530");
        System.out.println("\nFlight Manifest Query Result:");
            for (List<?> record : resultSet) {
                System.out.println(
                        "Flight Number: " + record.get(0) + ", " +
                        "First Name: " + record.get(1) + ", " +
                        "Last Name: " + record.get(2) + ", " +
                        "Seat: " + record.get(3) + ", " +
                        "Account: " + record.get(4) + ", " +
                        "Level: " + record.get(5));
            }
        System.out.println("\n");
    }
}
