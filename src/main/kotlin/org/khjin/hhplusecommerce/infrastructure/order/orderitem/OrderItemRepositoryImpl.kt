package org.khjin.hhplusecommerce.infrastructure.order.orderitem

import org.khjin.hhplusecommerce.domain.order.orderitem.OrderItemRepository

class OrderItemRepositoryImpl(
    private val orderItemJpaRepository: OrderItemJpaRepository
): OrderItemRepository {
}