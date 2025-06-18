package com.example.foodapp.model

data class MealDetailResponse(
    val meals: List<MealDetail>
)

data class MealDetail(
    val idMeal: String,
    val strMeal: String,
    val strMealThumb: String,
    val strInstructions: String
)
