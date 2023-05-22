package me.dio.ednascimento.creditapplicationsystem.service.impl

import me.dio.ednascimento.creditapplicationsystem.entity.Customer
import me.dio.ednascimento.creditapplicationsystem.exception.BusinessException
import me.dio.ednascimento.creditapplicationsystem.repository.CustomerRepository
import me.dio.ednascimento.creditapplicationsystem.service.ICustomerService
import org.springframework.stereotype.Service

@Service
class CustomerService(
    private val customerRepository: CustomerRepository
): ICustomerService {
    override fun save(customer: Customer): Customer =
        customerRepository.save(customer)

    override fun findById(id: Long): Customer =
        customerRepository
            .findById(id)
            .orElseThrow {throw BusinessException("Id $id not found") }

    override fun delete(id: Long) {
        val customer = findById(id)
        customerRepository.delete(customer)
    }
}