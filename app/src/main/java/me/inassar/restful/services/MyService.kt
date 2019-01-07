package me.inassar.restful.services

import android.app.IntentService
import android.content.Intent
import android.support.v4.content.LocalBroadcastManager
import android.util.Log
import me.inassar.restful.model.DataItem
import me.inassar.restful.parsers.XMLParser
import me.inassar.restful.utils.HttpHelper
import java.io.IOException

class MyService : IntentService("MyService") {
    override fun onHandleIntent(intent: Intent?) {
        val uri = intent?.data
        Log.i(TAG, "onHandleIntent: $uri")

        var response: String? = null
        try {
            response = HttpHelper.downloadUrl(uri.toString(), "nadias", "NadiasPassword")
        } catch (e: IOException) {
            e.printStackTrace()
            LocalBroadcastManager.getInstance(applicationContext)
                .sendBroadcast(Intent(MY_SERVICE_MESSAGE).apply {
                    putExtra(MY_SERVICE_EXCEPTION, e.message)
                })
        }

//        val gson = Gson()
//        val dataItems = gson.fromJson(response, Array<DataItem>::class.java)


        if (response != null) {
            val dataItems = XMLParser.parseFeed(response) as Array<DataItem>

            LocalBroadcastManager.getInstance(applicationContext)
                .sendBroadcast(Intent(MY_SERVICE_MESSAGE).apply {
                    putExtra(MY_SERVICE_PAYLOAD, dataItems)
                })
        }
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
        const val MY_SERVICE_EXCEPTION = "myServiceException"
    }
}