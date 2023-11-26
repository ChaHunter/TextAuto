package edu.msoe.textauto

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.UUID

@Entity
data class Remind (
    @PrimaryKey
    val id : UUID,
    val contact : String,
    val text : String
)