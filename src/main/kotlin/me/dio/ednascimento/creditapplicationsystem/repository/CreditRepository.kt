package me.dio.ednascimento.creditapplicationsystem.repository

import me.dio.ednascimento.creditapplicationsystem.entity.Credit
import org.springframework.data.jpa.repository.JpaRepository

interface CreditRepository: JpaRepository<Credit, Long> {
}