package com.example.roomsampleapp.ui.input

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.ViewModelProvider
import com.example.roomsampleapp.R
import com.example.roomsampleapp.data.database.AppDatabase
import com.example.roomsampleapp.data.entity.Todo
import com.example.roomsampleapp.databinding.ActivityInputBinding
import com.example.roomsampleapp.repository.TodoRepository
import com.example.roomsampleapp.repository.TodoRepositoryImpl

class InputActivity : AppCompatActivity() {

    private lateinit var binding: ActivityInputBinding

    private val viewModel : InputViewModel by lazy {
        val dao = AppDatabase.getInstance(baseContext).todoDao()
        val todoRepository = TodoRepositoryImpl(dao)
        val factory = InputViewModelFactory(todoRepository)
        ViewModelProvider(this, factory)[InputViewModel::class.java]
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityInputBinding.inflate(layoutInflater).apply {
            setContentView(root)
            lifecycleOwner = this@InputActivity
            viewmodel = viewModel
        }

        init()
    }

    fun init() {
        (intent.getSerializableExtra(ITEM) as? Todo)?.let {
            viewModel.initData(it)
        }

        viewModel.doneEvent.observe(this) {
            Toast.makeText(this, "success", Toast.LENGTH_SHORT).show()
            finish()
        }
    }

    companion object {
        private val ITEM = "ITEM"

        fun start(context: Context, todo: Todo? = null) {
            Intent(context, InputActivity::class.java).apply {
                putExtra(ITEM, todo)
            }.run {
                context.startActivity(this)
            }
        }
    }
}