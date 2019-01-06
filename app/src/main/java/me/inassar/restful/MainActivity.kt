package me.inassar.restful

import android.content.Context
import android.os.Bundle
import android.support.v4.app.LoaderManager
import android.support.v4.content.AsyncTaskLoader
import android.support.v4.content.Loader
import android.support.v7.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        interactions()
    }

    /** Notice that supportLoaderManager is deprecated at API 28,
     *  and it's recommended to start using ViewModel & LiveData
     **/
    private fun interactions() {
        runCodeBtn.setOnClickListener {
            /**
             * Use restartLoader instead of initLoader to avoid
             * bypassing loadInBackground function*/
            supportLoaderManager.restartLoader(0, null, object : LoaderManager.LoaderCallbacks<String> {
                override fun onCreateLoader(id: Int, args: Bundle?): Loader<String> {
                    outputTv.append("Creating loader\n")
                    return MyTaskLoader((this@MainActivity))
                }

                override fun onLoadFinished(loader: Loader<String>, data: String?) {
                    outputTv.append("Loader finished, returned: $data")
                }

                override fun onLoaderReset(loader: Loader<String>) {

                }

            }).forceLoad()
        }
        clearBtn.setOnClickListener {
            outputTv.text = ""
        }
    }

    companion object {
        private class MyTaskLoader(context: Context) : AsyncTaskLoader<String>(context) {
            override fun loadInBackground(): String? {
                Thread.sleep(1000)
                return "From the loader"
            }

            /**
             * You could use this function to edit your result before
             * displaying it.
             * */
            override fun deliverResult(data: String?) {
                val result = "$data , delivered\n"
                super.deliverResult(result)
            }
        }
    }

}
