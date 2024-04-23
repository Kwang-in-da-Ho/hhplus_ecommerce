package org.khjin.hhplusecommerce.infrastructure.order

import org.springframework.data.jpa.repository.JpaRepository

interface OrderJpaRepository: JpaRepository<Long, OrderEntity> {
}