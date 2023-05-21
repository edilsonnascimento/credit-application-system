package me.dio.ednascimento.creditapplicationsystem.repository

import me.dio.ednascimento.creditapplicationsystem.entity.Credit
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import java.util.UUID

interface CreditRepository: JpaRepository<Credit, Long> {
    fun findByCreditCode(creditCode: UUID): Credit

    @Query(value = "SELECT * FROM credit WHERE id_customer = ?1", nativeQuery = true)
    fun findAllByCustomersId(customerId: Long): List<Credit>
}