package com.pluralsight.duckair.computation;

import com.pluralsight.duckair.model.RouteSegments;
import org.apache.ignite.Ignite;
import org.apache.ignite.IgniteException;
import org.apache.ignite.cluster.ClusterNode;
import org.apache.ignite.compute.*;
import org.apache.ignite.lang.IgniteUuid;
import org.apache.ignite.resources.IgniteInstanceResource;
import org.apache.ignite.resources.TaskSessionResource;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

@ComputeTaskSessionFullSupport
public class RouteDistanceComputation extends ComputeTaskAdapter<List<RouteSegments>, Integer> {

    @IgniteInstanceResource
    private Ignite ignite;

    @TaskSessionResource
    private ComputeTaskSession session;

    @Nullable
    @Override
    public Map<? extends ComputeJob, ClusterNode> map(List<ClusterNode> list, @Nullable List<RouteSegments> routeSegments) throws IgniteException {

        String nodeId = ignite.cluster().localNode().id().toString();
        Map<ComputeJob, ClusterNode> jobMapping = session.loadCheckpoint(nodeId + ":TotalDistanceMapping");
        if(jobMapping == null){
            jobMapping = new HashMap<>();
        }

        Iterator<ClusterNode> nodeIterator = list.iterator();

        for(RouteSegments segment : routeSegments) {

            System.out.println("Processing job for segment: " + segment.getRouteId() + " : " + segment.getSequence());

            if(!nodeIterator.hasNext()) {
                nodeIterator = list.iterator();
            }

            ComputeJobAdapter computeJob = new ComputeJobAdapter() {
                @Override
                public Object execute() throws IgniteException {
                    return segment.getDistanceToNext();
                }
            };
            jobMapping.put(computeJob, nodeIterator.next());

            session.saveCheckpoint(nodeId + ":TotalDistanceMapping", jobMapping);

        }

        return jobMapping;
    }

    @Nullable
    @Override
    public Integer reduce(List<ComputeJobResult> list) throws IgniteException {
        Integer totalDistance = 0;
        for(ComputeJobResult result : list) {
            totalDistance += result.<Integer>getData();
        }
        return totalDistance;
    }
}