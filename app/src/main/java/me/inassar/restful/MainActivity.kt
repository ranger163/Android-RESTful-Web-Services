package me.inassar.restful

import android.os.AsyncTask
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        interactions()
    }

    private fun interactions() {
        runCodeBtn.setOnClickListener {
            val task = MyAsyncTask()
            task.execute("String 1", "String 2", "String 3")
        }
        clearBtn.setOnClickListener {
            outputTv.text = ""
        }
    }

    inner class MyAsyncTask : AsyncTask<String, String, Void>() {

        override fun doInBackground(vararg params: String?): Void? {
            params.forEach {
                publishProgress(it)
                Thread.sleep(1000)
            }
            return null
        }

        override fun onProgressUpdate(vararg values: String?) {
            super.onProgressUpdate(*values)
            outputTv.append("${values[0]},\n")
        }

        override fun onPreExecute() {
            super.onPreExecute()
        }

        override fun onPostExecute(result: Void?) {
            super.onPostExecute(result)
        }

    }

}
