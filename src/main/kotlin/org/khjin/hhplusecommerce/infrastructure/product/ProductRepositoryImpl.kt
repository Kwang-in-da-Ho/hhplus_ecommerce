package org.khjin.hhplusecommerce.infrastructure.product

import org.khjin.hhplusecommerce.domain.product.ProductRepository
import org.springframework.stereotype.Repository

@Repository
class ProductRepositoryImpl(
    private val productJpaRepository: ProductJpaRepository
): ProductRepository {
}