package me.dio.ednascimento.creditapplicationsystem


import io.mockk.junit5.MockKExtension
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.test.context.ActiveProfiles

@ActiveProfiles("test")
@ExtendWith(MockKExtension::class)
class CreditApplicationSystemApplicationTests {

	@Test
	fun contextLoads() {
	}

}
