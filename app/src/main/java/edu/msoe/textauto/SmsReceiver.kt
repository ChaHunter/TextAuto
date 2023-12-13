package edu.msoe.textauto

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.provider.Telephony
import android.util.Log
import android.widget.Toast


class SmsReceiver : BroadcastReceiver() {
    companion object {
        val SMS_RECEIVED = "android.provider.Telephony.SMS_RECEIVED"
    }
     override fun onReceive(context: Context?, intent: Intent?) {
        if (intent?.action.equals(SMS_RECEIVED)){
            val messages = Telephony.Sms.Intents.getMessagesFromIntent(intent)
            messages.forEach { s -> Log.println(Log.INFO, "SMSRecieve", s.serviceCenterAddress.toString()) }
            Log.println(Log.INFO, "SMSRecieve", "Recieved")

        }
    }
}