package com.droid.pawpics

import android.app.Application
import com.droid.dogceo.core.DogCEO

class PawPicsApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        DogCEO.init(this)
    }
}