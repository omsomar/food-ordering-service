package com.food.ordering.system.kafka.producer.service;

import com.google.common.util.concurrent.FutureCallback;
import org.apache.avro.specific.SpecificRecordBase;
import org.checkerframework.checker.units.qual.K;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.util.concurrent.ListenableFutureCallback;

import java.io.Serializable;

public interface KafkaProducer<K extends Serializable, V extends SpecificRecordBase> {

    void send(String topicName, K key, V message, ListenableFutureCallback<SendResult<K,V>> callback);
}
