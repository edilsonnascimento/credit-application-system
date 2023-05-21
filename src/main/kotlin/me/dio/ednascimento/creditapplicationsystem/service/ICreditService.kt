package me.dio.ednascimento.creditapplicationsystem.service

import me.dio.ednascimento.creditapplicationsystem.entity.Credit
import java.util.*

interface ICreditService {
    fun save(credit: Credit) : Credit
    fun findAllByCustomers(customerId: Long): List<Credit>
    fun findByCreditCode(customerId: Long, creditCode: UUID): Credit
}