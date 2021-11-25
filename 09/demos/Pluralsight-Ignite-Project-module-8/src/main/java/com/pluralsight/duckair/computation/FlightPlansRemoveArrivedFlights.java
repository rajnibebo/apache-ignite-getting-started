/*
 * Copyright (c) 2019. Edward Curren
 */

package com.pluralsight.duckair.computation;

import com.pluralsight.duckair.model.FlightPlans;
import org.apache.ignite.Ignite;
import org.apache.ignite.IgniteCache;
import org.apache.ignite.cache.query.QueryCursor;
import org.apache.ignite.cache.query.SqlQuery;
import org.apache.ignite.lang.IgniteClosure;
import org.apache.ignite.resources.IgniteInstanceResource;

import javax.cache.Cache;
import java.util.ArrayList;
import java.util.Collection;

public class FlightPlansRemoveArrivedFlights implements IgniteClosure<IgniteCache<String, FlightPlans>, Collection<FlightPlans>> {

    @IgniteInstanceResource
    Ignite ignite;

    @Override
    public Collection<FlightPlans> apply(IgniteCache<String, FlightPlans> flightPlansCache) {
        Collection<FlightPlans> flightPlansCollection = new ArrayList<FlightPlans>();
        SqlQuery<String, FlightPlans> query = new SqlQuery<>(FlightPlans.class, "flight_status = ?");
        try(QueryCursor<Cache.Entry<String, FlightPlans>> cursor = flightPlansCache.query(query.setArgs("Arrived"))) {
            for(Cache.Entry<String, FlightPlans> entry : cursor) {
                flightPlansCollection.add(entry.getValue());
                flightPlansCache.remove(entry.getKey());
            }
        }
        return flightPlansCollection;
    }
}
