package me.dio.ednascimento.creditapplicationsystem.repository

import io.mockk.verify
import me.dio.ednascimento.creditapplicationsystem.entity.Address
import me.dio.ednascimento.creditapplicationsystem.entity.Customer
import org.assertj.core.api.Assertions
import org.assertj.core.api.Assertions.assertThatExceptionOfType
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager
import org.springframework.dao.DataIntegrityViolationException
import org.springframework.test.context.ActiveProfiles
import java.lang.Exception
import java.math.BigDecimal

@ActiveProfiles("test")
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class CustomerRepositoryTest {

    @Autowired lateinit var repository: CustomerRepository
    @Autowired lateinit var testEntityManager: TestEntityManager

    @Test
    fun `NOT SHOULD save custumer email duplicated`() {
        // given
        repository.save(buildCustomer(id=1L))

        // when
        assertThatExceptionOfType(DataIntegrityViolationException::class.java)
            .isThrownBy {repository.save(buildCustomer(id=2L))}
            .withMessageContaining("CPF")

        // then

    }

    private fun buildCustomer(
        firstName: String = "Edilson",
        lastName: String = "Nascimento",
        cpf: String = "28475934625",
        email: String = "edilsona@gmail.com",
        password: String = "12345",
        zipCode: String = "12345",
        street: String = "Rua da Edi",
        income: BigDecimal = BigDecimal.valueOf(1000.0),
        id: Long = 1L
    ) = Customer(
        firstName = firstName,
        lastName = lastName,
        cpf = cpf,
        email = email,
        password = password,
        address = Address(
            zipCode = zipCode,
            street = street,
        ),
        income = income,
        id = id
    )

}