/*
* ProducerEx.java 
* Created on  202016/11/30 9:48 
* Copyright © 2012 Phoenix New Media Limited All Rights Reserved 
*/
package com.ifeng.kafka;


import org.apache.kafka.clients.producer.Callback;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.apache.kafka.common.serialization.Serializer;
import org.apache.log4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

/**
 * Class Description Here
 *
 * @author zhanglr
 * @version 1.0.1
 */
public class KafkaProducerEx<K,V> extends KafkaProducer {
    private int batchSize = 1000;
    private int timeout = 3000;
    private long lastFlushTime = -1;
    private List<ProducerRecord> records = new ArrayList<>();
    private List<Future<RecordMetadata>> futures = new ArrayList<>();
    private static final Logger logger = Logger.getLogger(KafkaProducerEx.class);

    public KafkaProducerEx(Map<String, Object> configs) {
        super(configs);
        batchSize = configs.get("batch.size") == null ? batchSize : Integer.valueOf(configs.get("batch.size").toString());
        timeout = configs.get("timeout") == null ? timeout : Integer.valueOf(configs.get("timeout").toString());
    }

    public KafkaProducerEx(Map<String, Object> configs, Serializer<K> keySerializer, Serializer<V> valueSerializer) {
        super(configs, keySerializer, valueSerializer);
        batchSize = configs.get("batch.size") == null ? batchSize : Integer.valueOf(configs.get("batch.size").toString());
        timeout = configs.get("timeout") == null ? timeout : Integer.valueOf(configs.get("timeout").toString());
    }

    public KafkaProducerEx(Properties properties) {
        super(properties);
        batchSize = properties.get("batch.size") == null ? batchSize : Integer.valueOf(properties.get("batch.size").toString());
        timeout = properties.get("timeout") == null ? timeout : Integer.valueOf(properties.get("timeout").toString());
    }

    public KafkaProducerEx(Properties properties, Serializer<K> keySerializer, Serializer<V> valueSerializer) {
        super(properties, keySerializer, valueSerializer);
        batchSize = properties.get("batch.size") == null ? batchSize : Integer.valueOf(properties.get("batch.size").toString());
        timeout = properties.get("timeout") == null ? timeout : Integer.valueOf(properties.get("timeout").toString());
    }

    private boolean timeout() {
        return System.currentTimeMillis() - lastFlushTime > timeout;
    }

    @Override
    public void flush() {
        synchronized (records) {
            if (records.size() >= batchSize){
                doSubmit();
            }
        }
    }
    private void doSubmit(){
        try {
            records.forEach(r -> futures.add(send(r,new ProducerCallback())));
            super.flush();
            lastFlushTime = System.currentTimeMillis();
            futures.forEach(k -> {
                try {
                    k.get();
                } catch (InterruptedException e) {
                    logger.error(e);
                } catch (ExecutionException e) {
                    logger.error(e);
                }
            });
        } catch (Exception e) {
            logger.error(e);
        } finally {
            records.clear();
            futures.clear();
        }
    }
    public void sendBatch(ProducerRecord record) {
        synchronized (records) {
            records.add(record);
            if (records.size() >= batchSize || timeout()) {
                doSubmit();
            }
        }
    }
}

class ProducerCallback implements Callback {
    private static final Logger logger = Logger.getLogger(ProducerCallback.class);

    public void onCompletion(RecordMetadata metadata, Exception exception) {
        if (exception != null) {
            logger.error("Error sending message to Kafka {} ", exception);
        }
    }
}
