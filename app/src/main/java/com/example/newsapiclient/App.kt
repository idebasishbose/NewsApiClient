package com.example.newsapiclient

import android.app.Application
import com.orhanobut.logger.AndroidLogAdapter
import com.orhanobut.logger.Logger
import dagger.hilt.android.HiltAndroidApp
import timber.log.Timber
import timber.log.Timber.DebugTree


@HiltAndroidApp
class App : Application() {
    override fun onCreate() {
        super.onCreate()
        if (BuildConfig.DEBUG) {
//            Timber.plant(Timber.DebugTree())
            Logger.addLogAdapter(AndroidLogAdapter())

            Timber.plant(
                object : DebugTree() {
                    override fun log(priority: Int, tag: String?, message: String, t: Throwable?) {
                        Logger.log(priority, tag, message, t)
                    }
                },
            )
        }
    }
}