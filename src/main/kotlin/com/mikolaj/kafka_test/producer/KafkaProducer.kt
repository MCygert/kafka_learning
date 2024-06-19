package com.mikolaj.kafka_test.producer

import com.google.gson.Gson
import com.mikolaj.kafka_test.dto.Client
import com.mikolaj.kafka_test.dto.Order
import org.springframework.kafka.core.KafkaTemplate
import org.springframework.stereotype.Component
import java.math.BigDecimal
import java.util.*

const val ORDER_TOPIC = "orderTopic"
const val CLIENT_TOPIC = "clientTopic"
const val GROUP_ID = "groupId"

@Component
class KafkaProducer(
    private val orderDtoKafkaTemplate: KafkaTemplate<String, String>,
    private val clientDtoKafkaTemplate: KafkaTemplate<String, String>,
    private val gson: Gson
) {
    fun sendOrder() {
        val order = Order(clientId = "123", amount = BigDecimal.TEN)
        orderDtoKafkaTemplate.send(ORDER_TOPIC, gson.toJson(order))
    }

    fun sendClient() {
        val client = Client(UUID.randomUUID(), "123", "Mikolaj")
        clientDtoKafkaTemplate.send(CLIENT_TOPIC, gson.toJson(client))
    }

}