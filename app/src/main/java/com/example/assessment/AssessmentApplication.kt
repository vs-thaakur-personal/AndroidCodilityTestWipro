package com.example.assessment

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class AssessmentApplication: Application() {
    override fun onCreate() {
        super.onCreate()
    }
}