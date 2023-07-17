package com.judlup.todo.config.database

import io.vertx.core.Vertx
import io.vertx.pgclient.PgConnectOptions
import io.vertx.pgclient.PgPool
import io.vertx.sqlclient.PoolOptions

val connectOption: PgConnectOptions = PgConnectOptions()
  .setPort(5432)
  .setHost("localhost")
  .setDatabase("todos")
  .setUser("postgres")
  .setPassword("123456")

val poolOption: PoolOptions = PoolOptions().setMaxSize(5)

val client = PgPool.pool(Vertx.vertx(), connectOption, poolOption)
