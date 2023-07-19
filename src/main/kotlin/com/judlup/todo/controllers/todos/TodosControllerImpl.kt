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
      var result: List<Todo>? = null
      todosServiceImpl.get().onComplete { ar ->
        if(ar.succeeded()){
          result = ar.result()
          val map = mapOf("data" to result)
          val response = Json.encodePrettily(map)
          ctx.response()
            .putHeader("content-type", "application/json")
            .setStatusCode(200)
            .end(response)
        } else{
          ctx.response()
            .putHeader("content-type", "application/json")
            .setStatusCode(500)
            .end()
        }
      }

  }

  override fun getById(ctx: RoutingContext) {
    val id = ctx.pathParam("id")
    val todo = todosServiceImpl.getById(id).onComplete { ar ->
      if(ar.succeeded()){
        val map = mapOf("data" to ar.result())
        val response = Json.encodePrettily(map)
          ctx.response()
            .putHeader("content-type", "application/json")
            .setStatusCode(200)
            .end(response)
      }else{
          ctx.response()
            .putHeader("content-type", "application/json")
            .setStatusCode(404)
            .end()
      }
    }

  }

  override fun create(ctx: RoutingContext) {
    val body = ctx.body().asJsonObject()
    var res: Todo? = null
    val newTodo = Todo(title = body.getString("title"))
    todosServiceImpl.create(newTodo).onComplete { ar ->
      if(ar.succeeded()){
        res = Todo(ar.result().id, ar.result().title, ar.result().completed, ar.result().createdAt, ar.result().status)
        val map = mapOf("data" to res,"success" to true)
        ctx.response()
            .putHeader("content-type", "application/json")
            .setStatusCode(200)
            .end(Json.encodePrettily(map))
      }else{
        ctx.response()
            .putHeader("content-type", "application/json")
            .setStatusCode(500)
            .end()
      }
    }
  }

  override fun update(ctx: RoutingContext) {
    val id = ctx.pathParam("id")
    val body = ctx.body().asJsonObject()
    val todo = Todo(UUID.fromString(id),body.getString("title"), body.getString("completed").toBoolean(), body.getString("createdAt").toLong(), body.getString("status").toBoolean())
    val updateTodo = todosServiceImpl.update(id, todo).onComplete { ar ->
      if(ar.succeeded()){
        val result = ar.result()
        val response = mapOf("data" to result,"success" to true)
        ctx.response()
          .putHeader("content-type", "application/json")
          .setStatusCode(200)
          .end(Json.encodePrettily(response))
      }else {
        ctx.response()
          .putHeader("content-type", "application/json")
          .setStatusCode(500)
          .end()
      }
    }
  }

  override fun delete(ctx: RoutingContext) {
    val id = ctx.pathParam("id")
    val deleteTodo = todosServiceImpl.delete(id).onComplete { ar ->
      if(ar.succeeded()){
        val result = ar.result()
        val response = mapOf("data" to result, "success" to true)
        ctx.response()
          .putHeader("content-type", "application/json")
          .setStatusCode(200)
          .end(Json.encodePrettily(response))
      }else {
        ctx.response()
          .putHeader("content-type", "application/json")
          .setStatusCode(500)
          .end()
      }
    }

  }
}
