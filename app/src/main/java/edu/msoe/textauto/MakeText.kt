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
import java.net.URI
import android.provider.ContactsContract
import android.telephony.SmsManager
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.loader.content.CursorLoader
import edu.msoe.textauto.DataBase.TextRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.UUID

class MakeText : Fragment() {

    private var _binding: AddTextBinding? = null
    private val binding
        get() = checkNotNull(_binding) {
            "Cannot access binding because it is null..."
        }
    private val dataViewModel : TextViewModel by viewModels()

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
        binding.Confirm.setOnClickListener(){
            var sms : SmsManager  = SmsManager.getDefault()
            sms.sendTextMessage(rPhoneNumber, null, binding.TextInput.text.toString(),
                null, null)
            CoroutineScope(Dispatchers.IO).launch {
                dataViewModel.getRepository().addRemind(Remind(UUID.randomUUID(), rPhoneNumber, binding.TextInput.text.toString()))

                parentFragmentManager.popBackStack()
            }
        }
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