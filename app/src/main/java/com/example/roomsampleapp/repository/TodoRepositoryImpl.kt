package com.example.roomsampleapp.repository

import com.example.roomsampleapp.data.dao.TodoDao
import com.example.roomsampleapp.data.entity.Todo
import kotlinx.coroutines.flow.Flow

class TodoRepositoryImpl(private val todoDao: TodoDao) : TodoRepository {

    override fun loadAll(): Flow<List<Todo>> {
        return todoDao.selectAll()
    }

    override suspend fun insert(todo: Todo) {
        todoDao.insert(todo)
    }

    override suspend fun update(todo: Todo) {
        todoDao.insert(todo)
    }

    override suspend fun delete(todo: Todo) {
        todoDao.delete(todo)
    }
}