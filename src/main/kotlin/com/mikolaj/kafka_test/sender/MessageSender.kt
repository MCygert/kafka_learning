package com.mikolaj.kafka_test.sender

import com.mikolaj.kafka_test.producer.KafkaProducer
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController

@RestController
class MessageSender(
    private val messageProducer: KafkaProducer
) {
    @GetMapping("/order")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    fun sendOrder() {
        messageProducer.sendOrder()
    }

    @GetMapping("/client")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    fun sendClient() {
        messageProducer.sendClient()
    }
}