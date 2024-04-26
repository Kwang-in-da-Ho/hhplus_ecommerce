package org.khjin.hhplusecommerce.infrastructure.product

import jakarta.persistence.*

@Entity
@Table(name = "product")
class ProductEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long?,
    val productName: String,
    val category: ProductCategory,
    val price: Long,
) {
}

enum class ProductCategory {
    SHIRT, SWEATER, JACKET, JEANS, SLACKS, BAG, OTHER
}
