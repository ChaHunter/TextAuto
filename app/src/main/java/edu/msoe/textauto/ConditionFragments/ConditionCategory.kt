package edu.msoe.textauto.ConditionFragments

import androidx.navigation.NavDirections
import androidx.work.CoroutineWorker
import androidx.work.OneTimeWorkRequest
import androidx.work.PeriodicWorkRequest
import androidx.work.WorkRequest
import edu.msoe.textauto.PollWorker
import java.util.Calendar
import java.util.UUID
import java.util.concurrent.TimeUnit

enum class ConditionCategory(val outName: String,
                             val description: String,
                             val fragmentLocation: (UUID) -> NavDirections
)  {
    Time("Time", "Triggers when a specific time has passed",
        fun(id): NavDirections{ return SelectConditionFragmentDirections.actionSelectConditionFragmentToTimeFragment(id) },

    ),
    RecievedMessage("Recieved Message", "Triggers when you recieve a message",
        fun(id): NavDirections{return SelectConditionFragmentDirections.actionSelectConditionFragmentToTimeFragment(id) }
    ),
    Pizza("YES", "Not workin",
        fun(id): NavDirections{ return SelectConditionFragmentDirections.actionSelectConditionFragmentToTimeFragment(id) });

    companion object{
        //Not sure to use the strategy pattern here or not.
        fun getSpecificDescription(c :Conditional): String{
            when(c.conditionName){
                //time
                ConditionCategory.Time -> {
                    return c.data[3] +"/"+ c.data[4] +"/"+ c.data[2] +
                            "  -  "+ c.data[0] +":"+ c.data[1]
                }
                ConditionCategory.RecievedMessage ->{
                    return "Receiving From" + c.data[0]
                }
                ConditionCategory.Pizza ->{
                    return "Not Possible"
                }

                else -> {return "Error item does not have a description"}
            }
        }



    }
}