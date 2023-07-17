package com.judlup.todo.services.todos

import com.judlup.todo.interfaces.todos.repositories.TodosRepository
import com.judlup.todo.interfaces.todos.services.TodosService
import com.judlup.todo.models.todos.Todo

class TodosServiceImpl(private val todosRepository: TodosRepository): TodosService {
  override fun get(): List<Todo>? {
    return todosRepository.get()
  }

  override fun getById(id: String): Todo? {
    return todosRepository.getById(id)
  }

  override fun create(todo: Todo): Todo {
    return todosRepository.create(todo)
  }

  override fun update(id: String, todo: Todo): Boolean {
    return todosRepository.update(id, todo)
  }

  override fun delete(id: String): Boolean {
    return todosRepository.delete(id)
  }
}
