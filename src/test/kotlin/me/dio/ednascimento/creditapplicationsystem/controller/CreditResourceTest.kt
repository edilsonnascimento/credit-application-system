package me.dio.ednascimento.creditapplicationsystem.controller

import com.fasterxml.jackson.databind.ObjectMapper
import me.dio.ednascimento.creditapplicationsystem.dto.CreditDto
import me.dio.ednascimento.creditapplicationsystem.dto.CustomerDto
import me.dio.ednascimento.creditapplicationsystem.repository.CreditRepository
import me.dio.ednascimento.creditapplicationsystem.repository.CustomerRepository
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType.*
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.context.ContextConfiguration
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import org.springframework.test.web.servlet.result.MockMvcResultHandlers
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.*
import java.math.BigDecimal
import java.time.LocalDate

@SpringBootTest
@ActiveProfiles("test")
@AutoConfigureMockMvc
@ContextConfiguration
class CreditResourceTest {
    @Autowired private lateinit var repository: CreditRepository
    @Autowired private lateinit var custormerRepository: CustomerRepository
    @Autowired private lateinit var mockMvc: MockMvc
    @Autowired private lateinit var objectMapper: ObjectMapper

    companion object {
        const val URL = "/api/credits"
    }

    @BeforeEach
    fun setup() = repository.deleteAll()

    @AfterEach
    fun tearEach() = repository.deleteAll()

    @Test
    fun `SHOULD create a credit and return 201 status`() {
        // given
        val customer = custormerRepository.save(builderCustomerDto().toEntity())
        val creditDto = builderCreditDto()
        val creditDtoJson = objectMapper.writeValueAsString(creditDto)

        // when
        mockMvc.perform(post(URL)
            .contentType(APPLICATION_JSON)
            .content(creditDtoJson))

        //then
            .andExpect(status().isCreated)
            .andExpect(jsonPath("$.creditCode").exists())
            .andExpect(jsonPath("$.creditValue").value("1000"))
            .andExpect(jsonPath("$.numberOfInstallments").value("2"))
            .andExpect(jsonPath("$.status").value("IN_PROGRESS"))
            .andExpect(jsonPath("$.emailCustomer").value(""))
            .andExpect(jsonPath("$.incomeCustomer").value("0"))
            .andDo(MockMvcResultHandlers.print())
    }

    private fun builderCustomerDto(
        firstName: String = "Cami",
        lastName: String = "Cavalcante",
        cpf: String = "28475934625",
        email: String = "camila@email.com",
        income: BigDecimal = BigDecimal.valueOf(1000.0),
        password: String = "1234",
        zipCode: String = "000000",
        street: String = "Rua da Cami, 123",
    ) = CustomerDto(
        firstName = firstName,
        lastName = lastName,
        cpf = cpf,
        email = email,
        income = income,
        password = password,
        zipCode = zipCode,
        street = street
    )
    private fun builderCreditDto(
        creditValue: BigDecimal = BigDecimal.valueOf(1000),
        dayFirstOfInstallment: LocalDate = LocalDate.of(2023, 6,2),
        numberOfInstallments: Int = 2,
        customerId: Long = 1L
    ) = CreditDto(
        creditValue = creditValue,
        dayFirstOfInstallment = dayFirstOfInstallment,
        numberOfInstallments = numberOfInstallments,
        customerId = customerId)
}