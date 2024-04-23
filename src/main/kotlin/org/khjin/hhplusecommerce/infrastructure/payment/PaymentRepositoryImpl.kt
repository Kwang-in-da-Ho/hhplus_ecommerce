package org.khjin.hhplusecommerce.infrastructure.payment

import org.khjin.hhplusecommerce.domain.payment.PaymentRepository
import org.springframework.stereotype.Repository

@Repository
class PaymentRepositoryImpl(
    private val paymentJpaRepository: PaymentJpaRepository
): PaymentRepository {
}