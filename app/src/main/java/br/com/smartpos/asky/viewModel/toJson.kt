package br.com.smartpos.asky.viewModel


import br.com.smartpos.asky.data.model.Menu.MenuResponse
import br.com.smartpos.asky.data.model.MenuAll.MenuAllResponse
import com.google.gson.Gson

fun MenuAllResponse.toJson(): String {
    return Gson().toJson(this)
}

fun String.toCategoryResponse(): MenuAllResponse {
    return Gson().fromJson(this, MenuAllResponse::class.java)
}

fun MenuResponse.toJson(): String {
    return Gson().toJson(this)
}

fun String.toMenuResponse(): MenuResponse {
    return Gson().fromJson(this, MenuResponse::class.java)
}
