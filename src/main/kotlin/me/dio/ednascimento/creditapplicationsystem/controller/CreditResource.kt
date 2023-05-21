package me.dio.ednascimento.creditapplicationsystem.controller

import me.dio.ednascimento.creditapplicationsystem.dto.*
import me.dio.ednascimento.creditapplicationsystem.service.impl.CreditService
import org.springframework.http.*
import org.springframework.web.bind.annotation.*
import java.util.*
import java.util.stream.Collectors
@RestController
@RequestMapping("/api/credits")
class CreditResource(
    private val creditService: CreditService
) {

    @PostMapping
    fun salveCredit(@RequestBody creditDto: CreditDto): ResponseEntity<String> {
        val credit = creditService.save(creditDto.toEntity())
        val message = "Credit ${credit.creditCode} - Customer ${credit.customer?.lastName} saved"
        return ResponseEntity.status(HttpStatus.CREATED).body(message)
    }

    @GetMapping
    fun findAllByCustomerId(@RequestParam(value = "customerId") customerId: Long)
        : ResponseEntity<List<CreditViewList>> {
        val credits = creditService
            .findAllByCustomers(customerId).stream()
            .map { credit -> CreditViewList(credit) }
            .collect(Collectors.toList())
        return ResponseEntity.status(HttpStatus.OK).body(credits)
    }

    @GetMapping("/{creditCode}")
    fun findByCreditCode(@RequestParam(value = "customerId") customerId: Long,
                         @PathVariable creditCode: UUID) : ResponseEntity<CreditView> {
        val credit = creditService.findByCreditCode(customerId, creditCode)
        return ResponseEntity.status(HttpStatus.OK).body(CreditView(credit))
    }
}