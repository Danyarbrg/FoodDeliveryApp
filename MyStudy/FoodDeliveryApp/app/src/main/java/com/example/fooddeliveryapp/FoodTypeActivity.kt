package com.example.foodapp

import android.os.Bundle
import android.view.MenuItem
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.foodapp.R
import android.widget.ImageView
import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.lifecycleScope
import com.bumptech.glide.Glide
import com.example.foodapp.network.MealApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


class FoodTypeActivity : AppCompatActivity() {

    private lateinit var tvName: TextView
    private lateinit var ivImage: ImageView
    private lateinit var tvDescription: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_food_type)

        // 1) Toolbar как ActionBar
        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        // 2) view binding через findViewById
        tvName = findViewById(R.id.tv_food_type)
        ivImage = findViewById(R.id.iv_food_image)
        tvDescription = findViewById(R.id.tv_description)

        // 3) Отображаем название
        val foodType = intent.getStringExtra("food_type") ?: ""
        tvName.text = foodType
        supportActionBar?.title = foodType

        // 4) Загружаем данные о блюде из TheMealDB
        lifecycleScope.launch {
            try {
                val retrofit = Retrofit.Builder()
                    .baseUrl("https://www.themealdb.com/api/json/v1/1/")
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()

                val api = retrofit.create(MealApi::class.java)

                // 1. Получаем блюда по категории
                val mealsResponse = withContext(Dispatchers.IO) {
                    api.getMealsByCategory(foodType)
                }

                val firstMeal = mealsResponse.meals.firstOrNull()
                if (firstMeal != null) {
                    // 2. Получаем подробности блюда
                    val mealDetail = withContext(Dispatchers.IO) {
                        api.getMealDetail(firstMeal.idMeal)
                    }.meals.firstOrNull()

                    // 3. Обновляем UI
                    if (mealDetail != null) {
                        Glide.with(this@FoodTypeActivity)
                            .load(mealDetail.strMealThumb)
                            .into(ivImage)

                        tvDescription.text = mealDetail.strInstructions
                        tvName.text = mealDetail.strMeal
                    } else {
                        tvDescription.text = "Описание блюда не найдено"
                    }
                } else {
                    tvDescription.text = "Блюда по этой категории не найдены"
                }

            } catch (e: Exception) {
                tvDescription.text = "Ошибка загрузки: ${e.localizedMessage}"
            }
        }
    }


        // Обработка клика по стрелке «назад»
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            finish()
            return true
        }
        return super.onOptionsItemSelected(item)
    }
}
