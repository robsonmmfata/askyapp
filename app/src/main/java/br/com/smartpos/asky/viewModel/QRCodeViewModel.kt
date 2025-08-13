package br.com.smartpos.asky.viewModel

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.smartpos.asky.data.model.QRCodeLoginRequest
import br.com.smartpos.asky.data.remote.api.AskyApi
import br.com.smartpos.asky.data.remote.repository.RemoteRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import retrofit2.Response

class QRCodeViewModel(
    private val remoteRepository: RemoteRepository,
    private val askyApi: AskyApi
) : ViewModel() {
    
    private val _qrCodeResponse = MutableStateFlow<String?>(null)
    val qrCodeResponse: StateFlow<String?> = _qrCodeResponse
    
    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading
    
    private val _errorMessage = MutableStateFlow<String?>(null)
    val errorMessage: StateFlow<String?> = _errorMessage
    
    fun generateQRCode(deviceId: String, deviceName: String) {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                val request = QRCodeLoginRequest(
                    code = "OWN_DEVICE_${System.currentTimeMillis()}",
                    deviceId = deviceId,
                    deviceName = deviceName
                )
                
                val response = askyApi.generateQRCode(request)
                if (response.isSuccessful) {
                    _qrCodeResponse.value = response.body()?.code
                } else {
                    _errorMessage.value = "Erro ao gerar QR Code"
                }
            } catch (e: Exception) {
                _errorMessage.value = e.message ?: "Erro desconhecido"
            } finally {
                _isLoading.value = false
            }
        }
    }
    
    fun verifyQRCode(code: String) {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                val response = askyApi.verifyQRCode(code)
                if (response.isSuccessful) {
                    _qrCodeResponse.value = response.body()?.token
                } else {
                    _errorMessage.value = "Erro ao verificar QR Code"
                }
            } catch (e: Exception) {
                _errorMessage.value = e.message ?: "Erro desconhecido"
            } finally {
                _isLoading.value = false
            }
        }
    }
}
