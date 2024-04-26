package org.khjin.hhplusecommerce.infrastructure.order

import jakarta.persistence.*
import org.khjin.hhplusecommerce.infrastructure.order.orderitem.OrderItemEntity

enum class OrderStatus {
    IN_PROGRESS, CANCELLED, COMPLETE
}

@Entity
@Table(name = "order")
class OrderEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long,
    val customerId: Long,
    val orderStatus: OrderStatus,
    val totalPrice: Long,

    @OneToMany
    val orderItems: List<OrderItemEntity>,
) {
}