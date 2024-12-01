package com.example.notepadtest

data class Note(
    val id: Long = 0,
    val title: String,
    val content: String,
    val modificationDate: String,
    val type: String,
    val isDone: Boolean, // 新增字段

)
