package com.mikolaj.kafka_test.repository

import com.mikolaj.kafka_test.dto.Order
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface OrderRepository : CrudRepository<Order, UUID>
