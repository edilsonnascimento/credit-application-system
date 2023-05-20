package me.dio.ednascimento.creditapplicationsystem.entity

import jakarta.persistence.*
import me.dio.ednascimento.creditapplicationsystem.enumeration.Status
import java.math.BigDecimal
import java.time.LocalDate
import java.util.UUID

@Entity
data class Credit(
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        @Column(name = "id_credit", nullable = false)
        val id: Long? = null,

        @Column(nullable = false, unique = true)
        val creditCode: UUID = UUID.randomUUID(),

        @Column(nullable = false)
        val creditValue: BigDecimal = BigDecimal.ZERO,

        @Column(nullable = false)
        val dayFirstInstallment: LocalDate,

        @Column(nullable = false)
        val numberOfIntallments: Int = 0,

        @Enumerated
        val status: Status = Status.IN_PROGRESS,

        @ManyToOne
        @JoinColumn(name = "id_customer")
        val customer: Customer? = null
)