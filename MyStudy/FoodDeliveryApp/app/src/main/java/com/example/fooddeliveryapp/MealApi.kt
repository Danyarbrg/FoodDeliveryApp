package com.example.foodapp.network

import com.example.foodapp.model.MealDetailResponse
import com.example.foodapp.model.MealListResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface MealApi {

    // Получить список блюд по категории
    @GET("filter.php")
    suspend fun getMealsByCategory(@Query("c") category: String): MealListResponse

    // Получить подробности о блюде по ID
    @GET("lookup.php")
    suspend fun getMealDetail(@Query("i") id: String): MealDetailResponse
}
