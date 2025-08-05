package br.com.smartpos.asky.data.model.MenuAll

data class MenuAllResponse(
    val description: String,
    val orderTime: ArrayList<String>,
    val schedule: Schedule,
    val menus: List<Menus>,
    val default_service: DefaultService,
)