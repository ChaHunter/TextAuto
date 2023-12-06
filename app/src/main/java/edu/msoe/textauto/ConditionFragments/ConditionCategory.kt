package edu.msoe.textauto.ConditionFragments

import androidx.navigation.NavDirections
import java.util.Calendar
import java.util.UUID

enum class ConditionCategory(val outName: String,
                             val description: String,
                             val fragmentLocation: (UUID) -> NavDirections,
                            val pollWorker:
    )  {
    Time("Time", "Triggers when a specific time has passed",
        fun(id): NavDirections{ return SelectConditionFragmentDirections.actionSelectConditionFragmentToTimeFragment(id) }),
    Pizza("YES", "Not workin",
        fun(id): NavDirections{ return SelectConditionFragmentDirections.actionSelectConditionFragmentToTimeFragment(id) });

    companion object{
        //Not sure to use the strategy pattern here or not.
        fun getSpecificDescription(c :Conditional): String{
            when(c.conditionName){
                //time
                ConditionCategory.Time -> {
                    return c.data.get(3)+"/"+c.data.get(4)+"/"+c.data.get(2)+
                            "  -  "+c.data.get(0)+":"+c.data.get(1)
                }
                ConditionCategory.Pizza ->{
                    return "Not Possible"
                }
            }
        }


    }
}