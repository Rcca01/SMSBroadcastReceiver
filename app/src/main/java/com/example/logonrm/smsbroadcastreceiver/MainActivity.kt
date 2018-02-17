package com.example.logonrm.smsbroadcastreceiver

import android.content.BroadcastReceiver
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_main.*
import android.support.v4.app.ActivityCompat
import android.content.pm.PackageManager
import android.support.v4.content.ContextCompat
import android.Manifest.permission
import android.Manifest.permission.RECEIVE_SMS
import android.content.Context
import android.content.IntentFilter


class MainActivity : AppCompatActivity() {

    lateinit var mReceiver : BroadcastReceiver

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if (intent != null){
            var remetente = intent.getStringExtra("remetente")
            var mensagem = intent.getStringExtra("mensagem")
            tvMessage.text = if (remetente == null && mensagem == null) " " else remetente+" : "+mensagem
        }
        requestSmsPermission();
    }

    private fun requestSmsPermission() {
        val permission = permission.RECEIVE_SMS
        val grant = ContextCompat.checkSelfPermission(this, permission)
        if (grant != PackageManager.PERMISSION_GRANTED) {
            val permission_list = arrayOfNulls<String>(1)
            permission_list[0] = permission
            ActivityCompat.requestPermissions(this, permission_list, 1)
        }
    }

    override fun onResume() {
        super.onResume()
        var intentFilter = IntentFilter("android.intent.action.SMSRECEBIDO")

        mReceiver  = object : BroadcastReceiver(){
            override fun onReceive(context: Context?, intent: Intent?) {
                var remetente = intent!!.getStringExtra("remetente")
                var mensagem= intent!!.getStringExtra("mensagem")
                tvMessage.text = remetente+" : "+mensagem
            }
        }
        registerReceiver(mReceiver,intentFilter)
    }

    override fun onPause() {
        super.onPause()
        unregisterReceiver(mReceiver)
    }
}
