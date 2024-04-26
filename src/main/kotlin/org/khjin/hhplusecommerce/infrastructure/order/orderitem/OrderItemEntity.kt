package org.khjin.hhplusecommerce.infrastructure.order.orderitem

import jakarta.persistence.*
import org.khjin.hhplusecommerce.infrastructure.order.OrderEntity

@Entity
@Table(name = "order_item")
class OrderItemEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long,
    @ManyToOne
    @JoinColumn(name = "order_id")
    val orderEntity: OrderEntity,
    @ManyToOne
    @JoinColumn(name = "product_id")
    val productEntity: OrderEntity,
    val quantity: Long,
    val orderItemPrice: Long,
) {
}