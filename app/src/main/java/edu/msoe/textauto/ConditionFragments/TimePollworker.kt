package edu.msoe.textauto.ConditionFragments

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.OneTimeWorkRequest
import androidx.work.WorkerParameters
import edu.msoe.textauto.DataBase.TextRepository
import java.util.Calendar
import java.util.UUID
import java.util.concurrent.TimeUnit

/**
 * Main Poll worker for Time Condition
 */
class TimePollWorker(
    private val context: Context,
    workerParameters: WorkerParameters
): CoroutineWorker(context, workerParameters){


    override suspend fun doWork(): Result {
        TextRepository.initialize(context)
        val repo = TextRepository.get()
        val id = UUID.fromString(inputData.getString("id"))

        val condition = repo.getConditional(id)
        repo.addConditional(Conditional(condition.id, condition.conditionName,
            condition.data, condition.remind, true))


        return Result.success()
    }

    companion object{
        fun build(condition: Conditional): OneTimeWorkRequest.Builder {
            val baseC = Calendar.getInstance()
            val conditionC = Calendar.getInstance()
            conditionC.set(Calendar.HOUR_OF_DAY ,Integer.parseInt(condition.data.get(0)))
            conditionC.set(Calendar.MINUTE ,Integer.parseInt(condition.data.get(1)))
            conditionC.set(Calendar.YEAR ,Integer.parseInt(condition.data.get(2)))
            conditionC.set(Calendar.MONTH ,Integer.parseInt(condition.data.get(3)))
            conditionC.set(Calendar.DAY_OF_MONTH ,Integer.parseInt(condition.data.get(4)))
            val time = (conditionC.timeInMillis-baseC.timeInMillis)
            return OneTimeWorkRequest.Builder(TimePollWorker::class.java).setInitialDelay(time, TimeUnit.MILLISECONDS)
        }
    }
}