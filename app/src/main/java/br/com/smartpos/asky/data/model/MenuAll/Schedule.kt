package br.com.smartpos.asky.data.model.MenuAll

import br.com.smartpos.asky.data.model.MenuAll.tradingHour.TradingHours

data class Schedule(
    val tradingHours: TradingHours,
    val deliveryAccepting: Boolean,
    val pickupAccepting: Boolean,
    val tableRoomAccepting: Boolean,
)
