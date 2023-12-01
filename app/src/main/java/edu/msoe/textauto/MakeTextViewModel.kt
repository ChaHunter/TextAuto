package edu.msoe.textauto

import androidx.lifecycle.ViewModel
import edu.msoe.textauto.ConditionFragments.Conditional
import java.util.UUID

class MakeTextViewModel : ViewModel() {


    val conditions  = mutableListOf<Conditional>()

}