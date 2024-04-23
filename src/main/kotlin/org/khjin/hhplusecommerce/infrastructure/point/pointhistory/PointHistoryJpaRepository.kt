package org.khjin.hhplusecommerce.infrastructure.point.pointhistory

import org.springframework.data.jpa.repository.JpaRepository

interface PointHistoryJpaRepository: JpaRepository<Long, PointHistoryEntity> {
}