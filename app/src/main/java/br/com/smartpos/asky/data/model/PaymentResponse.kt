package br.com.smartpos.asky.data.model

data class PaymentResponse(
    val `data`: Data,
    val sucesso: Boolean,
)

data class Data(
    val stoneId: Long,
    val valor: Int,
)