package edu.msoe.textauto.DataBase

import androidx.room.Database
import androidx.room.RoomDatabase
import edu.msoe.textauto.ConditionFragments.Conditional
import edu.msoe.textauto.Remind

@Database(entities =[Remind::class, Conditional::class], version = 1)
abstract class TextDataBase : RoomDatabase() {
    abstract fun classDao(): DAO

}