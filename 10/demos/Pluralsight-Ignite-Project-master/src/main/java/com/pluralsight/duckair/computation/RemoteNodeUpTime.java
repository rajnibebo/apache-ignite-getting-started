/*
 * Copyright (c) 2019. Edward Curren
 */

package com.pluralsight.duckair.computation;

import org.apache.ignite.Ignite;

import java.util.Collection;
import java.util.PrimitiveIterator;

public class RemoteNodeUpTime {

    private Ignite ignite;

    public RemoteNodeUpTime(Ignite ignite) {this.ignite = ignite;}

    public void showRemoteNodeUpTime() {
        Collection<Long> upTimeList = ignite.compute(ignite.cluster().forRemotes()).broadcast(() -> ignite.cluster().metrics().getUpTime());
        PrimitiveIterator.OfLong iterator = upTimeList.stream().mapToLong(Long::longValue).iterator();
        for(PrimitiveIterator.OfLong it = iterator; iterator.hasNext();) {
            System.out.println(millisecondsToString(it.next()));
        }
    }

    private static String millisecondsToString(Long milliseconds) {
        long days = milliseconds / 86400000;
        milliseconds -= 86400000 * days;
        long hours = milliseconds / 3600000;
        milliseconds -= 3600000 * hours;
        long minutes = milliseconds / 60000;
        milliseconds -= 60000 * minutes;
        long seconds = milliseconds / 1000;
        milliseconds -= 1000 * seconds;

        return days + ":" + hours + ":" + minutes + ":" + seconds + ":" + milliseconds;
    }
}
