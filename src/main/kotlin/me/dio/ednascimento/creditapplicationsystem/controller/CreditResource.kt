package me.dio.ednascimento.creditapplicationsystem.controller

import me.dio.ednascimento.creditapplicationsystem.dto.*
import me.dio.ednascimento.creditapplicationsystem.service.impl.CreditService
import org.springframework.web.bind.annotation.*
import java.util.stream.Collectors

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

    @GetMapping
    fun findAllByCustomerId(@RequestParam customerId: Long) = creditService
            .findAllByCustomers(customerId).stream()
            .map { credit -> CreditViewList(credit) }
            .collect(Collectors.toList())
}