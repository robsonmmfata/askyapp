package br.com.smartpos.asky.data.remote.network.interceptors

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import br.com.smartpos.asky.data.remote.network.WifiConnectivity
import okhttp3.Interceptor
import okhttp3.Response
import java.io.Serializable
import java.net.SocketException
import java.net.SocketTimeoutException
import java.net.UnknownHostException

class ErrorInterceptor(
    private val wifiHelper: WifiConnectivity,
    private val onUnauthorized: (() -> Unit)? = null
) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        try {
            request.let {
                val response = chain.proceed(it)
                _events.postValue(ErrorMessage.ANDROID_CANCELED)
                when (response.code) {
                    401 -> {
                        onUnauthorized?.invoke() // chama o callback se estiver configurado
                        return response
                    }
                    in 100..199 -> {
                        return response
                    }

                    in 200..299 -> {
                        return response
                    }

                    in 300..399 -> {
                        return response
                    }

                    in 400..421 -> {
                        return response
                    }

                    422 -> {
                        return response
                    }

                    in 423..499 -> {
                        return response
                    }

                    in 500..599 -> {
                        return response

                    }

                    else -> {
                        return response
                    }
                }
            }


        } catch (e: Exception) {
            _events.postValue(ErrorMessage.ANDROID_CANCELED)
            when (e) {
                is SocketException, is SocketTimeoutException -> {
                    if (!wifiHelper.isSsidCorrect()) {
                        hostNameError = chain.request().url.toString()
                        _events.postValue(ErrorMessage.ANDROID_TIME_OUT)
                    }
                    throw e

                }

                is UnknownHostException -> {
                    if (!wifiHelper.isSsidCorrect()) {
                        hostNameError = chain.request().url.toString()
                        _events.postValue(ErrorMessage.ANDROID_UNKNOWHOST)
                    }

                    throw e
                }

                else -> {
                    if (!wifiHelper.isSsidCorrect()) {
                        _events.postValue(ErrorMessage.ANDROID_CANCELED)
                    }
                    throw e
                }
            }
        }
        return chain.proceed(chain.request())
    }

    enum class ErrorMessage : Serializable {
        ANDROID_UNKNOWHOST,
        ANDROID_TIME_OUT,
        ANDROID_CANCELED
    }

    companion object {
        sealed class Result<out T> {
            data class Success<out T>(val value: T) : Result<T>()
            data class GenericError(val code: Int? = null, val error: ErrorResponse? = null) :
                Result<Nothing>()

            object NetworkError : Result<Nothing>()
        }

        data class ErrorResponse(val message: String)

        val _events = MutableLiveData<ErrorMessage>()
        val events: LiveData<ErrorMessage>
            get() = _events

        var hostNameError = ""

    }
}