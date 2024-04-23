package org.khjin.hhplusecommerce.infrastructure.cart

import org.khjin.hhplusecommerce.domain.cart.CartRepository
import org.springframework.stereotype.Repository

@Repository
class CartRepositoryImpl(
    private val cartJpaRepository: CartJpaRepository
): CartRepository {
}