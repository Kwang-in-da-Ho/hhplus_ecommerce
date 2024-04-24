package org.khjin.hhplusecommerce.infrastructure.cart.cartItem

import org.khjin.hhplusecommerce.domain.cart.cartItem.CartItemRepository
import org.springframework.stereotype.Repository

@Repository
class CartItemRepositoryImpl(
    private val cartItemJpaRepository: CartItemJpaRepository
): CartItemRepository {
}