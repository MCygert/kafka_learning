package com.mikolaj.kafka_test.consumer

import com.google.gson.Gson
import com.mikolaj.kafka_test.dto.Client
import com.mikolaj.kafka_test.dto.Order
import com.mikolaj.kafka_test.producer.CLIENT_TOPIC
import com.mikolaj.kafka_test.producer.GROUP_ID
import com.mikolaj.kafka_test.producer.ORDER_TOPIC
import org.slf4j.LoggerFactory
import org.springframework.kafka.annotation.KafkaHandler
import org.springframework.kafka.annotation.KafkaListener
import org.springframework.kafka.annotation.RetryableTopic
import org.springframework.retry.annotation.Backoff
import org.springframework.stereotype.Component

@Component
class MessageConsumer(private val gson: Gson) {
    private val logger = LoggerFactory.getLogger(this.javaClass)

    @KafkaHandler
    @KafkaListener(
        topics = [ORDER_TOPIC],
        groupId = GROUP_ID,
    )
    @RetryableTopic(
        backoff = Backoff(value = 3000L),
        attempts = "5",
        autoCreateTopics = "false",
    )
    fun orderListener(message: String) {
        val order = gson.fromJson(message, Order::class.java)
        logger.info("Message received: [$order]")
    }

    @KafkaListener(topics = [CLIENT_TOPIC], groupId = GROUP_ID)
    fun clientListener(message: String) {
        val client = gson.fromJson(message, Client::class.java)
        logger.info("Message received: [$client]")
    }
}