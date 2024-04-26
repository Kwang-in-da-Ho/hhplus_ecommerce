package org.khjin.hhplusecommerce.infrastructure.product.productinventory

import jakarta.persistence.*
import org.khjin.hhplusecommerce.infrastructure.product.ProductEntity
import java.time.LocalDateTime

@Entity
@Table(name = "product_inventory")
class ProductInventoryEntity(
    @Id
    @OneToOne
    @JoinColumn(name = "product_id")
    val productEntity: ProductEntity,
    val amount: Long,
    val lastUpdated: LocalDateTime
) {
}