package org.khjin.hhplusecommerce.infrastructure.product

import org.springframework.data.jpa.repository.JpaRepository

interface ProductJpaRepository: JpaRepository<Long, ProductEntity> {
}