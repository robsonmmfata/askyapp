package br.com.smartpos.asky.data.remote.repository

import android.util.Log
import br.com.smartpos.asky.data.model.LoginResponse
import br.com.smartpos.asky.data.model.Menu.MenuResponse
import br.com.smartpos.asky.data.model.MenuAll.MenuAllResponse
import br.com.smartpos.asky.data.remote.api.AskyApi
import br.com.smartpos.asky.data.model.PasswordRequest
import br.com.smartpos.asky.data.model.PaymentRequest
import br.com.smartpos.asky.data.model.PaymentResponse
import br.com.smartpos.asky.data.model.cliente.ClienteResponse
import br.com.smartpos.asky.data.model.produto.ProdutoResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Response

class RemoteRepository(private val api: AskyApi) {
    suspend fun sendPayment(stoneId: String, payment: PaymentRequest): Response<PaymentResponse>? {
        return withContext(Dispatchers.IO) {
            try {
                val response = api.sendPayment(stoneId, payment)
                if (!response.isSuccessful) {
                    Log.e("AskyRepository", "Error: ${response.errorBody()?.string()}")
                }
                response
            } catch (e: Exception) {
                Log.e("AskyRepository", "Exception: ${e.message}")
                e.printStackTrace()
                null
            }
        }
    }

    suspend fun resetPassword(code: String): Response<Unit>? {
        return withContext(Dispatchers.IO) {
            try {
                val response = api.resetPassword(PasswordRequest(code))
                if (!response.isSuccessful) {
                    Log.e("AskyRepository", "Error: ${response.errorBody()?.string()}")
                }
                response
            } catch (e: Exception) {
                Log.e("AskyRepository", "Exception: ${e.message}")
                e.printStackTrace()
                null
            }
        }
    }

    suspend fun getAllMenu(): MenuAllResponse? {
        return api.getAllMenu()
    }

    suspend fun getMenu(idMenu:Int): MenuResponse? {
        return api.getMenu(idMenu)
    }

    suspend fun getProduto(idProduto:Int): ProdutoResponse? {
        return api.getProduto(idProduto)
    }

    suspend fun getToken(code:String): Response<LoginResponse> {
        return api.getToken(code)
    }

    suspend fun getCliente(): ClienteResponse? {
        return api.getCliente()
    }
}
