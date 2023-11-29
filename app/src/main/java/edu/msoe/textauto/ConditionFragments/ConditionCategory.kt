package edu.msoe.textauto.ConditionFragments

import androidx.navigation.NavDirections

enum class ConditionCategory(val outName: String,
                             val description: String,
                             val fragmentLocation: NavDirections
    )  {
    Time("Time", "Triggers when a specific time has passed",
        SelectConditionFragmentDirections.actionSelectConditionFragmentToTimeFragment()),
    Pizza("YES", "Not workin",
        SelectConditionFragmentDirections.actionSelectConditionFragmentToTimeFragment());


}