package com.judlup.todo.interfaces.todos.repositories

import com.judlup.todo.models.todos.Todo
import io.vertx.core.Future


interface TodosRepository {
  fun get(): Future<List<Todo>?>
  fun getById(id: String): Future<Todo>
  fun create(todo: Todo): Future<Todo>
  fun update(id: String, todo: Todo): Future<Boolean>
  fun delete(id: String): Future<Boolean>
}
