package edu.msoe.textauto.ConditionFragments

import android.app.DatePickerDialog
import android.app.Dialog
import android.app.TimePickerDialog
import android.os.Bundle
import android.text.format.DateFormat
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.DatePicker
import android.widget.TimePicker
import androidx.core.os.bundleOf
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.Fragment
import androidx.fragment.app.setFragmentResult
import androidx.fragment.app.setFragmentResultListener
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import edu.msoe.textauto.TextViewModel
import edu.msoe.textauto.databinding.ConditionFragmentTimeBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.Calendar
import java.util.UUID

class TimeFragment : Fragment(){
    private var _binding: ConditionFragmentTimeBinding? = null
    private val binding
        get() = checkNotNull(_binding) {
            "Cannot access binding because it is null..."
        }
    private val dataViewModel : TextViewModel by viewModels()
    private val timeViewModel : TimeViewModel by viewModels()
    private val args: TimeFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = ConditionFragmentTimeBinding.inflate(inflater, container, false)
        //binding.TextViews.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        setFragmentResultListener(TimePickerFragment.TIMEKEY){s, b ->
            val h = b.getInt(TimePickerFragment.HOURKEY)
            val m = b.getInt(TimePickerFragment.MINUTEKEY)
            timeViewModel.c.set(Calendar.HOUR_OF_DAY, h)
            timeViewModel.c.set(Calendar.MINUTE, m)
            binding.TimeText.text = "Hour: "+h+" Minute: "+m
        }
        setFragmentResultListener(DatePickerFragment.DATEKEY){s, b ->
            val y = b.getInt(DatePickerFragment.YEARKEY)
            val m = b.getInt(DatePickerFragment.MONTHKEY)
            val d = b.getInt(DatePickerFragment.DAYKEY)
            timeViewModel.c.set(Calendar.YEAR, y)
            timeViewModel.c.set(Calendar.MONTH, m)
            timeViewModel.c.set(Calendar.DAY_OF_MONTH, d)
            binding.DateText.text = "Year: "+y+" Month: "+m+" Day: "+d
        }
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.confirm.setOnClickListener(){
            val id = UUID.randomUUID()
            val data = listOf(timeViewModel.c.get(Calendar.HOUR_OF_DAY).toString(),
                timeViewModel.c.get(Calendar.MINUTE).toString(),
                timeViewModel.c.get(Calendar.YEAR).toString(),
                timeViewModel.c.get(Calendar.MONTH).toString(),
                timeViewModel.c.get(Calendar.DAY_OF_MONTH).toString()
            )
            val newCondition = Conditional(id, ConditionCategory.Time, data, args.id, false)
            CoroutineScope(Dispatchers.IO).launch {
                dataViewModel.getRepository().addConditional(newCondition)
            }


            setFragmentResult(
                "TEST", bundleOf(
                    "id" to id.toString()))
            parentFragmentManager.popBackStack()
        }
        binding.Picktime.setOnClickListener(){
            TimePickerFragment(timeViewModel.c).show(parentFragmentManager, "TimePicker")
        }
        binding.Datepick.setOnClickListener(){
            DatePickerFragment(timeViewModel.c).show(parentFragmentManager, "DatePicker")
        }

    }


}

class TimePickerFragment(val c : Calendar) : DialogFragment(), TimePickerDialog.OnTimeSetListener {
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return TimePickerDialog(activity, this, c.get(Calendar.HOUR_OF_DAY),
            c.get(Calendar.MINUTE), DateFormat.is24HourFormat(activity))

    }

    override fun onTimeSet(view: TimePicker?, hourOfDay: Int, minute: Int) {

        setFragmentResult(TIMEKEY, bundleOf(HOURKEY to hourOfDay,
            MINUTEKEY to minute))
    }

    companion object {
        val HOURKEY = "Hour"
        val MINUTEKEY = "Minute"
        val TIMEKEY = "TIMEKEY"
    }
}

class DatePickerFragment(val c: Calendar) : DialogFragment(), DatePickerDialog.OnDateSetListener {
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {

        return DatePickerDialog(requireContext(), this, c.get(Calendar.YEAR),
            c.get(Calendar.MONTH), c.get(Calendar.DAY_OF_MONTH))

    }

    companion object {
        val YEARKEY = "Year"
        val MONTHKEY = "Month"
        val DAYKEY = "Day"
        val DATEKEY = "DATEKEY"
    }

    override fun onDateSet(view: DatePicker?, year: Int, month: Int, dayOfMonth: Int) {
        setFragmentResult(DATEKEY, bundleOf(YEARKEY to year,
            MONTHKEY to month,
            DAYKEY to dayOfMonth
            ))
    }
}