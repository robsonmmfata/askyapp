package br.com.smartpos.asky.data.model.MenuAll.tradingHour

data class Hours(
    val delivery: List<Delivery>,
    val table: List<Table>,
    val pickup: List<Pickup>,
)
