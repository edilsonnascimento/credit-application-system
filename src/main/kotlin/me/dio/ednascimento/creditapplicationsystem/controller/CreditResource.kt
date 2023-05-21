package me.dio.ednascimento.creditapplicationsystem.controller

import me.dio.ednascimento.creditapplicationsystem.dto.CreditDto
import me.dio.ednascimento.creditapplicationsystem.service.impl.CreditService
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/credits")
class CreditResource(
    private val creditService: CreditService
) {

    @PostMapping
    fun salveCredit(@RequestBody creditDto: CreditDto): String {
        val credit = creditService.save(creditDto.toEntity())
        return "Credit ${credit.creditCode} - Customer ${credit.customer?.lastName} saved"
    }
}