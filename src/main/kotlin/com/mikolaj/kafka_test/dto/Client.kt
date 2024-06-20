package com.mikolaj.kafka_test.dto

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.Table
import java.util.UUID

@Entity
@Table(name = "clients")
class Client(
    @Id
    val id: UUID,
    @Column(name = "global_id")
    val globalId: String,
    val name: String
)

