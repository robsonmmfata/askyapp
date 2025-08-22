package br.com.smartpos.asky.data.model

data class QRCodeLoginRequest(
    val code: String,
    val deviceId: String,
    val deviceName: String
)
