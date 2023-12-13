package edu.msoe.textauto

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.provider.Telephony
import android.util.Log
import android.widget.Toast
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.work.OneTimeWorkRequest
import androidx.work.WorkManager
import androidx.work.workDataOf
import edu.msoe.textauto.ConditionFragments.ConditionCategory
import edu.msoe.textauto.ConditionFragments.Conditional
import edu.msoe.textauto.DataBase.TextRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class SmsReceiver : BroadcastReceiver() {
    companion object {
        val SMS_RECEIVED = "android.provider.Telephony.SMS_RECEIVED"
    }
     override fun onReceive(context: Context?, intent: Intent?) {
         Log.println(Log.INFO, "SMSRecieve", "Working")
        if (intent?.action.equals(SMS_RECEIVED)){
            val messages = Telephony.Sms.Intents.getMessagesFromIntent(intent)
            messages.forEach { s -> Log.println(Log.INFO, "SMSRecieve", s.serviceCenterAddress.toString()) }
            Log.println(Log.INFO, "SMSRecieve", "Recieved")
            if (context != null) {
                TextRepository.initialize(context)
            }
            CoroutineScope(Dispatchers.IO).launch {
                val repo = TextRepository.get()
                val conditions = repo.getConditionals()
                val completeConditions = mutableListOf<Conditional>()
                for (condition in conditions) {
                    if (!condition.complete and (condition.conditionName == ConditionCategory.RecievedMessage)) {
                        messages.forEach { s ->
                            val number =  condition.data[0].split(" ")[1].replace("-", "")
                            if (s.serviceCenterAddress.toString() == number) {

                                repo.addConditional(
                                    Conditional(
                                        condition.id, condition.conditionName,
                                        condition.data, condition.remind, true
                                    )
                                )
                                completeConditions.add(condition)
                            }

                        }
                    }
                }
                for (c in completeConditions){
                    val remind = repo.getRemind(c.remind)
                    val workrequest = OneTimeWorkRequest.Builder(PollWorker::class.java).setInputData(
                        workDataOf("remindid" to remind.id.toString())
                    ).addTag(remind.id.toString()).build()
                    if (context != null) {
                        WorkManager.getInstance(context).enqueue(workrequest)
                    }
                    Log.println(Log.INFO, "SMSRecieve", "Message begin")
                }
            }
        }
    }
}