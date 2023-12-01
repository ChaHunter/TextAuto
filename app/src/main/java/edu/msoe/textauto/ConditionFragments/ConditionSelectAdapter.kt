package edu.msoe.textauto.ConditionFragments

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.NavDirections
import androidx.navigation.Navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import edu.msoe.textauto.databinding.ConditionSelectRecyclerViewBinding
import java.util.UUID


/**
 * ViewHolder for Students recycler items
 */
class  ConditionSelectViewHolder (
    private val binding: ConditionSelectRecyclerViewBinding
    ): RecyclerView.ViewHolder(binding.root) {
    fun bind(
        condition: ConditionCategory,
        onClick: (NavDirections) -> Unit,
        id: UUID
    ) {
        binding.ConditionName.text = condition.outName
        binding.ConditionDescription.text = condition.description
        binding.root.setOnClickListener(){
            onClick(condition.fragmentLocation(id))
        }
    }
}

/**
 * Adapter class for students
 */
class ConditionSelectAdapter(val id : UUID,
                             val param: (NavDirections) -> Unit
    ) : RecyclerView.Adapter<ConditionSelectViewHolder>() {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ) : ConditionSelectViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ConditionSelectRecyclerViewBinding.inflate(inflater, parent, false)
        return ConditionSelectViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ConditionSelectViewHolder, position: Int) {
        holder.bind(ConditionCategory.values()[position], param, id)
    }

    override fun getItemCount() = ConditionCategory.values().size
}
