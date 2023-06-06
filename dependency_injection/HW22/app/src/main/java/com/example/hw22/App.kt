package com.example.hw22

import android.app.Application
import org.koin.core.context.startKoin
import org.koin.dsl.module

class App: Application() {
    val appComponent = DaggerDaggerComponent.builder().build()

    override fun onCreate() {
        super.onCreate()
        startKoin {
            modules(module {
                single { WheelsDealer() }
                factory<BicycleFactory> { BicycleFactory(WheelsDealer(), FrameFactory()) }
            })
        }
    }
}