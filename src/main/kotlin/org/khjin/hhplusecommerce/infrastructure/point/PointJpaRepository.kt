package org.khjin.hhplusecommerce.infrastructure.point

import org.springframework.data.jpa.repository.JpaRepository

interface PointJpaRepository: JpaRepository<Long, PointEntity> {
}