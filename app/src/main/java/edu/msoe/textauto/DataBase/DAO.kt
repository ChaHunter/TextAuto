package edu.msoe.textauto.DataBase

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import edu.msoe.textauto.Remind
import java.util.UUID

@Dao
interface DAO {
    @Query("SELECT * FROM Remind")
    fun getRemind(): List<Remind>

    @Query("SELECT * FROM Remind WHERE id=(:id)")
    fun getRemind(id: UUID): Remind

    @Query("Delete FROM Remind")
    fun deleteRemind()

    @Insert
    fun addRemind(remind: Remind)

    @Query("Delete FROM Remind WHERE id=(:id)")
    fun deleteRemind(id: UUID)
}