package com.gonnect.sb.camel.routes.kafka.partitioner;

import java.util.Map;

import org.apache.kafka.clients.producer.Partitioner;
import org.apache.kafka.common.Cluster;

public class StringPartitioner implements Partitioner {

    public StringPartitioner() {
        // noop
    }

    @Override
    public void configure(Map<String, ?> configs) {
    }

    @Override
    public int partition(String topic, Object key, byte[] keyBytes, Object value, byte[] valueBytes, Cluster cluster) {
        int partId = 0;

        if (key instanceof String) {
            String sKey = (String) key;
            int len = sKey.length();

            // This will return either 1 or zero
            partId = len % 2;
        }

        return partId;
    }

    @Override
    public void close() {
    }

}
