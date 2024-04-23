package org.khjin.hhplusecommerce.infrastructure.order

import org.khjin.hhplusecommerce.domain.order.OrderRepository
import org.springframework.stereotype.Repository

@Repository
class OrderRepositoryImpl(
    private val orderJpaRepository: OrderJpaRepository
): OrderRepository {
}