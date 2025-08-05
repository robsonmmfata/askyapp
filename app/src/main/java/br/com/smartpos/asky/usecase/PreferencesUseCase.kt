package br.com.smartpos.asky.usecase

import br.com.smartpos.asky.data.local.repository.PreferencesRepository

class PreferencesUseCase(private val repository: PreferencesRepository) {
    fun getPassword() = repository.getPassword()
    fun setPassword(value: String?) = repository.setPassword(value)

    fun getStoneId() = repository.getStoneId()
    fun setStoneId(value: String) = repository.setStoneId(value)

    fun getToken() = repository.getToken()
    fun setToken(value: String) = repository.setToken(value)

    fun getPedidos() = repository.getPedidos()
    fun setPedidos(value: String) = repository.setPedidos(value)

    fun getPaymentMethod() = repository.getPaymentMethod()
    fun setPaymentMethod(value: String) = repository.setPaymentMethod(value)

    fun getPaymentAmount() = repository.getPaymentAmount()
    fun setPaymentAmount(value: Double) = repository.setPaymentAmount(value)

    fun getColunas() = repository.getColunas()
    fun setColunas(value: String) = repository.setColunas(value)

    fun getMenuPrincipal() = repository.getMenuPrincipal()
    fun setMenuPrincipal(value: String) = repository.setMenuPrincipal(value)
}
