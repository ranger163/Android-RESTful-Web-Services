package me.inassar.restful

import android.support.v7.app.AppCompatActivity
import android.widget.Toast

fun AppCompatActivity.toast(message:String){
    Toast.makeText(this,message,Toast.LENGTH_SHORT).show()
}