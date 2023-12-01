package edu.msoe.textauto

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import edu.msoe.textauto.databinding.AddTextBinding
import android.provider.ContactsContract
import android.telephony.SmsManager
import androidx.fragment.app.setFragmentResultListener
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.work.OneTimeWorkRequest
import androidx.work.PeriodicWorkRequest
import androidx.work.WorkManager
import androidx.work.workDataOf
import edu.msoe.textauto.ConditionFragments.TimeFragmentArgs
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.UUID
import java.util.concurrent.TimeUnit

class MakeText : Fragment() {

    private var _binding: AddTextBinding? = null
    private val binding
        get() = checkNotNull(_binding) {
            "Cannot access binding because it is null..."
        }
    private val args: MakeTextArgs by navArgs()
    private val dataViewModel : TextViewModel by viewModels()
    private val fragViewModel : MakeTextViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = AddTextBinding.inflate(inflater, container, false)
        binding.recyclerView.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.button.setOnClickListener() {
            val intent =Intent.createChooser(Intent().apply(){
                action = Intent.ACTION_PICK
                type = ContactsContract.CommonDataKinds.Phone.CONTENT_TYPE
            }, null)
            resultLauncher.launch(intent)
        }
        binding.recyclerView.adapter = MakeTextConditionRecyclerViewAdapter(fragViewModel.conditions)
        binding.AddCondition.setOnClickListener(){
            findNavController().navigate(
                MakeTextDirections.actionMakeTextToSelectConditionFragment(args.id)
            )
        }
        binding.Confirm.setOnClickListener(){
            val remind = Remind(args.id, rPhoneNumber,
                binding.TextInput.text.toString())

            CoroutineScope(Dispatchers.IO).launch {
                dataViewModel.getRepository().addRemind(remind)
                startRequest(remind)
                parentFragmentManager.popBackStack()
            }
        }




        setFragmentResultListener("TEST"){s,b ->
            CoroutineScope(Dispatchers.IO).launch {
                val condition = dataViewModel.getRepository().getConditional(
                    UUID.fromString(b.getString("id")))
                fragViewModel.conditions.add(condition)
                val adapter = binding.recyclerView.adapter
                adapter?.notifyItemInserted(0)
            }
        }
    }

    private fun startRequest(remind :Remind) {
        val workrequest = PeriodicWorkRequest.Builder(PollWorker::class.java,
            PeriodicWorkRequest.MIN_PERIODIC_INTERVAL_MILLIS, TimeUnit.MILLISECONDS,
            PeriodicWorkRequest.MIN_PERIODIC_FLEX_MILLIS, TimeUnit.MILLISECONDS
            ).setInputData(
            workDataOf("id" to remind.id.toString())).addTag(remind.id.toString()).build()
//        val workrequest = OneTimeWorkRequest.Builder(PollWorker::class.java).setInputData(
//            workDataOf("id" to remind.id.toString())).addTag(remind.workTag.toString()).build()
        WorkManager.getInstance(requireContext()).enqueue(workrequest)

    }

    lateinit var rPhoneNumber : String
    var resultLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            // There are no request codes
            val data: Intent? = result.data
            if (data != null) {
                if (data.data != null) {
                    var data2 = data.data!!
                    val fields = arrayOf(ContactsContract.CommonDataKinds.Phone.NUMBER)
                    val test = activity?.contentResolver?.query(data2,
                        fields, null, null, null
                    ).use{
                        cursor ->
                        if (cursor != null) {
                            if (cursor.moveToFirst()){
                                //val index = cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER)
                                rPhoneNumber =cursor.getString(0)
                                Log.println(Log.INFO,"Working","Working")
                            }

                        }
                    }
                    Log.println(Log.INFO,"Working","Working")
                }
            }
            Log.println(Log.INFO,"Working","Working")
        }
    }

    fun handleuri(uri : Uri?){
        Log.println(Log.INFO,"Yes",uri.toString())
    }

}