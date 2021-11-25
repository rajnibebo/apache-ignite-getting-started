package com.pluralsight.duckair.query;

import com.pluralsight.duckair.model.RouteSegments;
import org.apache.ignite.Ignite;
import org.apache.ignite.IgniteCache;
import org.apache.ignite.cache.query.QueryCursor;
import org.apache.ignite.cache.query.SqlQuery;
import org.apache.ignite.resources.IgniteInstanceResource;

import javax.cache.Cache;
import java.util.ArrayList;
import java.util.List;

public class RouteSegmentQueries {

    @IgniteInstanceResource
    private Ignite ignite;

    public RouteSegmentQueries(Ignite ignite) {
        this.ignite = ignite;
    }

    public List<RouteSegments> getRouteSegmentsForRouteId(String routeId) {
        IgniteCache<String, RouteSegments> segmentsCache = ignite.getOrCreateCache("RouteSegmentsCache");
        SqlQuery<String, RouteSegments> totalDistanceQuery = new SqlQuery<>(RouteSegments.class, "route_id = ?");
        try (QueryCursor<Cache.Entry<String, RouteSegments>> cursor = segmentsCache.query(totalDistanceQuery.setArgs(routeId))) {
            List<RouteSegments> segmentList = new ArrayList<>();
            for (Cache.Entry<String, RouteSegments> segment : cursor) {
                segmentList.add(segment.getValue());
            }
            return segmentList;
        }
    }
}
