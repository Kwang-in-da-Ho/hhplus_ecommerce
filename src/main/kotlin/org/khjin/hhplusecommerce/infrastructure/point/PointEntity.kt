package org.khjin.hhplusecommerce.infrastructure.point

import jakarta.persistence.*
import org.khjin.hhplusecommerce.infrastructure.customer.CustomerEntity

@Entity
@Table(name = "point")
class PointEntity(
    @Id
    @OneToOne
    @JoinColumn(name = "customer_id")
    val customerEntity: CustomerEntity,
    val amount: Long,
) {
}