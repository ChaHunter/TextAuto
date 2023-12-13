/*
 * Course: CS4911 - 111
 * Fall 2023-2024
 * Lab 4: Persistence
 * Hunter Cha
 * 10/9/2023
 */
package edu.msoe.textauto

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ListAdapter
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import edu.msoe.textauto.ConditionFragments.Conditional
import edu.msoe.textauto.databinding.MaintextrecyclerviewBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.util.UUID


/**
 * ViewHolder for Students recycler items
 */
class MainTextViewHolder (
    private val binding: MaintextrecyclerviewBinding): RecyclerView.ViewHolder(binding.root) {
    fun bind(
        remind: Remind,
        conditions : List<Conditional>,
        context : Context
    ) {

        binding.name.text = remind.contact

        binding.textMessage.text = remind.text
        binding.recyclerView.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        val adapter = MakeTextConditionRecyclerViewAdapter(conditions)
        binding.recyclerView.adapter = adapter
        binding.recyclerView.visibility = View.GONE
        binding.root.setOnClickListener(){
            //Log.println(Log.ASSERT, "Button", "Button Clicked")
            if (binding.recyclerView.visibility == View.GONE) {
                binding.recyclerView.visibility = View.VISIBLE
                //Log.println(Log.ASSERT, "Button", "Button to visible")
            } else if (binding.recyclerView.visibility == View.VISIBLE){
                binding.recyclerView.visibility = View.GONE
                //Log.println(Log.ASSERT, "Button", "Button to Gone")
            }
        }
    }
}

/**
 * Adapter class for students
 */
class MainTextViewAdapter(
    private val reminders: List<Remind>,
    private val conditions: List<List<Conditional>>,
) : RecyclerView.Adapter<MainTextViewHolder>() {
    private lateinit var context: Context
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ) : MainTextViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        context = parent.context
        val binding = MaintextrecyclerviewBinding.inflate(inflater, parent, false)
        return MainTextViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MainTextViewHolder, position: Int) {

        holder.bind(reminders[position]
            , conditions[position],
            context)
    }

    override fun getItemCount() = reminders.size
}
