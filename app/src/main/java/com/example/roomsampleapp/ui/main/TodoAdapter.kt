package com.example.roomsampleapp.ui.main

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.roomsampleapp.data.entity.Todo
import com.example.roomsampleapp.databinding.ItemTodoBinding

class TodoAdapter(
    private val handler: MainActivity.Handler
) : ListAdapter<Todo, TodoAdapter.ViewHolder>(diffUtil) {


    class ViewHolder(
        private val binding: ItemTodoBinding,
        private val handler: MainActivity.Handler
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(todo: Todo) {
            binding.todo = todo
            binding.handler = handler
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TodoAdapter.ViewHolder {
        return TodoAdapter.ViewHolder(
            ItemTodoBinding.inflate(LayoutInflater.from(parent.context), parent, false),
            handler
        )
    }

    override fun onBindViewHolder(holder: TodoAdapter.ViewHolder, position: Int) {
        holder.bind(getItem(position))
        Log.d("askask", "onBindViewHolder")
    }

    companion object {
        val diffUtil = object : DiffUtil.ItemCallback<Todo>() {
            override fun areItemsTheSame(oldItem: Todo, newItem: Todo): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: Todo, newItem: Todo): Boolean {
                return oldItem == newItem
            }


        }
    }
}