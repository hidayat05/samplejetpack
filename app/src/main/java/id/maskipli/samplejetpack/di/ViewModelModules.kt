package id.maskipli.samplejetpack.di

import org.koin.dsl.module
import id.maskipli.samplejetpack.ui.main.MainViewModel
import org.koin.androidx.viewmodel.dsl.viewModel

/**
 * @author hidayat @on 12/07/19
 **/
object ViewModelModules {

    val module = module {

        viewModel { MainViewModel(get()) }
    }
}