package edu.msoe.textauto.DataBase

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import edu.msoe.textauto.ConditionFragments.Conditional
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

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addRemind(remind: Remind)

    @Query("Delete FROM Remind WHERE id=(:id)")
    fun deleteRemind(id: UUID)

    @Query("SELECT * FROM Conditional")
    fun getConditionals(): List<Conditional>

    @Query("SELECT * FROM Conditional WHERE id=(:id)")
    fun getConditional(id: UUID): Conditional

    @Query("SELECT * FROM Conditional WHERE remind=(:id)")
    fun getConditionalFromRemind(id: UUID): List<Conditional>

    @Query("Delete FROM Conditional")
    fun deleteConditional()

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addConditional(condtional: Conditional)

    @Query("Delete FROM Conditional WHERE id=(:id)")
    fun deleteConditional(id: UUID)
}