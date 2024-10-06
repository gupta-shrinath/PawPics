package com.droid.pawpics

import android.app.Application
import com.droid.dogceo.core.DogCEOAPI

class PawPicsApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        DogCEOAPI.init(this)
    }
}