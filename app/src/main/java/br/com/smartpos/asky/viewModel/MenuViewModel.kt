package br.com.smartpos.asky.viewModel

import android.util.Log
import androidx.compose.runtime.remember
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.smartpos.asky.AppApplication
import br.com.smartpos.asky.data.model.LoginResponse
import br.com.smartpos.asky.data.model.Menu.MenuResponse
import br.com.smartpos.asky.data.model.ProdutoItens
import br.com.smartpos.asky.data.model.SCList
import br.com.smartpos.asky.data.model.cliente.ClienteResponse
import br.com.smartpos.asky.data.model.pedido.PedidoItens
import br.com.smartpos.asky.data.model.produto.ProdutoResponse
import br.com.smartpos.asky.data.remote.network.AuthEventManager
import br.com.smartpos.asky.usecase.MenuCase
import br.com.smartpos.asky.usecase.PreferencesUseCase
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.math.BigDecimal

class MenuViewModel(
    private val menuCase: MenuCase,
    private val preferencesUseCase: PreferencesUseCase,
    private val authEventManager: AuthEventManager
) : ViewModel() {

    val navigateToLogin = MutableSharedFlow<Unit>(extraBufferCapacity = 1)

    private val _produto = MutableStateFlow<ProdutoResponse?>(null)
    val produto: StateFlow<ProdutoResponse?> = _produto

    private val _login = MutableStateFlow(false)
    val login: StateFlow<Boolean> = _login

    private val _cliente = MutableStateFlow<ClienteResponse?>(null)
    val cliente: StateFlow<ClienteResponse?> = _cliente

    private val _isLoading = MutableStateFlow(true)
    val isLoading: StateFlow<Boolean> = _isLoading

    private val _isLoadingP = MutableStateFlow(true)
    val isLoadingP: StateFlow<Boolean> = _isLoadingP

    private val _pedidos = MutableStateFlow<List<PedidoItens>>(emptyList())
    val pedidos: StateFlow<List<PedidoItens>> = _pedidos

    private val _menu = MutableStateFlow<List<String>>(emptyList())
    val menu: StateFlow<List<String>> = _menu

    init {
        viewModelScope.launch {
            authEventManager.unauthorizedEvent.collect {
                navigateToLogin.tryEmit(Unit)
            }
        }
    }

    fun getMenu() {
        try {
            viewModelScope.launch {

                val responseAll = menuCase.getAllMenu()
                Log.i("Resposta", "menu ${responseAll}")
                AppApplication.allMenuBody = responseAll
                val listmenu = mutableListOf<MenuResponse>()
                var currentList = mutableListOf<String>()
                responseAll?.menus?.forEach { getM ->
                    menuCase.getMenu(getM.id)?.let { listmenu.add(it) }
                    getM.name?.let { currentList.add(it) }
                }
                AppApplication.menuBody = listmenu
                 _menu.value = currentList

            }
        } catch (e: Exception) {
           // onError()
        }
    }
    fun getClientes() {

        try {
            viewModelScope.launch {
                _isLoading.value = true
                val response = menuCase.getCliente()
                _cliente.value = response
                _isLoading.value = false
            }
        } catch (e: Exception) {
            // onError()
        }

    }

    fun getTotal(pedido: List<PedidoItens>): Double {
        var total:Double = 0.0
        for(ped in pedido){
            total += ped.price
        }
        return total
    }

    fun getProduto(idProduto:Int) {

        try {
            viewModelScope.launch {
                _isLoadingP.value = true
                delay(100)
                val response = menuCase.getProduto(idProduto)
                _produto.value = response
                _isLoadingP.value = false
            }
        } catch (e: Exception) {
            // onError()
        }

    }

    fun getToken(code:String) {

        viewModelScope.launch {
            preferencesUseCase.setToken("")
            _isLoadingP.value = true
            val response = menuCase.getToken(code)
            if (response.isSuccessful) {
                val data = response.body()
                data?.let { preferencesUseCase.setToken(it.token) }
                _login.value = true
                _isLoadingP.value = false
            } else {
                _isLoadingP.value = false
            }
        }

    }

    fun getComidas(id:Int): Map<String, List<ProdutoItens>> {
        val categoryList = AppApplication.menuBody[id]?.categories
        var comidaItems:MutableList<ProdutoItens> = mutableListOf()
        for (category in categoryList!!) {
            if (category.name.isNotEmpty()) {
                if (category.macro_category == "Food") {
                    for (item in category.items) {
                        val prod1 = ProdutoItens(
                            codigo = item.id,
                            nomeProduto = item.name,
                            descricao = item.description,
                            valor = BigDecimal.valueOf(item.price),
                            qunatidade = 1,
                            categoria = category.name,
                            subcategoria = "",
                            unidade = "",
                            foto = item.photo,
                            esgotado = item.hidden,
                        )
                        comidaItems.add(prod1)
                    }
                }
            }
        }
        return comidaItems.groupBy { it.categoria!! } // Agrupa por nome
    }

//    fun getComidasNoite(): Map<String, List<ProdutoItens>> {
//        val categoryList = AppApplication.menuNoite?.categories
//        var comidaItems:MutableList<ProdutoItens> = mutableListOf()
//        for (category in categoryList!!) {
//            if (category.name.isNotEmpty()) {
//                if (category.macro_category == "Food") {
//                    for (item in category.items) {
//                        val prod1 = ProdutoItens(
//                            codigo = item.id,
//                            nomeProduto = item.name,
//                            descricao = item.description,
//                            valor = BigDecimal.valueOf(item.price),
//                            qunatidade = 1,
//                            categoria = category.name,
//                            subcategoria = "",
//                            unidade = "",
//                            foto = item.photo,
//                            esgotado = item.hidden,
//                        )
//                        comidaItems.add(prod1)
//                    }
//                }
//            }
//        }
//        return comidaItems.groupBy { it.categoria!! } // Agrupa por nome
//    }

    fun getBebidas(id:Int): Map<String, List<ProdutoItens>> {
        val categoryList = AppApplication.menuBody[id]?.categories
        var bebidaItems:MutableList<ProdutoItens> = mutableListOf()
        for (category in categoryList!!) {
            if (category.name.isNotEmpty()) {
                if (category.macro_category == "Drink") {
                    for (item in category.items) {
                        val prod1 = ProdutoItens(
                            codigo = item.id,
                            nomeProduto = item.name,
                            descricao = item.description,
                            valor = BigDecimal.valueOf(item.price),
                            qunatidade = 1,
                            categoria = category.name,
                            subcategoria = "",
                            unidade = "",
                            foto = item.photo,
                            esgotado = item.hidden,
                        )
                        bebidaItems.add(prod1)
                    }
                }
            }
        }
        return bebidaItems.groupBy { it.categoria!! } // Agrupa por nome

    }

    fun getMapa(mapa:List<SCList>):MutableMap<String, Int>{

            val mapaCreate = mutableMapOf<String, Int>()
            var index = 0
            for (categoria in mapa) {
                mapaCreate[categoria.name!!] = index
                index++ // Conta o título da categoria
                index += categoria.items.size // Conta os itens da categoria
            }
            return mapaCreate

    }

//    fun getBebidasNoite(): Map<String, List<ProdutoItens>> {
//        val categoryList = AppApplication.menuNoite?.categories
//        var bebidaItems:MutableList<ProdutoItens> = mutableListOf()
//        for (category in categoryList!!) {
//            if (category.name.isNotEmpty()) {
//                if (category.macro_category == "Drink") {
//                    for (item in category.items) {
//                        val prod1 = ProdutoItens(
//                            codigo = item.id,
//                            nomeProduto = item.name,
//                            descricao = item.description,
//                            valor = BigDecimal.valueOf(item.price),
//                            qunatidade = 1,
//                            categoria = category.name,
//                            subcategoria = "",
//                            unidade = "",
//                            foto = item.photo,
//                            esgotado = item.hidden,
//                        )
//                        bebidaItems.add(prod1)
//                    }
//                }
//            }
//        }
//        return bebidaItems.groupBy { it.categoria!! } // Agrupa por nome
//
//    }

    fun addPedido(pedido: PedidoItens) {

        _pedidos.value = _pedidos.value + pedido

    }

    fun limparProduto() {

        _produto.value = null

    }

    fun sizePedido(): Int {

       return pedidos.value.size

    }

    fun saveColuna(col:Int) {
        preferencesUseCase.setColunas(col.toString())
    }
    fun getColuna():Int {
        return preferencesUseCase.getColunas()!!.toInt()
    }
    fun saveMenuPrincipal(menu:Int) {
        AppApplication.menu = menu
    }
    fun getMenuPrincipal():Int {
        return AppApplication.menu
    }
    fun getPagA(): Boolean {
        return AppApplication.pegA
    }
    fun setPagA(pag: Boolean) {
        AppApplication.pegA = pag
    }
    fun getPagB(): Boolean {
        return AppApplication.pegB
    }
    fun setPagB(pag: Boolean) {
        AppApplication.pegB = pag
    }
    fun getTitulo(): String {
        return AppApplication.titulo
    }
    fun setTitulo(titulo: String) {
        AppApplication.titulo = titulo
    }
    fun getTituloNoite(): String {
        return AppApplication.tituloNoite
    }
    fun setTituloNoite(titulo: String) {
        AppApplication.tituloNoite = titulo
    }
    fun setLoading() {
        _isLoadingP.value = true
    }
    fun getLotinToken():String {
       return preferencesUseCase.getToken().toString()
    }

    fun getPedidos(): List<PedidoItens> {
        val pedidoItens=  preferencesUseCase.getPedidos()
        Log.i("Obj", "menu" + pedidoItens);
        val type = object : TypeToken<List<PedidoItens>>() {}.type
        val pedidoItensList: List<PedidoItens> = Gson().fromJson(pedidoItens, type)
        return pedidoItensList
    }
}