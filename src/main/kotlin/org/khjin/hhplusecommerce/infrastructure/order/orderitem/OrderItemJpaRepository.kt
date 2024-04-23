package org.khjin.hhplusecommerce.infrastructure.order.orderitem

import org.springframework.data.jpa.repository.JpaRepository

interface OrderItemJpaRepository: JpaRepository<Long, OrderItemEntity> {
}