package br.com.smartpos.asky.data.local

import android.content.Context
import android.content.SharedPreferences
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class PreferencesManager(context: Context) {
    private val prefs: SharedPreferences =
        context.getSharedPreferences("app_prefs", Context.MODE_PRIVATE)
    private val gson = Gson()
    var password: String?
        get() = prefs.getString("password", null)
        set(value) = prefs.edit().putString("password", value).apply()

    var stoneId: String?
        get() = prefs.getString("stone_id", null)
        set(value) = prefs.edit().putString("stone_id", value).apply()

    var token: String?
        get() = prefs.getString("token", "2")
        set(value) = prefs.edit().putString("token", value).apply()

    var paymentMethod: String?
        get() = prefs.getString("payment_method", null)
        set(value) = prefs.edit().putString("payment_method", value).apply()

    var transactionId: String?
        get() = prefs.getString("transaction_id", null)
        set(value) = prefs.edit().putString("transaction_id", value).apply()

    var paymentAtk: String?
        get() = prefs.getString("paymentAtk", null)
        set(value) = prefs.edit().putString("paymentAtk", value).apply()

    var paymentAmount: Double
        get() = prefs.getFloat("payment_amount", 0f).toDouble()
        set(value) = prefs.edit().putFloat("payment_amount", value.toFloat()).apply()

    var pedidos: String?
        get() = prefs.getString("pedidos", null)
        set(value) = prefs.edit().putString("pedidos", value).apply()

    var colunas: String?
        get() = prefs.getString("coluna", "2")
        set(value) = prefs.edit().putString("coluna", value).apply()

    var menuPrincipal: String?
        get() = prefs.getString("menu", "0")
        set(value) = prefs.edit().putString("menu", value).apply()
}

