package org.khjin.hhplusecommerce.infrastructure.point

import org.khjin.hhplusecommerce.domain.point.PointRepository
import org.springframework.stereotype.Repository

@Repository
class PointRepositoryImpl(
    private val pointJpaRepository: PointJpaRepository
): PointRepository {
}