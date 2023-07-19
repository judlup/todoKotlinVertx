package com.judlup.todo.services.todos

import com.judlup.todo.interfaces.todos.repositories.TodosRepository
import com.judlup.todo.interfaces.todos.services.TodosService
import com.judlup.todo.models.todos.Todo
import io.vertx.core.Future
import io.vertx.core.Future.future
import io.vertx.core.Promise

class TodosServiceImpl(private val todosRepository: TodosRepository): TodosService {
  override fun get(): Future<List<Todo>?> {
    return todosRepository.get()
  }

  override fun getById(id: String): Future<Todo> {
    return todosRepository.getById(id)
  }

  override fun create(todo: Todo): Future<Todo> {
    return todosRepository.create(todo)
  }

  override fun update(id: String, todo: Todo): Future<Boolean> {
    return todosRepository.update(id, todo)
  }

  override fun delete(id: String): Future<Boolean> {
    return todosRepository.delete(id)
  }
}
