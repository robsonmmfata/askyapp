package br.com.smartpos.asky.data.remote.network

import android.annotation.SuppressLint
import br.com.smartpos.asky.data.remote.network.interceptors.ErrorInterceptor

import okhttp3.OkHttpClient
import okhttp3.Protocol
import okhttp3.logging.HttpLoggingInterceptor
import java.security.SecureRandom
import java.security.cert.X509Certificate
import java.util.concurrent.TimeUnit
import javax.net.ssl.HostnameVerifier
import javax.net.ssl.SSLContext
import javax.net.ssl.X509TrustManager

private const val PROTOCOL_SSL = "SSL"
const val REQUEST_TIMEOUT = 30L

fun provideOkHttpClient(
    loginInterceptor: HttpLoggingInterceptor,
    errorInterceptor: ErrorInterceptor,
): OkHttpClient {

    val sslContext = SSLContext.getInstance(PROTOCOL_SSL).apply {
        init(null, arrayOf(trustAllAnchors), SecureRandom())
    }

    val timeout = REQUEST_TIMEOUT

    return OkHttpClient.Builder()
        .connectTimeout(timeout, TimeUnit.SECONDS)
        .readTimeout(timeout, TimeUnit.SECONDS)
        .sslSocketFactory(sslContext.socketFactory, trustAllAnchors)
        .hostnameVerifier(hostnameVerifier)
        .protocols(listOf(Protocol.HTTP_1_1))
        .addInterceptor(loginInterceptor)
        .addInterceptor(errorInterceptor)
        .retryOnConnectionFailure(true)
        .build()
}

val trustAllAnchors = object : X509TrustManager {

    @SuppressLint("TrustAllX509TrustManager")
    override fun checkClientTrusted(chain: Array<out X509Certificate>?, authType: String?) {
    }

    @SuppressLint("TrustAllX509TrustManager")
    override fun checkServerTrusted(chain: Array<out X509Certificate>?, authType: String?) {
    }

    override fun getAcceptedIssuers(): Array<X509Certificate>? = arrayOf()
}

val hostnameVerifier = HostnameVerifier { _, _ -> true }