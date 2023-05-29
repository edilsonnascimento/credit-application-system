package me.dio.ednascimento.creditapplicationsystem.controller

import com.fasterxml.jackson.databind.ObjectMapper
import me.dio.ednascimento.creditapplicationsystem.dto.CustomerDto
import me.dio.ednascimento.creditapplicationsystem.dto.CustomerUpdatedDto
import me.dio.ednascimento.creditapplicationsystem.repository.CustomerRepository
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.http.MediaType.APPLICATION_JSON
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.context.ContextConfiguration
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*
import org.springframework.test.web.servlet.result.MockMvcResultHandlers
import org.springframework.test.web.servlet.result.MockMvcResultHandlers.*
import org.springframework.test.web.servlet.result.MockMvcResultMatchers
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.*
import java.math.BigDecimal

@SpringBootTest
@ActiveProfiles("test")
@AutoConfigureMockMvc
@ContextConfiguration
class CustomerResourceTest {
    @Autowired private lateinit var repository: CustomerRepository
    @Autowired private lateinit var mockMvc: MockMvc
    @Autowired private lateinit var objectMapper: ObjectMapper

    companion object {
        const val URL = "/api/customers"
    }

    @BeforeEach
    fun setup() = repository.deleteAll()

    @AfterEach
    fun tearEach() = repository.deleteAll()

    @Test
    fun `SHOULD create a customer and return 201 status`() {
        // given
        val customerDto = builderCustomerDto()
        val customerDtoJson = objectMapper.writeValueAsString(customerDto)

        // when
        mockMvc.perform(post(URL)
            .contentType(APPLICATION_JSON)
            .content(customerDtoJson))

        //then
            .andExpect(status().isCreated)
            .andExpect(jsonPath("$.firstName").value("Cami"))
            .andExpect(jsonPath("$.lastName").value("Cavalcante"))
            .andExpect(jsonPath("$.cpf").value("28475934625"))
            .andExpect(jsonPath("$.email").value("camila@email.com"))
            .andExpect(jsonPath("$.income").value("1000.0"))
            .andExpect(jsonPath("$.zipCode").value("000000"))
            .andExpect(jsonPath("$.street").value("Rua da Cami, 123"))
            .andExpect(jsonPath("$.id").value(1))
            .andDo(print())
    }

    @Test
    fun `SHOULD not save a customer with same CPF and return 409 status`() {
        //given
        repository.save(builderCustomerDto().toEntity())
        val customerDto: CustomerDto = builderCustomerDto()
        val valueAsString: String = objectMapper.writeValueAsString(customerDto)

        //when
        mockMvc.perform(post(URL)
                .contentType(APPLICATION_JSON)
                .content(valueAsString)
        )
        //then
            .andExpect(status().isConflict)
            .andExpect(jsonPath("$.title").value("Conflict! Consult the documentation"))
            .andExpect(jsonPath("$.timestamp").exists())
            .andExpect(jsonPath("$.status").value(409))
            .andExpect(jsonPath("$.exception").value("DataIntegrityViolationException"))
            .andExpect(jsonPath("$.details[*]").isNotEmpty)
            .andDo(print())
    }

    @Test
    fun `SHOULD not save a customer with empty firstName and return 400 status`() {
        //given
        val customerDto: CustomerDto = builderCustomerDto(firstName = "")
        val valueAsString: String = objectMapper.writeValueAsString(customerDto)

        //when
        mockMvc.perform(post(URL)
                .content(valueAsString)
                .contentType(APPLICATION_JSON))
        //then
            .andExpect(status().isBadRequest)
            .andExpect(jsonPath("$.title").value("Bad Request! Consult the documentation"))
            .andExpect(jsonPath("$.timestamp").exists())
            .andExpect(jsonPath("$.status").value(400))
            .andExpect(jsonPath("$.exception").value("MethodArgumentNotValidException"))
            .andExpect(jsonPath("$.details[*]").isNotEmpty)
            .andDo(print())
    }

    @Test
    fun `should find customer by id and return 200 status`() {
        //given
        val customer = repository.save(builderCustomerDto().toEntity())

        //when
        mockMvc.perform(get("$URL/${customer.id}")
                .accept(APPLICATION_JSON)
        )
        //then
            .andExpect(status().isOk)
            .andExpect(jsonPath("$.firstName").value("Cami"))
            .andExpect(jsonPath("$.lastName").value("Cavalcante"))
            .andExpect(jsonPath("$.cpf").value("28475934625"))
            .andExpect(jsonPath("$.email").value("camila@email.com"))
            .andExpect(jsonPath("$.income").value("1000.0"))
            .andExpect(jsonPath("$.zipCode").value("000000"))
            .andExpect(jsonPath("$.street").value("Rua da Cami, 123"))
            //.andExpect(MockMvcResultMatchers.jsonPath("$.id").value(1))
            .andDo(print())
    }

    @Test
    fun `should not find customer with invalid id and return 400 status`() {
        //given
        val invalidId: Long = 2L
        //when
        mockMvc.perform(get("$URL/$invalidId")
                .accept(APPLICATION_JSON)
        )
        //then
            .andExpect(status().isBadRequest)
            .andExpect(jsonPath("$.title").value("Bad Request! Consult the documentation"))
            .andExpect(jsonPath("$.timestamp").exists())
            .andExpect(jsonPath("$.status").value(400))
            .andExpect(jsonPath("$.exception").value("BusinessException"))
            .andExpect(jsonPath("$.details[*]").isNotEmpty)
            .andDo(print())
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

    private fun builderCustomerUpdateDto(
        firstName: String = "CamiUpdate",
        lastName: String = "CavalcanteUpdate",
        income: BigDecimal = BigDecimal.valueOf(5000.0),
        zipCode: String = "45656",
        street: String = "Rua Updated"
    ): CustomerUpdatedDto = CustomerUpdatedDto(
        firstName = firstName,
        lastName = lastName,
        income = income,
        zipCode = zipCode,
        street = street
    )
}