package br.com.smartpos.asky.di


import android.util.Log
import br.com.smartpos.asky.data.remote.api.AskyApi
import br.com.smartpos.asky.data.remote.network.AuthEventManager
import br.com.smartpos.asky.data.remote.network.AuthInterceptor
import br.com.smartpos.asky.data.remote.network.WifiConnectivity
import br.com.smartpos.asky.data.remote.network.WifiConnectivityHelper
import br.com.smartpos.asky.data.remote.network.interceptors.ErrorInterceptor
import br.com.smartpos.asky.data.remote.network.provideOkHttpClient
import br.com.smartpos.asky.usecase.PreferencesUseCase
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

val networkModule = module {
    factory<WifiConnectivity> { WifiConnectivityHelper(context = get()) }
    factory { ErrorInterceptor(wifiHelper = get(),onUnauthorized = {
        get<PreferencesUseCase>().setToken("")
        get<AuthEventManager>().emitUnauthorized()
    }) }
    factory { provideLoginInterceptor() }
    factory {
        provideOkHttpClient(
            loginInterceptor = get(),
            errorInterceptor = get(),
        )
    }

    single { provideAskyApi(get(), get()) }
}

fun provideLoginInterceptor() = HttpLoggingInterceptor().apply {
    level =
        HttpLoggingInterceptor.Level.BODY
}

fun provideAskyApi(
    errorInterceptor: ErrorInterceptor,
    preferencesUseCase: PreferencesUseCase,
): AskyApi {
    val logging = HttpLoggingInterceptor()
    logging.setLevel(HttpLoggingInterceptor.Level.BODY)

    val client = OkHttpClient.Builder()
        .addInterceptor(logging)
        .addInterceptor(errorInterceptor)
        .addInterceptor(AuthInterceptor { preferencesUseCase.getToken().toString() })
        .build()

    return Retrofit.Builder()
        .baseUrl("https://staging123.asky.ai/")
        .addConverterFactory(GsonConverterFactory.create())
        .client(client)
        .build()
        .create(AskyApi::class.java)
}
