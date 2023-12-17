package com.dnx.japan.sharedplatform.data.token.api

import kotlinx.coroutines.runBlocking
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET

class TokenApi {

    suspend fun getList(){
        val users = fetchUsers()
        if (users.isEmpty()) {
            // Handle empty list case
        } else {
            // Update UI with list of users
        }
    }
    private val myService: MyService by lazy {
        createMyService()
    }

    suspend fun fetchUsers(): List<String> {
        return try {
            myService.getList()// Suspend function, waits for response
        } catch (e: Exception) {
            // Handle network errors
            emptyList()
        }
    }
}


interface MyService {
    @GET("/api/v1/users")
    suspend fun getList(): List<String>
}


fun createMyService(): MyService {
    val retrofit = Retrofit.Builder()
        .baseUrl("https://example.com")
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    return retrofit.create(MyService::class.java)
}