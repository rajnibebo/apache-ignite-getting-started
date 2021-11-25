/*
 *   Pluralsight: Getting Stareted with Apache Ignite
 *   Module 4: Examining Clustering in Ignite
 *             Cluster Groups Demo
 *
 *   The code below demonstrates how to create Apache Ignite cluster groups
 *   based on an attribute assigned to the node in the node's configuration.
 *
 *   You can find the attribute configuration entries in both the DuckAirlines-server.
 *   configuration file as well as in ServerConfigurationFactory.java
 */

package com.pluralsight.duckair.app;

import org.apache.ignite.Ignite;
import org.apache.ignite.Ignition;
import org.apache.ignite.cluster.ClusterGroup;
import org.apache.ignite.cluster.ClusterNode;

public class GetNodesByAttribute {

    public static void main(String[] args) {
        Ignition.setClientMode(true);
        try(Ignite ignite = Ignition.start("DuckAirlines-server.xml")) {

            /**
             *   This code demonstrates how to create a group of nodes
             *   within a cluster ( a cluster group) that have an attribute
             *   named ROLE with a value of FlightReservations.
             */
            ClusterGroup clusterGroup = ignite.cluster();
            ClusterGroup flightReservationsNodes = clusterGroup.forAttribute("ROLE", "FlightReservations");
            System.out.println("\nFlight Operations Nodes:");
            for(ClusterNode node : flightReservationsNodes.nodes()) {
                System.out.println(node.id());
            }

            /*
            ClusterGroup clusterGroup = ignite.cluster();
            ClusterGroup flightOperationsNodes = clusterGroup.forAttribute("ROLE", "FlightOperations");
            System.out.println("\nFlight Operations Nodes:");
            for(ClusterNode node : flightOperationsNodes.nodes()) {
                System.out.println(node.id());
            }*/
        }
    }
}
