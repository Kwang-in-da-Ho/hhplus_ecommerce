package org.khjin.hhplusecommerce.infrastructure.cart.cartItem

import org.springframework.data.jpa.repository.JpaRepository

interface CartItemJpaRepository: JpaRepository<Long, CartItemEntity> {
}