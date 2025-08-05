package br.com.smartpos.asky.usecase

import br.com.smartpos.asky.data.model.LoginResponse
import br.com.smartpos.asky.data.remote.repository.RemoteRepository
import retrofit2.Response

class MenuCase(
    private val remoteRepository: RemoteRepository,
) {
    suspend fun getToken(code: String): Response<LoginResponse> = remoteRepository.getToken(code)
    suspend fun getAllMenu() = remoteRepository.getAllMenu()
    suspend fun getMenu(idMenu:Int) = remoteRepository.getMenu(idMenu)
    suspend fun getProduto(idProduto:Int) = remoteRepository.getProduto(idProduto)
    suspend fun getCliente() = remoteRepository.getCliente()
}


