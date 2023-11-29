package edu.msoe.textauto.ConditionFragments

import androidx.lifecycle.ViewModel
import java.util.Calendar

class TimeViewModel: ViewModel() {

    val c = Calendar.getInstance()
    var hour = c.get(Calendar.HOUR_OF_DAY)
    var minute = c.get(Calendar.MINUTE)
    var date = c.get(Calendar.DATE)
}