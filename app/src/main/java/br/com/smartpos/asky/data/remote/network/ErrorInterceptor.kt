package br.com.smartpos.asky.data.remote.network

import okhttp3.Interceptor
import okhttp3.Response

class ErrorInterceptor(
    private val onUnauthorized: () -> Unit // você define o que acontece no 401
) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        val response = chain.proceed(request)

        if (response.code == 401) {
            // Token inválido ou expirado
            onUnauthorized()
        }

        return response
    }
}