package com.pluralsight.duckair.service;

import com.pluralsight.duckair.model.AsmarEvent;

import javax.cache.CacheException;

public interface AsmarService {

    void addEvent(AsmarEvent asmarEvent) throws CacheException;
}