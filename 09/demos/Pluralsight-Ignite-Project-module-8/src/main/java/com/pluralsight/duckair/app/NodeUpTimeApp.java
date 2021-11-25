package com.pluralsight.duckair.app;

import com.pluralsight.duckair.computation.RemoteNodeUpTime;
import org.apache.ignite.Ignite;
import org.apache.ignite.Ignition;

public class NodeUpTimeApp {

    public static void main(String[] args) {
        Ignition.setClientMode(true);
        try(Ignite ignite = Ignition.start("DuckAirlines-server.xml")){
            RemoteNodeUpTime remoteNodeUpTime = new RemoteNodeUpTime(ignite);
            remoteNodeUpTime.showRemoteNodeUpTime();
        }
    }

}
