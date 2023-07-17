package com.judlup.todo.routes.todos

import com.judlup.todo.controllers.todos.TodosControllerImpl
import io.vertx.core.AbstractVerticle
import io.vertx.core.Vertx
import io.vertx.ext.web.Router
import io.vertx.ext.web.handler.BodyHandler

class TodoRouter(private val vertx: Vertx) {

  val router: Router = Router.router((vertx))
  private var todoControllerImpl = TodosControllerImpl()

  init {
    router.route().handler(BodyHandler.create())

    router.get("/").handler(todoControllerImpl::get)

    router.get("/:id").handler(todoControllerImpl::getById)

    router.post("/").handler(todoControllerImpl::create)

    router.put("/:id").handler(todoControllerImpl::update)

    router.delete("/:id").handler(todoControllerImpl::delete)
  }
}
