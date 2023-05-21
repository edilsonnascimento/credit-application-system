package me.dio.ednascimento.creditapplicationsystem.dto

import me.dio.ednascimento.creditapplicationsystem.entity.Address
import me.dio.ednascimento.creditapplicationsystem.entity.Customer
import java.math.BigDecimal

data class CustomerDto(
    val firstName: String,
    val lastName: String,
    val cpf: String,
    val email: String,
    val income: BigDecimal,
    val password: String,
    val zipCode: String,
    val street: String
) {
    fun toEntity(): Customer = Customer(
        firstName = this.firstName,
        lastName = this.lastName,
        cpf = this.cpf,
        email = this.email,
        income = this.income,
        password = this.password,
        address = Address(
            zipCode = this.zipCode,
            street = this.street
        )
    )
}
