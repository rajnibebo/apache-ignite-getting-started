/*
 * Copyright (c) 2018. Edward Curren
 */

package com.pluralsight.duckair.app;

import com.pluralsight.duckair.query.AirportsQueries;

public class AirportApp {
    public static void main(String[] args) {
        AirportsQueries airportsQueries = new AirportsQueries();
        airportsQueries.runQuery();
    }
}
