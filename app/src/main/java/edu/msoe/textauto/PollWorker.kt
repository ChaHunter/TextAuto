package edu.msoe.textauto

import android.content.Context
import android.telephony.SmsManager
import androidx.fragment.app.viewModels
import androidx.work.CoroutineWorker
import androidx.work.WorkManager
import androidx.work.WorkerParameters
import edu.msoe.textauto.ConditionFragments.ConditionCategory
import edu.msoe.textauto.ConditionFragments.Conditional
import edu.msoe.textauto.DataBase.TextRepository
import java.util.Calendar
import java.util.UUID

class PollWorker (
    private val context: Context,
    workerParameters: WorkerParameters
    ): CoroutineWorker(context, workerParameters){


        override suspend fun doWork(): Result {

            val repo = TextRepository.get()
            val id = UUID.fromString(inputData.getString("id"))

            val remind = repo.getRemind(id)
            val conditionals = repo.getConditionalFromRemind(remind.id)
            var allTrue = true
            for (conditional in conditionals) {
                if (!checkConditional(conditional)){
                    allTrue = false
                }
            }

            if (allTrue) {
                message(remind)
                WorkManager.getInstance(context).cancelWorkById(remind.id)
                conditionals.forEach{c -> repo.removeConditional(c.id)}
                repo.removeRemind(remind.id)
            }
            return Result.success()
        }

        fun message(remind: Remind){
            var sms: SmsManager = SmsManager.getDefault()
            sms.sendTextMessage(
                remind.contact, null, remind.text,
                null, null
            )
        }

        fun checkConditional(condition: Conditional): Boolean{
            when(condition.conditionName){
                (ConditionCategory.Time) -> {
                    val baseC = Calendar.getInstance()
                    val conditionC = Calendar.getInstance()
                    conditionC.set(Calendar.HOUR ,Integer.parseInt(condition.data.get(0)))
                    conditionC.set(Calendar.MINUTE ,Integer.parseInt(condition.data.get(1)))
                    conditionC.set(Calendar.YEAR ,Integer.parseInt(condition.data.get(2)))
                    conditionC.set(Calendar.MONTH ,Integer.parseInt(condition.data.get(3)))
                    conditionC.set(Calendar.DAY_OF_MONTH ,Integer.parseInt(condition.data.get(4)))
                    return baseC > conditionC
                }
                else -> {return true}
            }
        }


    }