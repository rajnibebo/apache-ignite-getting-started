package com.pluralsight.duckair.app;

import com.pluralsight.duckair.model.Reservations;
import com.pluralsight.duckair.query.ReservationQueries;
import org.apache.ignite.Ignite;
import org.apache.ignite.IgniteCache;
import org.apache.ignite.Ignition;
import org.apache.ignite.cache.CacheEntry;
import org.apache.ignite.cache.affinity.AffinityKey;
import org.apache.ignite.cache.affinity.rendezvous.RendezvousAffinityFunction;
import org.apache.ignite.cluster.ClusterNode;

import java.util.List;

public class CollocationApp {

    public static void main(String[] args) {
        CollocationApp app = new CollocationApp();
        app.collocatePassengerAndReservation();
    }

    public void collocatePassengerAndReservation() {
        Ignition.setClientMode(true);
        try(Ignite ignite = Ignition.start("DuckAirlines-server.xml")) {
            IgniteCache<String, Reservations> reservationCache = ignite.getOrCreateCache("ReservationsCache");

            ReservationQueries reservationQueries = new ReservationQueries(ignite);
            CacheEntry<String, Reservations> randomReservation = reservationQueries.getRandomReservation();

            AffinityKey<String> affinityKeyForReservation = reservationQueries.getAffinityKeyForReservation();
            System.out.println(affinityKeyForReservation);

           /*
            *  NOTE: If you want to test the Apache Ignite Affinity Function, you will need assign the 'key' variable
            *  to a specific key in the reservations cache.  Otherwise you will get different values each time you run
            *  the code because this code is pulling a random key from the reservations cache each time.
            *
            *  It's also more interesting to test the Affinity Function on a cluster with more than one node.  Otherwise you
            *  will get the same node returned every time.
            * */
            demoIgniteAffinityFunction(ignite, randomReservation.getKey());
            demoIgniteAffinityFunction(ignite);
        }
    }

    public void demoIgniteAffinityFunction(Ignite ignite, String key)
    {
        RendezvousAffinityFunction affinityFunction = new RendezvousAffinityFunction(false, 30);
        int partition = affinityFunction.partition(key);
        List<ClusterNode> nodes = (List<ClusterNode>) ignite.cluster().nodes();
        List<ClusterNode> clusterNodes = affinityFunction.assignPartition(partition, nodes, 0, null);

        System.out.println("Partition number: " + partition);
        for (ClusterNode node : clusterNodes) {
            System.out.println(node.toString());
        }
    }

    public void demoIgniteAffinityFunction(Ignite ignite) {

        ReservationQueries reservationQueries = new ReservationQueries(ignite);
        AffinityKey<String> affKey = reservationQueries.getAffinityKeyForReservation();

        RendezvousAffinityFunction affinityFunction = new RendezvousAffinityFunction(false, 30);
        List<ClusterNode> nodes = (List<ClusterNode>) ignite.cluster().nodes();
        int partition = affinityFunction.partition(affKey);
        List<ClusterNode> clusterNodes = (List<ClusterNode>) affinityFunction.assignPartition(partition, nodes, 0, null);


        System.out.println("Partition number: " + partition);
        for (ClusterNode node : clusterNodes) {
            System.out.println(node.toString());
        }
    }
}

/*            String key = cursor.getAll().get(0).get(0).toString();

            CacheEntry<String, Reservations> entry = reservationsCache.getEntry(key);
            RendezvousAffinityFunction affinityFunction = new RendezvousAffinityFunction(false, 30);
            List<ClusterNode> nodes = (List<ClusterNode>) ignite.cluster().nodes();
            /*int partition = affinityFunction.partition(key);
            List<ClusterNode> clusterNodes = (List<ClusterNode>) affinityFunction.assignPartition(partition, nodes, 0, null);
            AffinityKey<String> affKey = getAffinityKeyForReservation();
            int partition = affinityFunction.partition(affKey);
            List<ClusterNode> clusterNodes = (List<ClusterNode>) affinityFunction.assignPartition(partition, nodes, 0, null);
            System.out.println("Partition number: " + partition);
            for (ClusterNode node : clusterNodes) {
            System.out.println(node.toString());
    }
            return reservationsCache.getEntry(key);
*/