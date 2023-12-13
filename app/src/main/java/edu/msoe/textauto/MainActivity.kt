package edu.msoe.textauto

import android.content.Intent
import android.content.IntentFilter
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.constraintlayout.widget.Constraints
import androidx.work.OneTimeWorkRequest
import androidx.work.WorkManager
import edu.msoe.textauto.DataBase.TextRepository


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        TextRepository.initialize(this)

        setContentView(R.layout.activity_main)
        val filter = IntentFilter()
        filter.addAction(SmsReceiver.SMS_RECEIVED)
        val reciever = SmsReceiver()
        registerReceiver(reciever, filter)

    }
}