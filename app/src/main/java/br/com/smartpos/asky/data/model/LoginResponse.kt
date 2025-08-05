package br.com.smartpos.asky.data.model

data class LoginResponse(
    val token: String,
    val message: String,
    val errors: Code,
)
