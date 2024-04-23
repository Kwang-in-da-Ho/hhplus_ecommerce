package org.khjin.hhplusecommerce.infrastructure.product.productinventory

import org.khjin.hhplusecommerce.domain.product.productinventory.ProductInventoryRepository
import org.springframework.stereotype.Repository

@Repository
class ProductInventoryRepositoryImpl(
    private val productInventoryJpaRepository: ProductInventoryJpaRepository
): ProductInventoryRepository {
}