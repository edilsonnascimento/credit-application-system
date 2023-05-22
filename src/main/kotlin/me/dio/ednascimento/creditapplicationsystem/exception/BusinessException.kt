package me.dio.ednascimento.creditapplicationsystem.exception

data class BusinessException(override val message: String?): RuntimeException(message) {
}