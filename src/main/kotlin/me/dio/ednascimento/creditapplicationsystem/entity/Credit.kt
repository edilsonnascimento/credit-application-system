package me.dio.ednascimento.creditapplicationsystem.entity

import me.dio.ednascimento.creditapplicationsystem.enumeration.Status
import java.math.BigDecimal
import java.time.LocalDate
import java.util.UUID

data class Credit(
        val creditCode: UUID = UUID.randomUUID(),
        val creditValue: BigDecimal = BigDecimal.ZERO,
        val dayFirstInstallment: LocalDate,
        val numberOfIntallments: Int = 0,
        val status: Status = Status.IN_PROGRESS,
        val customer: Customer? = null,
        val id: Long? = null
)