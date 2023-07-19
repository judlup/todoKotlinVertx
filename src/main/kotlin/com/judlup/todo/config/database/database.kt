package com.judlup.todo.config.database

import io.vertx.core.Vertx
import io.vertx.pgclient.PgConnectOptions
import io.vertx.pgclient.PgPool
import io.vertx.sqlclient.PoolOptions

class DatabaseClient(vertx: Vertx){

  private val connectOptions: PgConnectOptions = PgConnectOptions()
        .setPort(5432)
        .setHost("localhost")
        .setDatabase("todos")
        .setUser("postgres")
        .setPassword("123456")

    private val poolOptions: PoolOptions = PoolOptions().setMaxSize(5)

    val client: PgPool = PgPool.pool(vertx, connectOptions, poolOptions)
}
