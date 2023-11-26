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
import edu.msoe.textauto.databinding.MaintextrecyclerviewBinding
import java.util.UUID


/**
 * ViewHolder for Students recycler items
 */
class MainTextViewHolder (
    private val binding: MaintextrecyclerviewBinding): RecyclerView.ViewHolder(binding.root) {
    fun bind(
        remind: Remind
    ) {

        binding.name.text = remind.contact

        binding.textMessage.text = remind.text

    }
}

/**
 * Adapter class for students
 */
class MainTextViewAdapter(
    private val reminders: List<Remind>
) : RecyclerView.Adapter<MainTextViewHolder>() {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ) : MainTextViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = MaintextrecyclerviewBinding.inflate(inflater, parent, false)
        return MainTextViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MainTextViewHolder, position: Int) {

        holder.bind(reminders[position])
    }

    override fun getItemCount() = reminders.size
}
