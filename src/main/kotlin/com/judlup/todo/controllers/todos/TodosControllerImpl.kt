package com.judlup.todo.controllers.todos

import com.judlup.todo.interfaces.todos.controllers.TodosController
import com.judlup.todo.interfaces.todos.repositories.TodosRepository
import com.judlup.todo.models.todos.Todo
import com.judlup.todo.repositories.todos.TodosRepositoryImpl
import com.judlup.todo.services.todos.TodosServiceImpl
import io.vertx.core.json.Json
import io.vertx.ext.web.RoutingContext
import java.util.UUID

class TodosControllerImpl: TodosController {

  private val todosRepositoryImpl = TodosRepositoryImpl()
  private val todosServiceImpl = TodosServiceImpl(todosRepositoryImpl)

  override fun get(ctx: RoutingContext) {
      val todos = todosServiceImpl.get()
      val map = mapOf("data" to todos)
      val response = Json.encodePrettily(map)
      ctx.response()
        .putHeader("content-type", "application/json")
        .setStatusCode(200)
        .end(response)
  }

  override fun getById(ctx: RoutingContext) {
    val id = ctx.pathParam("id")
    val todo = todosServiceImpl.getById(id)
    val map = mapOf("data" to todo)
    val response = Json.encodePrettily(todo)
      ctx.response()
        .putHeader("content-type", "application/json")
        .setStatusCode(200)
        .end(response)
  }

  override fun create(ctx: RoutingContext) {
    val body = ctx.body().asJsonObject()
    val newTodo = Todo(title = body.getString("title"))
    val newTodoCreated = todosServiceImpl.create(newTodo)
    val map = mapOf("data" to newTodoCreated,"success" to true)
    ctx.response()
        .putHeader("content-type", "application/json")
        .setStatusCode(200)
        .end(Json.encodePrettily(map))
  }

  override fun update(ctx: RoutingContext) {
    val id = ctx.pathParam("id")
    val body = ctx.body().asJsonObject()
    val todo = Todo(UUID.fromString(id),body.getString("title"), body.getString("completed").toBoolean(), body.getString("createdAt").toLong(), body.getString("status").toBoolean())
    val updateTodo = todosServiceImpl.update(id, todo)
    val response = mapOf("data" to updateTodo,"success" to true)
    ctx.response()
        .putHeader("content-type", "application/json")
        .setStatusCode(200)
        .end(Json.encodePrettily(response))
  }

  override fun delete(ctx: RoutingContext) {
    val id = ctx.pathParam("id")
    val deleteTodo = todosServiceImpl.delete(id)
    val response = mapOf("data" to deleteTodo, "success" to true)
      ctx.response()
        .putHeader("content-type", "application/json")
        .setStatusCode(200)
        .end(Json.encodePrettily(response))
  }
}
