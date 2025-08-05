package br.com.smartpos.asky.di

import br.com.smartpos.asky.data.local.PreferencesManager
import br.com.smartpos.asky.data.local.repository.PreferencesRepository
import br.com.smartpos.asky.data.remote.network.AuthEventManager
import br.com.smartpos.asky.data.remote.repository.RemoteRepository
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val repositoryModule = module {
    single { PreferencesManager(androidContext()) }
    single { PreferencesRepository(get()) }
    single { RemoteRepository(get()) }
    single { AuthEventManager() }
}