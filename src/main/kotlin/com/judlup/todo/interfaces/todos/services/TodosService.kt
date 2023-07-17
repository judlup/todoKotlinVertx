package com.judlup.todo.interfaces.todos.services

import com.judlup.todo.models.todos.Todo
import java.util.UUID

interface TodosService {
  fun get(): List<Todo>?
  fun getById(id: String):Todo?
  fun create(todo: Todo): Todo
  fun update(id: String, todo: Todo): Boolean
  fun delete(id: String): Boolean
}
