package com.mikolaj.kafka_test.repository

import com.mikolaj.kafka_test.dto.Client
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface ClientRepository : CrudRepository<Client, UUID> {
    fun findClientByGlobalId(globalId: String): Client?
}
