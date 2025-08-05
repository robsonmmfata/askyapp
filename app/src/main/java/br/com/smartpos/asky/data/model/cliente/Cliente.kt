package br.com.smartpos.asky.data.model.cliente

data class Cliente(
    val blocked: String = "",
    val email: String?,
    val id:Int = 0,
    val image: String = "",
    val name: String = "",
    val phone: String?,
)
