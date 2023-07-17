package com.judlup.todo

import com.judlup.todo.routes.todos.TodoRouter
import io.vertx.core.AbstractVerticle
import io.vertx.core.Promise
import io.vertx.core.json.Json
import io.vertx.ext.web.Router
import io.vertx.ext.web.RoutingContext

class MainVerticle : AbstractVerticle() {

  override fun start(startPromise: Promise<Void>) {
    val mainRouter = Router.router(vertx)
    // External router
    val todoRouter = TodoRouter(vertx)
    mainRouter.mountSubRouter("/todos",todoRouter.router)

    vertx
      .createHttpServer()
      .requestHandler(mainRouter)
      .listen(8888) { http ->
        if (http.succeeded()) {
          startPromise.complete()
          println("HTTP server started on port 8888")
        } else {
          startPromise.fail(http.cause());
        }
      }

     fun healthCheckHandler(ctx: RoutingContext){

       val map = mapOf("data" to "Todo Api its working well")
       val response = Json.encodePrettily(map)
      ctx.response()
        .putHeader("content-type","application/json")
        .setStatusCode(200)
        .end(response)
    }
    mainRouter.get("/health").handler { healthCheckHandler(it) }
  }
}
