package me.inassar.restful

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.net.Uri
import android.os.Bundle
import android.support.v4.content.LocalBroadcastManager
import android.support.v7.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*
import me.inassar.restful.services.MyService

class MainActivity : AppCompatActivity() {

    private val broadcastReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent?) {
            val message = intent?.getStringExtra(MyService.MY_SERVICE_PAYLOAD)
            outputTv.append("$message,\n")
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        LocalBroadcastManager.getInstance(applicationContext)
            .registerReceiver(broadcastReceiver, IntentFilter(MyService.MY_SERVICE_MESSAGE))

        interactions()
    }

    private fun interactions() {
        runCodeBtn.setOnClickListener {

            startService(
                Intent(this@MainActivity, MyService::class.java).apply {
                    data = Uri.parse(JSON_URL)
                }
            )

            /**
             * Use the repeat function to call the service as many times as you wish
             * to test that the service will operate only once and do it's work as
             * many times as you entered
             * */
            repeat(3) {
                startService(
                    Intent(this@MainActivity, MyService::class.java).apply {
                        data = Uri.parse(JSON_URL)
                    }
                )
            }

        }
        clearBtn.setOnClickListener {
            outputTv.text = ""
        }
    }

    /**
     * Do not forget to unregister the receiver at on destroy to avoid memory leak
     * when you finish your activity
     * */
    override fun onDestroy() {
        super.onDestroy()
        LocalBroadcastManager.getInstance(applicationContext)
            .unregisterReceiver(broadcastReceiver)
    }

    companion object {
        private const val JSON_URL =
            "http://560057.youcanlearnit.net/services/json/itemsfeed.php"
    }

}
