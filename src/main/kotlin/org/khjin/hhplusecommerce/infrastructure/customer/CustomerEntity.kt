package org.khjin.hhplusecommerce.infrastructure.customer

import jakarta.persistence.*

@Entity
@Table(name = "customer")
class CustomerEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long,
    val name: String
) {
}