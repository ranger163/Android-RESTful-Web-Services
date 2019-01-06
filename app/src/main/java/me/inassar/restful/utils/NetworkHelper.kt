package me.inassar.restful.utils

import android.content.Context
import android.net.ConnectivityManager

object NetworkHelper {
    fun hasNetworkAccess(context: Context): Boolean {
        val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        return try {
            val activeNetwork = connectivityManager.activeNetworkInfo
            activeNetwork != null && activeNetwork.isConnectedOrConnecting
        } catch (e: Exception) {
            e.printStackTrace()
            false
        }
    }
}