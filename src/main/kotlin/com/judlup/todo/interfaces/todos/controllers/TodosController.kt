package com.judlup.todo.interfaces.todos.controllers

import com.judlup.todo.models.todos.Todo
import io.vertx.ext.web.RoutingContext

interface TodosController {
  fun get(ctx: RoutingContext)
  fun getById(ctx: RoutingContext )
  fun create(ctx: RoutingContext)
  fun update(ctx: RoutingContext)
  fun delete(ctx: RoutingContext)
}
