package com.example.api

import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface APIServer {
    @GET("tasks")
    suspend fun getTasks(): ApiResponse<List<Tasks>>

    @POST("tasks")
    suspend fun addTask(@Body task: Tasks): ApiResponse<Tasks>
}