package com.example.myapplication

import com.example.myapplication.modelo.ApiResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface FoodApiService {
    @GET("api/v2/search")
    suspend fun searchFoods(
        @Query("search_terms") query: String,
        @Query("json") json: Boolean = true,
        @Query("fields") fields: String = "product_name,nutriments,nutriscore_data,nutrition_grades",
        @Query("page") page: Int = 1, // Agregar parámetro de página
        @Query("page_size") pageSize: Int = 50 // Agregar tamaño de página
    ): ApiResponse
}

