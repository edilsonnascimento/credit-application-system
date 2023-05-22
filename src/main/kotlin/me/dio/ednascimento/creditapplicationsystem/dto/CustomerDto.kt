package me.dio.ednascimento.creditapplicationsystem.dto

import jakarta.validation.constraints.Email
import jakarta.validation.constraints.NotEmpty
import jakarta.validation.constraints.NotNull
import me.dio.ednascimento.creditapplicationsystem.entity.Address
import me.dio.ednascimento.creditapplicationsystem.entity.Customer
import org.hibernate.validator.constraints.br.CPF
import java.math.BigDecimal

data class CustomerDto(
    @field:NotEmpty(message = "Invalid input")
    val firstName: String,

    @field:NotEmpty(message = "Invalid input")
    val lastName: String,

    @field:CPF(message = "This invalid CPF")
    val cpf: String,

    @field:Email(message = "This invalid email")
    val email: String,

    @field:NotNull(message = "Invalid input")
    val income: BigDecimal,

    @field:NotEmpty(message = "Invalid input")
    val password: String,

    @field:NotEmpty(message = "Invalid input")
    val zipCode: String,

    @field:NotEmpty(message = "Invalid input")
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
