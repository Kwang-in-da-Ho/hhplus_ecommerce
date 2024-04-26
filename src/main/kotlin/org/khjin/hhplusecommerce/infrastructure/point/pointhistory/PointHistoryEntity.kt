package org.khjin.hhplusecommerce.infrastructure.point.pointhistory

import jakarta.persistence.*
import org.khjin.hhplusecommerce.infrastructure.customer.CustomerEntity
import java.time.LocalDateTime

@Entity
@Table(name = "point_history")
class PointHistoryEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long?,
    @ManyToOne
    @JoinColumn(name = "customer_id")
    val customerEntity: CustomerEntity,
    val amount: Long,
    val pointHistoryType: PointHistoryType,
    val occurDatetime: LocalDateTime,
) {
}

enum class PointHistoryType {
    CHARGE, USE
}
