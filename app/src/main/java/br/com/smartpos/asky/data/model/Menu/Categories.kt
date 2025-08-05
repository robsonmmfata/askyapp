package br.com.smartpos.asky.data.model.Menu

data class Categories(
    val id: Int,
    val name: String,
    val display_name: String,
    val macro_category: String,
    val sort_no: Int,
    val description: String,
    val items: ArrayList<Items>,
)
