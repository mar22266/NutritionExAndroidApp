package com.example.myapplication

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplication.modelo.FoodProduct
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


class FoodViewModel : ViewModel() {
    val foodItems = MutableLiveData<List<FoodProduct>>()
    val searchQuery = MutableLiveData("")


    companion object {
        private val retrofit = Retrofit.Builder()
            .baseUrl("https://world.openfoodfacts.org/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val apiService: FoodApiService = retrofit.create(FoodApiService::class.java)
    }

    fun searchFood() {
        viewModelScope.launch {
            try {
                Log.d("FoodViewModel", "Search initiated for query: ${searchQuery.value}")
                val query = searchQuery.value?.trim()?.toLowerCase() ?: return@launch
                val response = apiService.searchFoods(query)
                Log.d("FoodViewModel", "API response received")
                if (response.count > 0) {
                    val matchedProducts = response.products.filter {
                        it.product_name?.toLowerCase()?.contains(query) == true
                    }
                    Log.d("FoodViewModel", "Filtered products found: ${matchedProducts.size}")
                    if (matchedProducts.isNotEmpty()) {
                        foodItems.postValue(matchedProducts)
                    } else {

                        Log.d("FoodViewModel", "No exact match found for query: $query")
                    }
                } else {
                    Log.d("FoodViewModel", "No products found for query: $query")
                }
            } catch (e: Exception) {
                Log.e("FoodViewModel", "Error during API call: ${e.message}")
            }
        }
    }

}