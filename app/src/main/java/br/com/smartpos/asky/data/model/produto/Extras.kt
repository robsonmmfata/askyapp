package br.com.smartpos.asky.data.model.produto

data class Extras(
    val id: Int,
    val name: String,
    val price: Double,
    val description: String,
    val hidden: Boolean,
    val available: Boolean,
    val sold_out: Boolean,
    var checked: Boolean= false,
)
