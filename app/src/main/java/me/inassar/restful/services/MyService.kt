package me.inassar.restful.services

import android.app.IntentService
import android.content.Intent
import android.support.v4.content.LocalBroadcastManager
import android.util.Log

class MyService : IntentService("MyService") {
    override fun onHandleIntent(intent: Intent?) {
        val uri = intent?.data
        Log.i(TAG, "onHandleIntent: $uri")
        Thread.sleep(1000)
        LocalBroadcastManager.getInstance(applicationContext)
            .sendBroadcast(Intent(MY_SERVICE_MESSAGE).apply {
                putExtra(MY_SERVICE_PAYLOAD, "Service all done!")
            })
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
        const val MY_SERVICE_MESSAGE = "myServiceMessage"
        const val MY_SERVICE_PAYLOAD = "myServicePayload"
    }
}