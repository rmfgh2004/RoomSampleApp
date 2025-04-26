package com.example.roomsampleapp.repository

import com.example.roomsampleapp.data.dao.TodoDao
import com.example.roomsampleapp.data.entity.Todo
import kotlinx.coroutines.flow.Flow

interface TodoRepository {

    fun loadAll() : Flow<List<Todo>>

    suspend fun insert(todo: Todo)

    suspend fun update(todo: Todo)

    suspend fun delete(todo: Todo)

}