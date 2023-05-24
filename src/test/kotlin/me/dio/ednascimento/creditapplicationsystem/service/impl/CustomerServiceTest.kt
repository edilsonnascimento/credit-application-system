package me.dio.ednascimento.creditapplicationsystem.service.impl

import io.mockk.every
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import io.mockk.junit5.MockKExtension
import io.mockk.just
import io.mockk.runs
import io.mockk.verify
import me.dio.ednascimento.creditapplicationsystem.entity.Address
import me.dio.ednascimento.creditapplicationsystem.entity.Customer
import me.dio.ednascimento.creditapplicationsystem.exception.BusinessException
import me.dio.ednascimento.creditapplicationsystem.repository.CustomerRepository
import me.dio.ednascimento.creditapplicationsystem.repository.CustomerRepositoryTest
import org.assertj.core.api.Assertions
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.test.context.ActiveProfiles
import java.math.BigDecimal
import java.util.*

@ActiveProfiles("test")
@ExtendWith(MockKExtension::class)
class CustomerServiceTest {

    @MockK
    lateinit var repository: CustomerRepository
    @InjectMockKs
    lateinit var service: CustomerService

    @Test
    fun `SHOULD create customer`(){
        //given
        val fakeCustomer: Customer = buildCustomer()
        every { repository.save(any()) } returns fakeCustomer

        //when
        val actual: Customer = service.save(fakeCustomer)

        //then
        assertThat(actual).isNotNull
        assertThat(actual).isSameAs(fakeCustomer)
        verify(exactly = 1) { repository.save(fakeCustomer) }
    }

    @Test
    fun `SHOULD find customer by id`() {
        // given
        val fakeId = Random().nextLong()
        val fakeCustomer = buildCustomer(id = fakeId)
        every { repository.findById(fakeId) } returns  Optional.of(fakeCustomer)

        // when
        val actual = service.findById(fakeId)

        // then
        assertThat(actual).isNotNull
        assertThat(actual).isExactlyInstanceOf(Customer::class.java)
        assertThat(actual).isSameAs(fakeCustomer)
        verify(exactly = 1) { repository.findById(fakeId) }
    }

    @Test
    fun `SHOULD not find customer by invalid id and throw BusinessException`() {
        // given
        val fakeId = Random().nextLong()
        every { repository.findById(fakeId) } returns  Optional.empty()

        // then
        Assertions.assertThatExceptionOfType(BusinessException::class.java)
            .isThrownBy { service.findById(fakeId) }
            .withMessage("Id $fakeId not found")
        verify(exactly = 1) { repository.findById(fakeId) }
    }

    @Test
    fun `SHOULD delete customer by id`() {
        // given
        val fakeId = Random().nextLong()
        val fakeCustomer = buildCustomer(id = fakeId)
        every { repository.findById(fakeId) } returns  Optional.of(fakeCustomer)
        every { repository.delete(fakeCustomer)} just runs

        // when
        service.delete(fakeId)

        // then
        verify(exactly = 1) { repository.findById(fakeId) }
        verify(exactly = 1) { repository.delete(fakeCustomer) }
    }
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