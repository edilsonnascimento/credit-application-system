package me.dio.ednascimento.creditapplicationsystem.repository

import me.dio.ednascimento.creditapplicationsystem.entity.Address
import me.dio.ednascimento.creditapplicationsystem.entity.Credit
import me.dio.ednascimento.creditapplicationsystem.entity.Customer
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager
import org.springframework.test.context.ActiveProfiles
import java.math.BigDecimal
import java.time.LocalDate
import java.time.Month
import java.util.*


@ActiveProfiles("test")
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class CreditRepositoryTest {

    @Autowired lateinit var repository: CreditRepository
    @Autowired lateinit var testEntityManager: TestEntityManager

    private lateinit var customer: Customer
    private lateinit var credit1: Credit
    private lateinit var credit2: Credit

    @BeforeEach
    fun setup() {
        customer = testEntityManager.persist(buildCustomer())
        credit1 = testEntityManager.persist(buildCredit(customer = customer))
        credit2 = testEntityManager.persist(buildCredit(customer = customer))
    }

    @Test
    fun `SHOULD find credit by credit code`() {
        // given
        var creditCode1 = UUID.fromString("dc8a99da-829f-4855-ade8-a9a0e7f606a8")
        var creditCode2 = UUID.fromString("d91af325-e030-43d5-bb74-b54df11be766")
        credit1.creditCode = creditCode1
        credit2.creditCode = creditCode2

        // when
        val creditFake1 = repository.findByCreditCode(creditCode1)!!
        val creditFake2 = repository.findByCreditCode(creditCode2)!!

        // then
        assertThat(creditFake1).isNotNull
        assertThat(creditFake2).isNotNull
        assertThat(creditFake1).isSameAs(credit1)
        assertThat(creditFake2).isSameAs(credit2)
    }

    @Test
    fun `SHOULD find all credits by customer id`() {
        // given
        var custmerId = 1L

        // when
        val credits = repository.findAllByCustomersId(custmerId)

        // then
        assertThat(credits).isNotEmpty
        assertThat(credits.size).isEqualTo(2)
        assertThat(credits).contains(credit1, credit2)

    }
    private fun buildCredit(
        creditValue: BigDecimal = BigDecimal.valueOf(500.0),
        dayFirstInstallment: LocalDate = LocalDate.of(2023, Month.APRIL, 22),
        numberOfInstallments: Int = 5,
        customer: Customer
    ): Credit = Credit(
        creditValue = creditValue,
        dayFirstInstallment = dayFirstInstallment,
        numberOfInstallments = numberOfInstallments,
        customer = customer
    )
    private fun buildCustomer(
        firstName: String = "Cami",
        lastName: String = "Cavalcante",
        cpf: String = "28475934625",
        email: String = "camila@gmail.com",
        password: String = "12345",
        zipCode: String = "12345",
        street: String = "Rua da Cami",
        income: BigDecimal = BigDecimal.valueOf(1000.0),
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
    )
}