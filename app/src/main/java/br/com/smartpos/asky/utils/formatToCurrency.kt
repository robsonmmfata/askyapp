package br.com.smartpos.asky.utils

import java.text.NumberFormat
import java.util.Locale

fun formatToCurrency(value: Double): String {
    val numberFormat = NumberFormat.getCurrencyInstance(Locale("pt", "BR"))
    return numberFormat.format(value)
}
