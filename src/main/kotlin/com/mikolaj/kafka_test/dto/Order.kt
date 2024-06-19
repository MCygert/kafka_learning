package com.mikolaj.kafka_test.dto

import jakarta.persistence.*
import java.math.BigDecimal
import java.util.*

@Entity
@Table(name = "orders")
class Order(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Int? = null,
    @Column(name = "client_id", nullable = false)
    val clientId: String,
    @Column(nullable = false)
    val amount: BigDecimal,

    )


