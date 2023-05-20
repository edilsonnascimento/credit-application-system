package me.dio.ednascimento.creditapplicationsystem.repositoy

import me.dio.ednascimento.creditapplicationsystem.entity.Customer
import org.springframework.data.jpa.repository.JpaRepository

interface CustomerRepository: JpaRepository<Customer, Long> {
}