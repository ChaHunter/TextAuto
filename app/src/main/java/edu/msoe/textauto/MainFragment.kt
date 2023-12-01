package edu.msoe.textauto

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import edu.msoe.textauto.ConditionFragments.Conditional
import edu.msoe.textauto.DataBase.TextRepository

import edu.msoe.textauto.databinding.MainscreenBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.UUID

class MainFragment : Fragment() {
    private var _binding: MainscreenBinding? = null
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
        _binding = MainscreenBinding.inflate(inflater, container, false)
        binding.TextViews.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        val recyclerView: RecyclerView = binding.TextViews
        CoroutineScope(Dispatchers.IO).launch {
            //TextRepository.get().addRemind(Remind(UUID.randomUUID(),"Bob", " Hello"))
            val reminds = dataViewModel.getRepository().getReminds()
            val conditions = mutableListOf<List<Conditional>>()
            reminds.forEach { r -> dataViewModel.getRepository().getConditionalFromRemind(r.id) }
            val adapter = MainTextViewAdapter(reminds, conditions)
            withContext(Dispatchers.Main) {
                recyclerView.adapter = adapter
            }
        }
        binding.addTextButton.setOnClickListener {
            findNavController().navigate(
                MainFragmentDirections.actionMainFragmentToMakeText(UUID.randomUUID())
            )
        }
    }
}