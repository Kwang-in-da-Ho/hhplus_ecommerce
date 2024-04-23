package org.khjin.hhplusecommerce.infrastructure.cart

import org.khjin.hhplusecommerce.infrastructure.cart.CartEntity
import org.springframework.data.jpa.repository.JpaRepository

interface CartJpaRepository: JpaRepository<Long, CartEntity> {
}