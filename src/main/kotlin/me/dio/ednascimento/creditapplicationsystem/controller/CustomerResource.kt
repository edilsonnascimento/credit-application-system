package me.dio.ednascimento.creditapplicationsystem.controller

import me.dio.ednascimento.creditapplicationsystem.dto.CustomerDto
import me.dio.ednascimento.creditapplicationsystem.service.impl.CustomerService
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/customers")
class CustomerResource(
    private val customerService: CustomerService
) {

    @PostMapping
    fun saveCustomer(@RequestBody customerDto: CustomerDto) : String {
        val saveCustomer = customerService.save(customerDto.toEntity())
        return "Custormer ${saveCustomer.email} saved!"
    }
}