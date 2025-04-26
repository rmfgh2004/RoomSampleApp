package com.example.roomsampleapp.data.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity
data class Todo(
    @PrimaryKey(true)
    val id: Long = 0,

    val title: String,

    val content: String,

    val done: Boolean = false
) : Serializable
