package me.dio.ednascimento.creditapplicationsystem.controller

import me.dio.ednascimento.creditapplicationsystem.dto.*
import me.dio.ednascimento.creditapplicationsystem.service.impl.CustomerService
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/customers")
class CustomerResource(
    private val customerService: CustomerService
) {

    @PostMapping
    fun saveCustomer(@RequestBody customerDto: CustomerDto): String {
        val saveCustomer = customerService.save(customerDto.toEntity())
        return "Custormer ${saveCustomer.email} saved!"
    }

    @GetMapping("/{id}")
    fun findById(@PathVariable id: Long): CustomerView {
        val customer = customerService.findById(id)
        return CustomerView(customer)
    }
}