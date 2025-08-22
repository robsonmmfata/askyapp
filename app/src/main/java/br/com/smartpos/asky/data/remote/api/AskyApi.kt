package br.com.smartpos.asky.data.remote.api

import br.com.smartpos.asky.data.model.LoginResponse
import br.com.smartpos.asky.data.model.Menu.MenuResponse
import br.com.smartpos.asky.data.model.MenuAll.MenuAllResponse
import br.com.smartpos.asky.data.model.PasswordRequest
import br.com.smartpos.asky.data.model.QRCodeLoginRequest
import br.com.smartpos.asky.data.model.QRCodeResponse
import br.com.smartpos.asky.data.model.QRCodeLoginResponse
import br.com.smartpos.asky.data.model.PaymentRequest
import br.com.smartpos.asky.data.model.PaymentResponse
import br.com.smartpos.asky.data.model.cliente.ClienteResponse
import br.com.smartpos.asky.data.model.produto.ProdutoResponse

import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface AskyApi {
    @POST("stone/pagamento/{stoneId}")
    suspend fun sendPayment(
        @Path("stoneId") stoneId: String,
        @Body payment: PaymentRequest,
    ): Response<PaymentResponse>

    @POST("stone/reset-senha")
    suspend fun resetPassword(
        @Body password: PasswordRequest,
    ): Response<Unit>

    @POST("/api/app/v1/auth/login/qr-code")
    suspend fun getToken(
        @Query(value = "code", encoded = true) code: String,
    ): Response<LoginResponse>

    @POST("/api/app/v1/auth/qr-code/generate")
    suspend fun generateQRCode(
        @Body request: QRCodeLoginRequest
    ): Response<QRCodeResponse>

    @POST("/api/app/v1/auth/qr-code/verify")
    suspend fun verifyQRCode(
        @Query("code") code: String
    ): Response<QRCodeLoginResponse>

    @POST("/api/app/v1/auth/qr-code/poll")
    suspend fun pollQRCodeStatus(
        @Query("code") code: String
    ): Response<QRCodeLoginResponse>

    @GET("/api/app/v1/admin/order/menus")
    suspend fun getAllMenu(
//        @Header("Authorization") token: String
    ): MenuAllResponse

    @GET("/api/app/v1/admin/customers/search")
    suspend fun getCliente(): ClienteResponse

    @GET("/api/app/v1/admin/order/menus/{idMenu}")
    suspend fun getMenu(
        @Path("idMenu") idMenu: Int,
    ): MenuResponse

    @GET("/api/app/v1/admin/order/menus/items/{idProduto}")
    suspend fun getProduto(
        @Path("idProduto") idProduto: Int,
    ): ProdutoResponse
}

