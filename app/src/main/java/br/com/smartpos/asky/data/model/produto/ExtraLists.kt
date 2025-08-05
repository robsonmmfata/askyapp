package br.com.smartpos.asky.data.model.produto

data class ExtraLists(
    val id: Int,
    val name: String,
    val name_display: String,
    val min: Int,
    val max: Int,
    val multiple: Boolean,
    val description: String,
    val extras: ArrayList<Extras>


)
