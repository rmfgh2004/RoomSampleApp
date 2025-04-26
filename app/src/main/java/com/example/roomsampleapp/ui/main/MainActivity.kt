package com.example.roomsampleapp.ui.main

import android.os.Bundle
import android.util.Log
import android.widget.LinearLayout
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.isVisible
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView.LayoutManager
import com.example.roomsampleapp.R
import com.example.roomsampleapp.data.database.AppDatabase
import com.example.roomsampleapp.data.entity.Todo
import com.example.roomsampleapp.databinding.ActivityMainBinding
import com.example.roomsampleapp.repository.TodoRepository
import com.example.roomsampleapp.repository.TodoRepositoryImpl
import com.example.roomsampleapp.ui.input.InputActivity
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val viewModel : MainViewModel by lazy {
        val dao = AppDatabase.getInstance(baseContext).todoDao()
        val todoRepository = TodoRepositoryImpl(dao)
        val factory = MainViewModelFactory(todoRepository)
        ViewModelProvider(this, factory)[MainViewModel::class.java]
    }
    private val adapter : TodoAdapter by lazy {
        TodoAdapter(Handler())
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater).apply {
            setContentView(root)
            lifecycleOwner = this@MainActivity
            view = this@MainActivity

            recyclerView.adapter = adapter
            recyclerView.layoutManager = LinearLayoutManager(this@MainActivity)
            val decoration = DividerItemDecoration(this@MainActivity, LinearLayout.VERTICAL)
            recyclerView.addItemDecoration(decoration)
        }

        lifecycleScope.launch {
            viewModel.todoList
                .flowWithLifecycle(lifecycle, Lifecycle.State.STARTED)
                .collectLatest {
                    Log.d("askask", it.size.toString())
                    binding.emptyTextView.isVisible = it.isEmpty()
                    binding.recyclerView.isVisible = it.isNotEmpty()
                    adapter.submitList(it)
                }
        }
    }

    fun onClickAdd() {
        InputActivity.start(this)
    }

    inner class Handler {
        fun onClickTodo(todo: Todo) {
            InputActivity.start(this@MainActivity, todo)
        }

        fun onLongClickTodo(todo: Todo) :Boolean {
            viewModel.deleteTodo(todo)
            Toast.makeText(this@MainActivity, "delete", Toast.LENGTH_SHORT).show()
            return false
        }

        fun onCheckTodo(todo: Todo, isCheck: Boolean) {
            viewModel.updateTodo(todo.copy(done = isCheck))
        }
    }
}
