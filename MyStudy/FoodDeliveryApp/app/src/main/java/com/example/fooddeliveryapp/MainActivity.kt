package com.example.foodapp

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.example.foodapp.R

class MainActivity : AppCompatActivity() {

    private lateinit var btnFoodType1: Button
    private lateinit var btnFoodType2: Button
    private lateinit var btnFoodType3: Button
    private lateinit var btnAbout: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        btnFoodType1 = findViewById(R.id.btn_food_type_1)
        btnFoodType2 = findViewById(R.id.btn_food_type_2)
        btnFoodType3 = findViewById(R.id.btn_food_type_3)
        btnAbout = findViewById(R.id.btn_about)

        btnFoodType1.setOnClickListener {
            val intent = Intent(this, FoodTypeActivity::class.java)
            intent.putExtra("food_type", "Seafood")
            startActivity(intent)
        }
        btnFoodType2.setOnClickListener {
            val intent = Intent(this, FoodTypeActivity::class.java)
            intent.putExtra("food_type", "Beef")
            startActivity(intent)
        }
        btnFoodType3.setOnClickListener {
            val intent = Intent(this, FoodTypeActivity::class.java)
            intent.putExtra("food_type", "Pasta")
            startActivity(intent)
        }

        btnAbout.setOnClickListener {
            startActivity(Intent(this, AboutActivity::class.java))
        }
    }
}
