package com.judlup.todo.models.todos

import java.time.Instant
import java.util.Date
import java.util.UUID

data class Todo (
  val id: UUID? = UUID.randomUUID(),
  val title: String,
  val completed: Boolean = false,
  val createdAt: Long = Instant.now().toEpochMilli(),
  val status: Boolean = true
)
