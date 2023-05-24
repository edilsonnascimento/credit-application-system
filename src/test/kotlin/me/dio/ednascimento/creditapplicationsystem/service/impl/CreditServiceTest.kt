package me.dio.ednascimento.creditapplicationsystem.service.impl

import io.mockk.every
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import io.mockk.junit5.MockKExtension
import io.mockk.verify
import me.dio.ednascimento.creditapplicationsystem.entity.Address
import me.dio.ednascimento.creditapplicationsystem.entity.Credit
import me.dio.ednascimento.creditapplicationsystem.entity.Customer
import me.dio.ednascimento.creditapplicationsystem.enumeration.Status
import me.dio.ednascimento.creditapplicationsystem.exception.BusinessException
import me.dio.ednascimento.creditapplicationsystem.repository.CreditRepository
import org.assertj.core.api.Assertions
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.ArgumentMatchers.anyLong
import org.springframework.test.context.ActiveProfiles
import java.math.BigDecimal
import java.time.LocalDate
import java.util.*

@ActiveProfiles("test")
@ExtendWith(MockKExtension::class)
class CreditServiceTest {

    @MockK lateinit var customerService: CustomerService
    @MockK lateinit var repository: CreditRepository
    @InjectMockKs lateinit var service: CreditService


    @Test
    fun `SHOULD create credit`(){
        //given
        val idFake = Random().nextLong()
        val creditFake = buildCredit(id = idFake)
        val customerFake = buildCustomer()
        every { customerService.findById(creditFake.customer?.id!!) } returns customerFake
        every { repository.save(any()) } returns creditFake

        //when
        val actual = service.save(creditFake)

        //then
        assertThat(actual).isNotNull
        assertThat(actual).isSameAs(creditFake)
        verify(exactly = 1) { customerService.findById(creditFake.customer?.id!!) }
        verify(exactly = 1) { repository.save(creditFake) }
    }

    @Test
    fun `SHOULD find All By Customers`(){
        //given
        val fakeId = Random().nextLong()
        val creditFake1 = buildCredit(id = 1L)
        val creditFake2 = buildCredit(id = 2L)
        val creditsFake = mutableListOf(creditFake1, creditFake2)
        every { repository.findAllByCustomersId(fakeId) } returns creditsFake

        //when
        val actual = service.findAllByCustomers(fakeId)

        //then
        assertThat(actual).isNotNull
        assertThat(actual).isSameAs(creditsFake)
        assertThat(actual).containsExactlyInAnyOrderElementsOf(creditsFake)
        verify(exactly = 1) { repository.findAllByCustomersId(fakeId) }
    }

    @Test
    fun `SHOULD find By Credit Code`(){
        //given
        val fakeId = Random().nextLong()
        val creditCodeFake = UUID.randomUUID()
        val customerFake = buildCustomer(id = fakeId)
        val creditFake = buildCredit(customer = customerFake)
        every { repository.findByCreditCode(creditCodeFake) } returns creditFake

        //when
        val actual = service.findByCreditCode(fakeId, creditCodeFake)

        //then
        assertThat(actual).isNotNull
        assertThat(actual).isSameAs(creditFake)
        verify(exactly = 1) { repository.findByCreditCode(creditCodeFake) }
    }

    @Test
    fun `SHOULD find By Credit Code by invalid id and returns null throw IllegalArgumentException`(){
        val fakeId = Random().nextLong()
        val creditCodeFake = UUID.randomUUID()
        val creditFake = buildCredit()
        every { repository.findByCreditCode(creditCodeFake) } returns creditFake

        //when
        //then
        Assertions.assertThatExceptionOfType(IllegalArgumentException::class.java)
            .isThrownBy {service.findByCreditCode(fakeId, creditCodeFake)}
            .withMessage("Contact admin")
        verify(exactly = 1) { repository.findByCreditCode(creditCodeFake) }
    }

    private fun buildCredit(
        id: Long? = 1L,
        creditCode: UUID = UUID.randomUUID(),
        creditValue: BigDecimal = BigDecimal.ZERO,
        dayFirstInstallment: LocalDate = LocalDate.now(),
        numberOfInstallments: Int = 0,
        status: Status = Status.IN_PROGRESS,
        customer: Customer? = buildCustomer()
    ) = Credit(
        id = id,
        creditCode = creditCode,
        creditValue = creditValue,
        dayFirstInstallment = dayFirstInstallment,
        numberOfInstallments = numberOfInstallments,
        status = status,
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