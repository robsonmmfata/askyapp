package br.com.smartpos.asky.data.model.produto

data class ProdutoResponse(
    val id: Int,
    val name: String,
    val name_display: String,
    val sku: String,
    val photo: String,
    val price: Double,
    val note_active: Boolean,
    val description: String,
    val available: Boolean,
    val hidden: Boolean,
    val sold_out: Boolean,
    val slug: String,
    val discount_status: Boolean,
    val discount_price: Int,
    val waiting_minutes: Int,
    val extra_lists: ArrayList<ExtraLists>
)
