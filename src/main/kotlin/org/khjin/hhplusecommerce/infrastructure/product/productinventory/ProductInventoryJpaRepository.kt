package org.khjin.hhplusecommerce.infrastructure.product.productinventory

import org.springframework.data.jpa.repository.JpaRepository

interface ProductInventoryJpaRepository: JpaRepository<Long, ProductInventoryEntity> {
}