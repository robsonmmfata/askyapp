package br.com.smartpos.asky.data.model.MenuAll.tradingHour

data class SameDay(
    val date: String,
    val hours: Hours,
    val outlet_closed: OutletClosed,
    val currently_closed: CurrentlyClosed,
    val next_available_time: NextAvailableTime,
    val all_service_currently_closed: Boolean,
)
