package edu.msoe.textauto.ConditionFragments

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.OneTimeWorkRequest
import androidx.work.WorkerParameters
import edu.msoe.textauto.DataBase.TextRepository
import java.util.UUID

class TimePollWorker(
    private val context: Context,
    workerParameters: WorkerParameters
): CoroutineWorker(context, workerParameters){


    override suspend fun doWork(): Result {

        val repo = TextRepository.get()
        val id = UUID.fromString(inputData.getString("id"))

        val condition = repo.getConditional(id)
        repo.addConditional(Conditional(condition.id, condition.conditionName,
            condition.data, condition.remind, true))


        return Result.success()
    }

    companion object{
        fun build(): OneTimeWorkRequest.Builder {
            return OneTimeWorkRequest.Builder(TimePollWorker::class.java)
        }
    }
}