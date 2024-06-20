package com.mikolaj.kafka_test.consumer

import com.google.gson.Gson
import com.mikolaj.kafka_test.dto.Client
import com.mikolaj.kafka_test.dto.Order
import com.mikolaj.kafka_test.producer.CLIENT_TOPIC
import com.mikolaj.kafka_test.producer.GROUP_ID
import com.mikolaj.kafka_test.producer.ORDER_TOPIC
import com.mikolaj.kafka_test.repository.ClientRepository
import com.mikolaj.kafka_test.repository.OrderRepository
import org.slf4j.LoggerFactory
import org.springframework.kafka.annotation.KafkaHandler
import org.springframework.kafka.annotation.KafkaListener
import org.springframework.kafka.annotation.RetryableTopic
import org.springframework.retry.annotation.Backoff
import org.springframework.stereotype.Component
import org.springframework.stereotype.Repository

@Component
class MessageConsumer(
    private val gson: Gson,
    private val clientRepository: ClientRepository,
    private val orderRepository: OrderRepository
) {
    private val logger = LoggerFactory.getLogger(this.javaClass)

    @KafkaHandler
    @KafkaListener(
        topics = [ORDER_TOPIC],
        groupId = GROUP_ID,
    )
    @RetryableTopic(
        backoff = Backoff(value = 10000L),
        attempts = "5",
        autoCreateTopics = "true",
    )
    fun orderListener(message: String) {
        val order = gson.fromJson(message, Order::class.java)
        logger.info("Message received: [$order]")
        clientRepository.findClientByGlobalId(order.clientId)
            ?: throw ClientNotFound("Client not found for order ${order.id}")
        orderRepository.save(order)
        println("Message Saved")
    }

    @KafkaListener(topics = [CLIENT_TOPIC], groupId = GROUP_ID)
    fun clientListener(message: String) {
        val client = gson.fromJson(message, Client::class.java)
        logger.info("Message received: [$client]")
        clientRepository.save(client)
    }
}

class ClientNotFound(message: String) : Exception(message)