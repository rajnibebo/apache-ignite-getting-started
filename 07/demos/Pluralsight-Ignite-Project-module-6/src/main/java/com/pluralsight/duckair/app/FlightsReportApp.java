/*
 * Copyright (c) 2019. Edward Curren
 */

package com.pluralsight.duckair.app;

import com.pluralsight.duckair.report.FlightsReport;
import org.apache.ignite.Ignite;
import org.apache.ignite.Ignition;

public class FlightsReportApp {
    public static void main(String[] args) {
        Ignition.setClientMode(true);
        try (Ignite ignite = Ignition.start("DuckAirlines-server.xml")) {
            FlightsReport flightsReport = new FlightsReport(ignite);
            flightsReport.showFlightManifest();
            flightsReport.showFlightsEnroute();
        }
    }
}
