package br.com.smartpos.asky.di

import br.com.smartpos.asky.viewModel.MenuViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {
    viewModel { MenuViewModel(get(), get(), get()) }
}

