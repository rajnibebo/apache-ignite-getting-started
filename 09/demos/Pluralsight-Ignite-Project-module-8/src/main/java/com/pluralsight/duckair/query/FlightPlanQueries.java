package com.pluralsight.duckair.query;

import com.pluralsight.duckair.model.FlightPlans;
import org.apache.ignite.Ignite;
import org.apache.ignite.IgniteCache;
import org.apache.ignite.cache.CacheEntry;
import org.apache.ignite.cache.query.FieldsQueryCursor;
import org.apache.ignite.cache.query.QueryCursor;
import org.apache.ignite.cache.query.SqlFieldsQuery;
import org.apache.ignite.cache.query.SqlQuery;

import javax.cache.Cache;
import java.util.ArrayList;
import java.util.List;

public class FlightPlanQueries {

    private Ignite ignite;
    private IgniteCache<String, FlightPlans> flightPlanCache;

    public FlightPlanQueries(Ignite ignite) {
        this.ignite = ignite;
        this.flightPlanCache = ignite.getOrCreateCache("FlightPlansCache");
    }

    public CacheEntry<String, FlightPlans> getRandomFlightPlan() {
        IgniteCache<String, FlightPlans> cache = ignite.getOrCreateCache("FlightPlansCache");
        SqlFieldsQuery randomFlightPlanQuery = new SqlFieldsQuery("SELECT _key FROM flight_plans ORDER BY RAND() LIMIT 1");
        try (FieldsQueryCursor<List<?>> cursor = cache.query(randomFlightPlanQuery)) {
            List<List<?>> list = cursor.getAll();
            return cache.getEntry(list.get(0).get(0).toString());
        }
    }

    public List<FlightPlans> getEnRouteFlights() {
        SqlQuery<String, FlightPlans> query = new SqlQuery<String, FlightPlans>(
                FlightPlans.class, "flight_status = ?").setArgs("enroute");
        return runQuery(query);
    }

    private List<FlightPlans> runQuery(SqlQuery<String, FlightPlans> query) {
        List<FlightPlans> flightPlanList = new ArrayList<>();

        try (QueryCursor<Cache.Entry<String, FlightPlans>> cursor = flightPlanCache.query(query)) {
            for (Cache.Entry<String, FlightPlans> plan : cursor) {
                flightPlanList.add(plan.getValue());
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            System.out.println("\n");
            return null;
        }
        return flightPlanList;
    }
}
