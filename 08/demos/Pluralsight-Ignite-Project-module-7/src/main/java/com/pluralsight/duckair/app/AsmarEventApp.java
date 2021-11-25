/*
 * Copyright (c) 2019. Edward Curren
 */

package com.pluralsight.duckair.app;

import com.pluralsight.duckair.model.AsmarEvent;
import com.pluralsight.duckair.key.AsmarEventKey;
import com.pluralsight.duckair.service.AsmarServiceImpl;
import org.apache.ignite.Ignite;
import org.apache.ignite.Ignition;
import org.apache.ignite.cache.CacheAtomicityMode;
import org.apache.ignite.cache.CacheMode;
import org.apache.ignite.configuration.CacheConfiguration;
import org.apache.ignite.services.ServiceConfiguration;

public class AsmarEventApp {

    public static void main(String args[]) {
        Ignition.setClientMode(true);
        try (Ignite ignite = Ignition.start("DuckAirlines-server.xml")) {
            ignite.addCacheConfiguration(createAsmarEventCache());
            ignite.getOrCreateCache("AsmarEventCache");
            deployAsmarEventService(ignite);
        }
    }

    private static void deployAsmarEventService(Ignite ignite) {
        ServiceConfiguration serviceConfiguration = new ServiceConfiguration();
        serviceConfiguration.setName("AsmarService");
        serviceConfiguration.setService(new AsmarServiceImpl());
        serviceConfiguration.setTotalCount(5);
        serviceConfiguration.setMaxPerNodeCount(2);
        ignite.services().cancel("AsmarService");
        ignite.services().deploy(serviceConfiguration);
    }

    private static CacheConfiguration<AsmarEventKey, AsmarEvent> createAsmarEventCache() {
        CacheConfiguration<AsmarEventKey, AsmarEvent> ccfg = new CacheConfiguration<>("AsmarEventCache");
        ccfg.setCacheMode(CacheMode.PARTITIONED);
        ccfg.setAtomicityMode(CacheAtomicityMode.ATOMIC);
        ccfg.setIndexedTypes(AsmarEventKey.class, AsmarEvent.class);

        return ccfg;
    }
}
