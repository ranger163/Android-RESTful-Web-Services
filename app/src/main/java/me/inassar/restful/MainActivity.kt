package me.inassar.restful

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*
import me.inassar.restful.services.MyService

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

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

    companion object {
        private const val JSON_URL =
            "http://560057.youcanlearnit.net/services/json/itemsfeed.php"
    }

}
