package br.com.smartpos.asky.data.model.MenuAll.tradingHour

data class TradingHours(
    val sameDay: SameDay,
    val nextDay: NextDay,
    val orderSchedules: ArrayList<String>,
)
