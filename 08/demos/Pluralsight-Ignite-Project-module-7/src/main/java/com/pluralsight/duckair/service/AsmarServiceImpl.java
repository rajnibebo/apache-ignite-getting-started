/*
 * Copyright (c) 2019. Edward Curren
 */

package com.pluralsight.duckair.service;

import com.pluralsight.duckair.model.AsmarEvent;
import com.pluralsight.duckair.key.AsmarEventKey;
import org.apache.ignite.Ignite;
import org.apache.ignite.IgniteCache;
import org.apache.ignite.resources.IgniteInstanceResource;
import org.apache.ignite.services.Service;
import org.apache.ignite.services.ServiceContext;

import javax.cache.CacheException;

public class AsmarServiceImpl implements Service, AsmarService {

    /** Auto-injected instance of Ignite. */
    @IgniteInstanceResource
    private Ignite ignite;

    /** Distributed cache used to store events. */
    private IgniteCache<AsmarEventKey, AsmarEvent> asmarEventCache;

    /** Service name. */
    private String serviceName;

    @Override
    public void addEvent(AsmarEvent asmarEvent) throws CacheException {
        asmarEventCache.put(asmarEvent.getKey(), asmarEvent);
    }

    @Override
    public void init(ServiceContext serviceContext) throws Exception {
        // Pre-configured cache to store ASMAR events.
        asmarEventCache = ignite.getOrCreateCache("AsmarEventCache");
        serviceName = serviceContext.name();

        System.out.println("Service was initialized: " + serviceName);
    }

    @Override
    public void cancel(ServiceContext serviceContext) {
        asmarEventCache.clear();
        System.out.println("Service was cancelled: " + serviceName);
    }

    @Override
    public void execute(ServiceContext serviceContext) throws Exception {
        System.out.println("Executing distributed service: " + serviceName);

    }
}
