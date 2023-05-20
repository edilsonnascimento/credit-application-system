package me.dio.ednascimento.creditapplicationsystem.service

import me.dio.ednascimento.creditapplicationsystem.entity.Customer

interface ICustomerService {
    fun save(customer: Customer) : Customer
    fun findById(id: Long) : Customer
    fun delete(id: Long): Customer
}