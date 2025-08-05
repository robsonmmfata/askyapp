package br.com.smartpos.asky.data.model.MenuAll.tradingHour

data class NextDay(
    val date: String,
    val hours: Hours,
    val outlet_closed: Boolean,
    val service_closed: ServiceClosed
)
