package edu.msoe.textauto

import androidx.lifecycle.ViewModel
import edu.msoe.textauto.ConditionFragments.Conditional
import java.util.UUID

class MakeTextViewModel : ViewModel() {
    fun getConditionUUID(): List<UUID> {
        val result = mutableListOf<UUID>()
        conditions.forEach { c-> result.add(c.id) }
        return result
    }
    val remindID = UUID.randomUUID()
    val conditions  = mutableListOf<Conditional>()

}