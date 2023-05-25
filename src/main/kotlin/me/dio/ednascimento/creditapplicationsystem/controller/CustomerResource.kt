package me.dio.ednascimento.creditapplicationsystem.controller

import jakarta.validation.Valid
import me.dio.ednascimento.creditapplicationsystem.dto.*
import me.dio.ednascimento.creditapplicationsystem.service.impl.CustomerService
import org.springframework.http.*
import org.springframework.http.HttpStatus.*
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/customers")
class CustomerResource(
    private val customerService: CustomerService
) {

    @PostMapping
    fun saveCustomer(@RequestBody @Valid customerDto: CustomerDto): ResponseEntity<CustomerView> {
        val saveCustomer = customerService.save(customerDto.toEntity())
        return ResponseEntity.status(CREATED).body(CustomerView(saveCustomer))
    }

    @GetMapping("/{id}")
    fun findById(@PathVariable id: Long): ResponseEntity<CustomerView> {
        val customer = customerService.findById(id)
        return ResponseEntity.status(OK).body(CustomerView(customer))
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(NO_CONTENT)
    fun deleteCustomer(@PathVariable id: Long) =
        customerService.delete(id)

    @PatchMapping
    fun updateCustomer(
        @RequestParam(value = "customerId") id: Long,
        @RequestBody @Valid customerUpdateDto: CustomerUpdatedDto
    ): ResponseEntity<CustomerView> {
        val customer = customerService.findById(id)
        val customerToUpdate = customerUpdateDto.toEntity(customer)
        val customerUpdated = customerService.save(customerToUpdate)
        return ResponseEntity.status(OK).body(CustomerView(customerUpdated))
    }
}