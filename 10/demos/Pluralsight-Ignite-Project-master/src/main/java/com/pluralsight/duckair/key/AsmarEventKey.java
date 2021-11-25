package com.pluralsight.duckair.key;

import org.apache.ignite.cache.affinity.AffinityKeyMapped;

public class AsmarEventKey {

    private String asmarEventId;

    @AffinityKeyMapped
    private String telemetryId;

    public AsmarEventKey(String asmarEventId, String telemetryId) {
        this.asmarEventId = asmarEventId;
        this.telemetryId = telemetryId;
    }

    public String getAsmarEventId() {
        return asmarEventId;
    }

    public String getTelemetryId() {
        return telemetryId;
    }

}
