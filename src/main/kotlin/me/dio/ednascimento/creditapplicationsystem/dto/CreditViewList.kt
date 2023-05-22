package me.dio.ednascimento.creditapplicationsystem.dto

import me.dio.ednascimento.creditapplicationsystem.entity.Credit
import java.math.BigDecimal
import java.util.UUID

data class CreditViewList(
    val creditCode: UUID,
    val creditValue: BigDecimal,
    val numerOfInstallments: Int
) {
    constructor(credit: Credit): this (
        creditCode = credit.creditCode,
        creditValue = credit.creditValue,
        numerOfInstallments = credit.numberOfInstallments
    )
}
