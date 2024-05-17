package com.example.myapplication

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

/*
interface RetrofitService {
    @GET("/v1/food/fdcld?format=full&nutrients=203")
    suspend fun alimentosrecomendados(){
        @Query("api_key") apiKey: String,
        //@Query("region") region: String

    }
    //codigo que ira en actividad donde se sugiere comidas
    /*val servicio = RetrofitServiceFactory.makeRetrofitService()
    lifecycleScope.launch{
        service.alimentosrecomendados()
    }
    * */

}
object RetrofitServiceFactory{
    fun makeRetrofitService(): RetrofitService{
        return Retrofit.Builder()
            .baseUrl("https://api.nal.usda.gov/fdc")
            .addConverterFactory(GsonConverterFactory.create())
            .build().create(RetrofitService::class.java)

    }
}
 */