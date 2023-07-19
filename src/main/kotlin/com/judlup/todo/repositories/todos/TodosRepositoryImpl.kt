package com.judlup.todo.repositories.todos

import com.judlup.todo.config.database.DatabaseClient
import com.judlup.todo.interfaces.todos.repositories.TodosRepository
import com.judlup.todo.models.todos.Todo
import io.vertx.core.Future
import io.vertx.core.Promise
import io.vertx.core.Vertx
import io.vertx.sqlclient.Tuple
import java.util.*

class TodosRepositoryImpl: TodosRepository {
  private val databaseClient = DatabaseClient(Vertx.vertx())
  private val client = databaseClient.client

  val todos: MutableList<Todo> = mutableListOf()
  override fun get(): Future<List<Todo>?> {
    val promise: Promise<List<Todo>> = Promise.promise()

    client.preparedQuery("SELECT * FROM todos WHERE status = true").execute { ar ->
      if(ar.succeeded()) {
        val rows = ar.result()
        val todos = mutableListOf<Todo>()
        for(row in rows){
          todos.add(Todo(
            row.getUUID("id"),
            row.getString("title"),
            row.getBoolean("completed"),
            row.getLong("created_at"),
            row.getBoolean("status")
          ))
        }
        promise.complete(todos)
      }else {
        promise.fail(ar.cause())
      }
    }
    return promise.future()
    }

  override fun getById(id: String): Future<Todo> {
    val promise: Promise<Todo> = Promise.promise()
    client.preparedQuery("SELECT * FROM todos WHERE id = $1 AND status = true")
      .execute(Tuple.of(id)) { ar ->
        if (ar.succeeded()) {
          val resultSet = ar.result()
          if(resultSet.size() > 0){
            val row = resultSet.iterator().next()
            val todo = Todo(
              id = row.getUUID("id"),
              title = row.getString("title"),
              completed = row.getBoolean("completed"),
              createdAt = row.getLong("created_at"),
              status = row.getBoolean("status")
            )
            promise.complete(todo)
          }else{
            promise.fail("No rows were founded")
          }
        } else {
          promise.fail(ar.cause())
        }
      }
    return promise.future()
  }

  override fun create(todo: Todo): Future<Todo> {
    val promise: Promise<Todo> = Promise.promise()

    client.preparedQuery(
      "INSERT INTO todos(id, title, completed, created_at, status) VALUES($1, $2, $3, $4, $5) RETURNING *"
    )
      .execute(Tuple.of(todo.id, todo.title, todo.completed, todo.createdAt, todo.status)) { ar ->
        if(ar.succeeded()){
          val resultSet = ar.result()
          if(resultSet.size() > 0){
            val row = resultSet.iterator().next()
            val createdTodo = Todo(
              id = row.getUUID("id"),
              title = row.getString("title"),
              completed = row.getBoolean("completed"),
              createdAt = row.getLong("created_at"),
              status = row.getBoolean("status")
            )
            promise.complete(createdTodo)
          } else {
            promise.fail("No rows were inserted.")
          }
        } else{
          promise.fail(ar.cause())
        }
      }
    return promise.future()
}

  override fun update(id: String, todo: Todo): Future<Boolean> {
    val promise: Promise<Boolean> = Promise.promise()


    client.preparedQuery(
      "UPDATE todos SET title = $1, completed = $2 WHERE id = $3 AND status = true"
    )
      .execute(Tuple.of(todo.title, todo.completed, todo.id)) { ar ->
        if (ar.succeeded()) {
          promise.complete(true)
        } else {
          promise.complete(false)
        }
      }
    return promise.future()
  }

  override fun delete(id: String): Future<Boolean> {
    val promise: Promise<Boolean> = Promise.promise()

    client.preparedQuery(
      "UPDATE todos SET status = false WHERE id = $1"
    ).execute(Tuple.of(id)) { ar ->
      if(ar.succeeded()){
        promise.complete(true)
      }else{
        promise.fail(ar.cause())
      }
    }
    return promise.future()
  }
}
