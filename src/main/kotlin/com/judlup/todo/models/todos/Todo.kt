package com.judlup.todo.models.todos

import java.time.Instant
import java.util.Date
import java.util.UUID

data class Todo (
  val id: UUID? = UUID.randomUUID(),
  var title: String,
  var completed: Boolean = false,
  var createdAt: Long = Instant.now().toEpochMilli(),
  val status: Boolean = true
)
