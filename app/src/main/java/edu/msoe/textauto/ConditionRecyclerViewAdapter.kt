/*
 * Course: CS4911 - 111
 * Fall 2023-2024
 * Lab 4: Persistence
 * Hunter Cha
 * 10/9/2023
 */
package edu.msoe.textauto

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ListAdapter
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import edu.msoe.textauto.databinding.ConditionalRecyclerViewBinding
import edu.msoe.textauto.databinding.MaintextrecyclerviewBinding
import java.util.UUID


/**
 * ViewHolder for Students recycler items
 */
class ConditionalViewHolder (
    private val binding: ConditionalRecyclerViewBinding): RecyclerView.ViewHolder(binding.root) {
    fun bind(
        condition: Conditional
    ) {

        binding.conditionName.text = condition.toString()


    }
}

/**
 * Adapter class for students
 */
class ConditionalAdapter(
    private val conditions: List<Conditional>
) : RecyclerView.Adapter<ConditionalViewHolder>() {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ) : ConditionalViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ConditionalRecyclerViewBinding.inflate(inflater, parent, false)
        return ConditionalViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ConditionalViewHolder, position: Int) {

        holder.bind(conditions[position])
    }

    override fun getItemCount() = conditions.size
}
