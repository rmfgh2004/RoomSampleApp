package com.example.roomsampleapp.ui.input

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.roomsampleapp.data.entity.Todo
import com.example.roomsampleapp.repository.TodoRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class InputViewModel(
    private val todoRepository: TodoRepository
) : ViewModel() {

    private var _doneEvent = MutableLiveData<Unit>()
    val doneEvent : LiveData<Unit> = _doneEvent

    val title = MutableLiveData<String>("")
    val content = MutableLiveData<String>("")
    var todo : Todo? = null

    fun initData(todo: Todo) {
        this.todo = todo
        title.value = todo.title
        content.value = todo.content
    }

    fun insert() {
        viewModelScope.launch(Dispatchers.IO) {
            todoRepository.insert(
                todo?.copy(title = title.value!!, content = content.value!!)
                    ?: Todo(title = title.value!!, content = content.value!!)
            )
        }
        _doneEvent.postValue(Unit)
    }
}

class InputViewModelFactory(
    private val todoRepository: TodoRepository
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return InputViewModel(todoRepository) as T
    }

}