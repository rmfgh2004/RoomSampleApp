package com.example.roomsampleapp.repository

import androidx.lifecycle.LiveData
import com.example.roomsampleapp.data.dao.TodoDao
import com.example.roomsampleapp.data.entity.Todo
import kotlinx.coroutines.flow.Flow

interface TodoRepository {

    fun loadAll() : LiveData<List<Todo>>

    suspend fun insert(todo: Todo)

    suspend fun update(todo: Todo)

    suspend fun delete(todo: Todo)

}