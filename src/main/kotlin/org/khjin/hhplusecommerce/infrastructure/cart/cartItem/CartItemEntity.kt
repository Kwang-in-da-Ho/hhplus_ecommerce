package org.khjin.hhplusecommerce.infrastructure.cart.cartItem

import jakarta.persistence.*
import org.khjin.hhplusecommerce.infrastructure.cart.CartEntity

@Entity
@Table(name = "cart_item")
class CartItemEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long,
    @ManyToOne
    @JoinColumn(name = "cart_id")
    val cart: CartEntity,
    val productId: Long,
    val quantity: Long,
) {
}