package org.khjin.hhplusecommerce.infrastructure.payment

import org.springframework.data.jpa.repository.JpaRepository

interface PaymentJpaRepository: JpaRepository<Long, PaymentEntity>{
}