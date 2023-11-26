package edu.msoe.textauto.DataBase

import android.content.Context
import androidx.room.Room
import edu.msoe.textauto.Remind
import java.util.UUID
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

const val DATABASE_NAME = "test"

class TextRepository private constructor(context: Context) {

    private val NUMBER_OF_THREADS = 4
    val databaseWriteExecutor: ExecutorService =
        Executors.newFixedThreadPool(NUMBER_OF_THREADS)

    private val database: TextDataBase = Room
        .databaseBuilder(
            context.applicationContext,
            TextDataBase::class.java,
            DATABASE_NAME
        ).build()


    companion object {
        private var INSTANCE: TextRepository? = null

        fun initialize(context: Context) {
            if (INSTANCE == null) {
                INSTANCE = TextRepository(context)
            }
        }



        fun get(): TextRepository {
            return INSTANCE ?: throw IllegalStateException("Repository must be initialized")
        }
    }

    fun getReminds(): List<Remind> = database.classDao().getRemind()
    suspend fun getRemind(id: UUID): Remind = database.classDao().getRemind(id)
    suspend fun addRemind(remind: Remind) = database.classDao().addRemind(remind)
    suspend fun removeRemind(id:UUID) = database.classDao().deleteRemind(id)

}