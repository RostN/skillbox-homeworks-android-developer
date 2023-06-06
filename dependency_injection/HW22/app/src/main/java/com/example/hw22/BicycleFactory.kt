package com.example.hw22

import dagger.Component
import dagger.Module
import javax.inject.Inject
import javax.inject.Singleton

@Component(modules = [BicycleFactory::class])
interface DaggerComponent {
    fun createBicycle():BicycleFactory
}
@Module
class BicycleFactory @Inject constructor(
    private var wheels: WheelsDealer,
    private var frame: FrameFactory){
}
@Module
class WheelsDealer @Inject constructor(){
    @Singleton
    var serialNumber = 1
}

@Module
class FrameFactory @Inject constructor(){
    var serialNumber = 2
    var color = 15000000
}