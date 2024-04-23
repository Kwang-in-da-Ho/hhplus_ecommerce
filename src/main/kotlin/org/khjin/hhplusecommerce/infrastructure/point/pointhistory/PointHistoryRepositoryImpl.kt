package org.khjin.hhplusecommerce.infrastructure.point.pointhistory

import org.khjin.hhplusecommerce.domain.point.pointhistory.PointHistoryRepository
import org.springframework.stereotype.Repository

@Repository
class PointHistoryRepositoryImpl(
    private val pointHistoryJpaRepository: PointHistoryJpaRepository
): PointHistoryRepository {
}