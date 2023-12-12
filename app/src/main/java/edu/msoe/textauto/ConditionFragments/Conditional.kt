package edu.msoe.textauto.ConditionFragments

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.UUID

@Entity
data class Conditional (
    @PrimaryKey
    val id : UUID,
    val conditionName : ConditionCategory,
    val data : List<String>,
    val remind : UUID,
    val complete : Boolean
)
