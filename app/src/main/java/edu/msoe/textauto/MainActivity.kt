package edu.msoe.textauto

import android.Manifest.permission.SEND_SMS
import android.content.Intent
import android.content.IntentFilter
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.constraintlayout.widget.Constraints
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.work.OneTimeWorkRequest
import androidx.work.WorkManager
import edu.msoe.textauto.DataBase.TextRepository


/**
 * Main activity that is the fragment host.
 */
class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        TextRepository.initialize(this)

        setContentView(R.layout.activity_main)
        val filter = IntentFilter()
        filter.addAction(SmsReceiver.SMS_RECEIVED)
        val reciever = SmsReceiver()
        registerReceiver(reciever, filter)

        //Didn't have a change to implement permissions on start up. Needs to be
        //Done manually.
//        if(ContextCompat.checkSelfPermission(this, SEND_SMS) != PackageManager.PERMISSION_GRANTED){
//            ActivityCompat.requestPermissions(this, arrayOf<String>("android.permission.SEND_SMS"))
//        }

    }
}