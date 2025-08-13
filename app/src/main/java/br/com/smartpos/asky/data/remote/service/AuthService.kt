package br.com.smartpos.asky.data.remote.service

import android.content.Context
import br.com.smartpos.asky.data.model.QRCodeLoginRequest
import br.com.smartpos.asky.data.model.QRCodeLoginResponse
import br.com.smartpos.asky.data.remote.api.AskyApi
import br.com.smartpos.asky.utils.QRCodeManager
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class AuthService(
    private val askyApi: AskyApi,
    private val context: Context
) {
    
    suspend fun loginWithQRCode(code: String): Result<QRCodeLoginResponse> {
        return withContext(Dispatchers.IO) {
            try {
                val response = askyApi.verifyQRCode(code)
                if (response.isSuccessful) {
                    response.body()?.let { Result.success(it) } 
                        ?: Result.failure(Exception("Resposta vazia"))
                } else {
                    Result.failure(Exception("Erro na verificação: ${response.code()}"))
                }
            } catch (e: Exception) {
                Result.failure(e)
            }
        }
    }
    
    suspend fun generateOwnDeviceQRCode(): Result<String> {
        return withContext(Dispatchers.IO) {
            try {
                val deviceId = QRCodeManager.generateDeviceId(context)
                val deviceName = "Stone POS - ${android.os.Build.MODEL}"
                val code = "OWN_DEVICE_${System.currentTimeMillis()}"
                
                val request = QRCodeLoginRequest(
                    code = code,
                    deviceId = deviceId,
                    deviceName = deviceName
                )
                
                val response = askyApi.generateQRCode(request)
                if (response.isSuccessful) {
                    response.body()?.code?.let { Result.success(it) }
                        ?: Result.failure(Exception("Código não gerado"))
                } else {
                    Result.failure(Exception("Erro ao gerar QR Code"))
                }
            } catch (e: Exception) {
                Result.failure(e)
            }
        }
    }
    
    suspend fun pollForLoginStatus(code: String): Result<QRCodeLoginResponse> {
        return withContext(Dispatchers.IO) {
            try {
                val response = askyApi.pollQRCodeStatus(code)
                if (response.isSuccessful) {
                    response.body()?.let { Result.success(it) }
                        ?: Result.failure(Exception("Status não disponível"))
                } else {
                    Result.failure(Exception("Erro ao verificar status"))
                }
            } catch (e: Exception) {
                Result.failure(e)
            }
        }
    }
}
