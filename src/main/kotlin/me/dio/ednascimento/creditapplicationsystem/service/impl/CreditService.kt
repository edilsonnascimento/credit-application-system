package me.dio.ednascimento.creditapplicationsystem.service.impl

import me.dio.ednascimento.creditapplicationsystem.entity.Credit
import me.dio.ednascimento.creditapplicationsystem.exception.BusinessException
import me.dio.ednascimento.creditapplicationsystem.repository.CreditRepository
import me.dio.ednascimento.creditapplicationsystem.service.ICreditService
import org.springframework.stereotype.Service
import java.lang.IllegalArgumentException
import java.util.*

@Service
class CreditService(
    private val creditRepository: CreditRepository,
    private val custumerService: CustomerService
): ICreditService {

    override fun save(credit: Credit): Credit {
        credit.apply {
            custumerService.findById(credit.customer?.id!!)
        }
        return creditRepository.save(credit)
    }

    override fun findAllByCustomers(customerId: Long): List<Credit> =
        creditRepository.findAllByCustomersId(customerId)

    override fun findByCreditCode(customerId: Long, creditCode: UUID): Credit {
        val credit: Credit = creditRepository.findByCreditCode(creditCode)
            ?: throw BusinessException("CreditCode $creditCode not found")
        return if(credit.customer?.id == customerId) credit else throw IllegalArgumentException("Contact admin")
    }
}