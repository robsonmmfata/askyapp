package br.com.smartpos.asky

import android.app.Application
import br.com.smartpos.asky.data.model.Menu.MenuResponse
import br.com.smartpos.asky.data.model.MenuAll.MenuAllResponse
import br.com.smartpos.asky.di.networkModule
import br.com.smartpos.asky.di.controllerModule
import br.com.smartpos.asky.di.repositoryModule
import br.com.smartpos.asky.di.useCaseModule
import br.com.smartpos.asky.di.viewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class AppApplication : Application() {

    companion object {
        private var _allMenuBody: MenuAllResponse? = null
        var allMenuBody: MenuAllResponse?
            get() = _allMenuBody
            set(value) {_allMenuBody = value}

        private var _menuBody: List<MenuResponse?> = emptyList()
        var menuBody: List<MenuResponse?>
            get() = _menuBody
            set(value) {_menuBody = value}

//        private var _menuNoite: MenuResponse? = null
//        var menuNoite: MenuResponse?
//            get() = _menuNoite
//            set(value) {_menuNoite = value}

        private var _pegA: Boolean = false
        var pegA: Boolean
            get() = _pegA
            set(value) {_pegA = value}

        private var _pegB: Boolean = true
        var pegB: Boolean
            get() = _pegB
            set(value) {_pegB = value}

        private var _menu: Int = 0
        var menu: Int
            get() = _menu
            set(value) {_menu = value}

        private var _titulo: String = ""
        var titulo: String
            get() = _titulo
            set(value) {_titulo = value}

        private var _tituloNoite: String = ""
        var tituloNoite: String
            get() = _tituloNoite
            set(value) {_tituloNoite = value}
    }

    override fun onCreate() {
        super.onCreate()
        setupKoin()
    }

    private fun setupKoin() {
        startKoin {
            androidContext(this@AppApplication)
            modules(
                viewModelModule,
                repositoryModule,
                networkModule,
                controllerModule,
                useCaseModule
            )
        }
    }


}