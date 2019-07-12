package id.maskipli.samplejetpack

import android.app.Application
import android.content.Context
import id.maskipli.samplejetpack.di.NetworkModules
import id.maskipli.samplejetpack.di.ViewModelModules
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level
import timber.log.Timber

/**
 * @author hidayat @on 12/07/19
 **/
class Apps : Application() {

    init {
        instance = this
    }

    companion object {
        private var instance: Apps? = null

        fun applicationContext(): Context {
            return instance!!.applicationContext
        }
    }

    override fun onCreate() {
        super.onCreate()
        if (BuildConfig.DEBUG) Timber.plant(Timber.DebugTree())
        startKoin {
            androidLogger(Level.DEBUG)
            androidContext(this@Apps)
            modules(listOf(NetworkModules.module, ViewModelModules.module))
        }
    }
}