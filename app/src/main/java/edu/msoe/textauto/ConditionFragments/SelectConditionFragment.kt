package edu.msoe.textauto.ConditionFragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.NavDirections
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import edu.msoe.textauto.TextViewModel
import edu.msoe.textauto.databinding.ConditionSelectViewBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.UUID

class SelectConditionFragment: Fragment() {
    private var _binding: ConditionSelectViewBinding? = null
    private val binding
        get() = checkNotNull(_binding) {
            "Cannot access binding because it is null..."
        }
    private val dataViewModel : TextViewModel by viewModels()
    private val args: SelectConditionFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = ConditionSelectViewBinding.inflate(inflater, container, false)
        binding.recyclerview.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        val recyclerView: RecyclerView = binding.recyclerview
        CoroutineScope(Dispatchers.IO).launch {
            //TextRepository.get().addRemind(Remind(UUID.randomUUID(),"Bob", " Hello"))
            val adapter = ConditionSelectAdapter(){navDirection:(UUID) -> NavDirections ->
                findNavController().navigate(
                    navDirection(args.id)
                )
            }
            withContext(Dispatchers.Main) {
                recyclerView.adapter = adapter
            }
        }

    }
}