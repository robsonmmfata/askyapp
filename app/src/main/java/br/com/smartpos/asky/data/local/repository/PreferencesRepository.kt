package br.com.smartpos.asky.data.local.repository

import br.com.smartpos.asky.data.local.PreferencesManager

class PreferencesRepository(private val preferencesManager: PreferencesManager) {
    fun getPassword(): String? = preferencesManager.password
    fun setPassword(value: String?) = value.also { preferencesManager.password = it }

    fun getStoneId(): String? = preferencesManager.stoneId
    fun setStoneId(value: String) = value.also { preferencesManager.stoneId = it }

    fun getToken(): String? = preferencesManager.token
    fun setToken(value: String) = value.also { preferencesManager.token = it }

    fun getPaymentMethod(): String? = preferencesManager.paymentMethod
    fun setPaymentMethod(value: String) = value.also { preferencesManager.paymentMethod = it }

    fun getPaymentAmount(): Double = preferencesManager.paymentAmount
    fun setPaymentAmount(value: Double) = value.also { preferencesManager.paymentAmount = it }

    fun getPedidos(): String? = preferencesManager.pedidos
    fun setPedidos(value: String) = value.also { preferencesManager.pedidos = it }

    fun getColunas(): String? = preferencesManager.colunas
    fun setColunas(value: String) = value.also { preferencesManager.colunas = it }

    fun getMenuPrincipal(): String? = preferencesManager.menuPrincipal
    fun setMenuPrincipal(value: String) = value.also { preferencesManager.menuPrincipal = it }

}
