package com.judlup.todo.repositories.todos

import com.judlup.todo.interfaces.todos.repositories.TodosRepository
import com.judlup.todo.models.todos.Todo
import java.util.*

class TodosRepositoryImpl: TodosRepository {
  val todos: MutableList<Todo> = mutableListOf()
  override fun get(): List<Todo> {
    print(todos)
    return todos
  }

  override fun getById(id: String): Todo? {
    return todos.find { todo -> todo.id == UUID.fromString(id) }
  }

  override fun create(todo: Todo): Todo {
    todos.add(Todo(UUID.randomUUID(), todo.title))
    return todo
  }

  override fun update(id: String, todo: Todo): Boolean {
    val todoIndex = todos.indexOfFirst { todo.id == UUID.fromString(id) }
    if(todoIndex == -1) return false
    todos[todoIndex] = todo
    return true
  }

  override fun delete(id: String): Boolean {
    val todoIndex = todos.indexOfFirst { todo -> todo.id == UUID.fromString(id)}
    if(todoIndex == -1) return false
    todos.removeAt(todoIndex)
    return true
  }
}
