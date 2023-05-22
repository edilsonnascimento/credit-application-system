package me.dio.ednascimento.creditapplicationsystem.exception

import java.time.LocalDate

data class ExceptionDetails(
    val title: String,
    val timestamp: LocalDate,
    val status: Int,
    val exception: String,
    val detail: MutableMap<String, String?>
)