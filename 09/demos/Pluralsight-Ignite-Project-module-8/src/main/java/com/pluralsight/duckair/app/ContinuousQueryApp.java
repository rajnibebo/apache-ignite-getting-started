/*
 * Copyright (c) 2018. Edward Curren
 */

package com.pluralsight.duckair.app;

import com.pluralsight.duckair.query.TelemetryContinuousQuery;

public class ContinuousQueryApp {
    public static void main(String[] args) {
        TelemetryContinuousQuery telemetryContinuousQuery = new TelemetryContinuousQuery();
        telemetryContinuousQuery.runQuery();
    }
}
