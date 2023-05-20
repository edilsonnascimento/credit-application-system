package me.dio.ednascimento.creditapplicationsystem.repositoy

import me.dio.ednascimento.creditapplicationsystem.entity.Credit
import org.springframework.data.jpa.repository.JpaRepository

interface CreditRepository: JpaRepository<Credit, Long> {
}