package br.com.smartpos.asky.data.model.Menu

data class Menu(
    val id: Int,
    val name: String,
    val display_name: String,
    val time_based: Boolean,
    val status: Boolean,
    val services: ArrayList<String>,
    val description: String,
    val default_macro_category: String,
    val categories_count: Int,
    val available_items_count: Int,
)
