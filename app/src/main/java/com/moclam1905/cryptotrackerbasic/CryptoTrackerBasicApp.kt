package com.moclam1905.cryptotrackerbasic

import android.app.Application
import com.moclam1905.cryptotrackerbasic.di.appModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class CryptoTrackerBasicApp : Application() {
    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidContext(this@CryptoTrackerBasicApp)
            androidLogger()

            modules(appModule)
        }
    }
}