package br.com.smartpos.asky.di

import br.com.smartpos.asky.usecase.MenuCase
import br.com.smartpos.asky.usecase.PreferencesUseCase
import org.koin.dsl.module

val useCaseModule = module {
   factory { MenuCase(get()) }
   factory { PreferencesUseCase(get()) }

}

