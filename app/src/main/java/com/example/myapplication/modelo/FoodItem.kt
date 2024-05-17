package com.example.myapplication.modelo

// Respuesta de la API
data class ApiResponse(
    val count: Int,
    val products: List<FoodProduct>,
    val page: Int,
    val page_count: Int,
    val page_size: Int
)

data class FoodProduct(
    val product_name: String?,
    val nutriments: Nutriments?,
    // Añade más campos según la API
)

data class Nutriments(
    val energy_kcal_100g: Double?,
    val fat_100g: Double?,
    val sugars_100g: Double?,
    val salt_100g: Double?,
    val carbohydrates_100g: Double?
    // Añade más nutrientes según necesites
)

