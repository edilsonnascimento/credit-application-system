package me.dio.ednascimento.creditapplicationsystem.dto

import me.dio.ednascimento.creditapplicationsystem.entity.Customer
import java.math.BigDecimal

data class CustomerUpdatedDto(
    val firstName: String,
    val lastName: String,
    val income: BigDecimal,
    val zipCode: String,
    val street: String
) {
    fun toEntity(customer: Customer): Customer{
        customer.firstName = this.firstName
        customer.lastName = this.lastName
        customer.income = this.income
        customer.address.zipCode = this.zipCode
        customer.address.street = this.street
        return customer
    }
}
