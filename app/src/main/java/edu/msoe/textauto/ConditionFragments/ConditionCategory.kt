package edu.msoe.textauto.ConditionFragments

import androidx.navigation.NavDirections
import java.util.UUID

enum class ConditionCategory(val outName: String,
                             val description: String,
                             val fragmentLocation: (UUID) -> NavDirections
    )  {
    Time("Time", "Triggers when a specific time has passed",
        { id -> SelectConditionFragmentDirections.actionSelectConditionFragmentToTimeFragment(id) }),
    Pizza("YES", "Not workin",
        { id -> SelectConditionFragmentDirections.actionSelectConditionFragmentToTimeFragment(id) });


}