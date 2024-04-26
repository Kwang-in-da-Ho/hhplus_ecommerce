package org.khjin.hhplusecommerce.infrastructure.payment

import jakarta.persistence.*
import org.khjin.hhplusecommerce.infrastructure.order.OrderEntity
import java.time.LocalDateTime

@Entity
@Table(name = "payment")
class PaymentEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long?,
    @OneToOne
    @JoinColumn(name = "order_id")
    val orderEntity: OrderEntity,
    val paymentMethod: PaymentMethod,
    val payAmount: Long,
    val payDatetime: LocalDateTime,
    val payStatus: PayStatus,
) {
}

enum class PayStatus {
    IN_PROGRESS, FAILED, COMPLETED, CANCELLED
}

enum class PaymentMethod {
    CARD, TRANSFER
}
