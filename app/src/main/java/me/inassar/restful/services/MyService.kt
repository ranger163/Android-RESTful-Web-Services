package me.inassar.restful.services

import android.app.IntentService
import android.content.Intent
import android.util.Log

class MyService : IntentService("MyService") {
    override fun onHandleIntent(intent: Intent?) {
        val uri = intent?.data
        Log.i(TAG, "onHandleIntent: $uri")
        Thread.sleep(1000)
    }

    override fun onCreate() {
        super.onCreate()
        Log.i(TAG, "onCreate")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.i(TAG, "onDestroy")
    }

    companion object {
        private val TAG = MyService::class.java.simpleName
    }
}