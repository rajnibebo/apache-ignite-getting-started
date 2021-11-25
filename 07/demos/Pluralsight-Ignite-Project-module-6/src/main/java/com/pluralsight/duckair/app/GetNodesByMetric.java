/*
 *   Pluralsight: Getting Stareted with Apache Ignite
 *   Module 4: Examining Clustering in Ignite
 *             Cluster Groups Demo
 *
 *   The code below demonstrates how to create Apache Ignite cluster groups
 *   based on what time the node started.
 *
 */

package com.pluralsight.duckair.app;

import org.apache.ignite.Ignite;
import org.apache.ignite.Ignition;
import org.apache.ignite.cluster.ClusterGroup;
import org.apache.ignite.cluster.ClusterNode;

public class GetNodesByMetric {
    public static void main(String[] args) {
        Ignition.setClientMode(true);
        try(Ignite ignite = Ignition.start("DuckAirlines-server.xml")) {

            /**
             *   This code demonstrates how to create a group of nodes
             *   within a cluster ( a cluster group ) that started after
             *   a specific time.
             *
             *   The first block of code simply shows the start time of
             *   each of the nodes.  It's the second block of code that
             *   actually creates the cluster group.
             */

            ClusterGroup clusterGroup = ignite.cluster();
            ClusterGroup serverClusterGroup = clusterGroup.forServers();
            System.out.println("\nFlight Operations Nodes:");
            for(ClusterNode node : serverClusterGroup.nodes()) {
                System.out.println("Node id: " + node.id() +
                    "       Node start time: " + node.metrics().getStartTime());
            }

            ClusterGroup startTimeClusterGroup = serverClusterGroup.forPredicate(
                    (node) -> node.metrics().getStartTime() > 1518650000000L);
            for(ClusterNode node : startTimeClusterGroup.nodes()) {
                System.out.println(node.id());
            }

        }
    }
}
