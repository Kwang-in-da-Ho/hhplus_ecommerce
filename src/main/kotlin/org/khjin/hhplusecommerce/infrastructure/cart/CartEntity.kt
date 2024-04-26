package org.khjin.hhplusecommerce.infrastructure.cart

import jakarta.persistence.*
import org.khjin.hhplusecommerce.infrastructure.cart.cartItem.CartItemEntity
import org.khjin.hhplusecommerce.infrastructure.customer.CustomerEntity

@Entity
@Table(name = "cart")
class CartEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long?,
    @OneToOne
    @JoinColumn(name = "customer_id")
    val customer: CustomerEntity,
    @OneToMany
    val cartItems: List<CartItemEntity>
) {
}