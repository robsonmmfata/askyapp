package br.com.smartpos.asky.data.model.Menu

data class Items(
    val id: Int,
    val name: String,
    val name_display: String,
    val photo: String,
    val price: Double,
    val description: String,
    val hidden: Boolean,
    val sold_out: Boolean,
    val available: Boolean,
    val discount_price: Int,
    val discount_available: Boolean,
    val tags: ArrayList<Tags>,

)
