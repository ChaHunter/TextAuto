package edu.msoe.textauto

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import edu.msoe.textauto.ConditionFragments.ConditionCategory
import edu.msoe.textauto.ConditionFragments.Conditional
import edu.msoe.textauto.databinding.ConditionDetailRecyclerViewBinding


/**
 * Viewholder for the conditions with extra details to be had
 */
class MakeTextConditionRecyclerViewViewHolder(
    private val binding: ConditionDetailRecyclerViewBinding): RecyclerView.ViewHolder(binding.root) {
    fun bind(
        condition: Conditional
    ) {

        binding.conditionTitle.text = condition.conditionName.outName

        binding.description.text = ConditionCategory.getSpecificDescription(condition)

    }
}

/**
 * Adapter class for students
 */
class MakeTextConditionRecyclerViewAdapter(
    private val conditions: List<Conditional>
) : RecyclerView.Adapter<MakeTextConditionRecyclerViewViewHolder>() {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): MakeTextConditionRecyclerViewViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ConditionDetailRecyclerViewBinding.inflate(inflater, parent, false)
        return MakeTextConditionRecyclerViewViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MakeTextConditionRecyclerViewViewHolder, position: Int) {

        holder.bind(conditions[position])
    }

    override fun getItemCount() = conditions.size
}