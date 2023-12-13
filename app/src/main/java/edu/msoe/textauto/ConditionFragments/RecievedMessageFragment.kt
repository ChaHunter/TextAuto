package edu.msoe.textauto.ConditionFragments

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.provider.ContactsContract
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.setFragmentResult
import androidx.fragment.app.setFragmentResultListener
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import edu.msoe.textauto.TextViewModel
import edu.msoe.textauto.databinding.ConditionFragmentTimeBinding
import edu.msoe.textauto.databinding.ConditionRecieveMessageBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.Calendar
import java.util.UUID

/**
 * Fragment for getting information about the Recieved message category.
 */
class RecievedMessageFragment: Fragment() {
    private var _binding: ConditionRecieveMessageBinding? = null
    private val binding
        get() = checkNotNull(_binding) {
            "Cannot access binding because it is null..."
        }
    private val dataViewModel : TextViewModel by viewModels()
    private val reciViewModel : RecievedMessageViewModel by viewModels()
    private val args: TimeFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = ConditionRecieveMessageBinding.inflate(inflater, container, false)
        //binding.TextViews.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)

        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        binding.selectcontactrecieve.setOnClickListener() {
            val intent = Intent.createChooser(Intent().apply(){
                action = Intent.ACTION_PICK
                type = ContactsContract.CommonDataKinds.Phone.CONTENT_TYPE
            }, null)
            resultLauncher.launch(intent)
        }
        binding.confirmrecievemessage.setOnClickListener(){
            val id = UUID.randomUUID()
            val data = listOf(reciViewModel.phonenumber)
            val newCondition = Conditional(id, ConditionCategory.RecievedMessage, data, args.id, false)
            CoroutineScope(Dispatchers.IO).launch {
                dataViewModel.getRepository().addConditional(newCondition)
            }


            setFragmentResult(
                "TEST", bundleOf(
                    "id" to id.toString())
            )
            parentFragmentManager.popBackStack()
        }


    }
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
                                reciViewModel.phonenumber =cursor.getString(0)
                                binding.textView2.text = reciViewModel.phonenumber
                                Log.println(Log.INFO,"Working","obtained phone")
                            }

                        }
                    }
                    Log.println(Log.INFO,"Working","Working")
                }
            }
            Log.println(Log.INFO,"Working","Working")
        }
    }
}