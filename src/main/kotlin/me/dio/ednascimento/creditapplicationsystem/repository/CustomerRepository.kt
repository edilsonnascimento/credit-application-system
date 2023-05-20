package me.dio.ednascimento.creditapplicationsystem.repository

import me.dio.ednascimento.creditapplicationsystem.entity.Customer
import org.springframework.data.jpa.repository.JpaRepository

interface CustomerRepository: JpaRepository<Customer, Long> {
}