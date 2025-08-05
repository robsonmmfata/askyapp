package br.com.smartpos.asky.data.model

data class PaymentRequest(
    val valor: Int,
    val formaDePagamento: String,
    val idTransacao: String,
)