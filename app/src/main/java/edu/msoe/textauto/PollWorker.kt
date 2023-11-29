package edu.msoe.textauto

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters

class PollWorker (
    private val context: Context,
    workerParameters: WorkerParameters
    ): CoroutineWorker(context, workerParameters){
        override suspend fun doWork(): Result {
            return Result.success()
        }
    }