package com.example.myapplication

import androidx.activity.ComponentActivity
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.myapplication.modelo.FoodProduct

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FoodSearchScreen() {
    val viewModel: FoodViewModel = viewModel(LocalContext.current as ComponentActivity)
    val searchQuery by viewModel.searchQuery.observeAsState("")
    val foodItems by viewModel.foodItems.observeAsState(emptyList())
    Box(modifier = Modifier
        .fillMaxSize()
        .background(Color.White)) {
        Column {
            Row(modifier = Modifier.fillMaxWidth()) {
                TextField(
                    value = searchQuery,
                    onValueChange = { viewModel.searchQuery.value = it },
                    label = { Text("Buscar comida") },
                    modifier = Modifier.weight(1f)
                )
                Button(onClick = { viewModel.searchFood() }) {
                    Text("Buscar")
                }
            }
            LazyColumn {
                items(foodItems) { foodProduct ->
                    Card(
                        modifier = Modifier
                            .padding(8.dp)
                            .fillMaxWidth()
                            .height(200.dp), // Establece una altura uniforme
                    ) {
                        Box(
                            modifier = Modifier
                                .background(Color.Black) // Establece el color de fondo aquí
                                .padding(16.dp)
                                .fillMaxSize()
                        ) {
                            Column(modifier = Modifier.padding(16.dp)) {
                                Text(
                                    "Nombre: ${foodProduct.product_name ?: "No disponible"}",
                                    style = MaterialTheme.typography.bodySmall,
                                    color = Color.White
                                )
                                foodProduct.nutriments?.let { nutriments ->
                                    Text(
                                        "Calorías (por 100g): ${nutriments.energy_kcal_100g ?: "No disponible"} kcal",
                                        color = Color.White
                                    )
                                    Text(
                                        "Grasa (por 100g): ${nutriments.fat_100g ?: "No disponible"} g",
                                        color = Color.White
                                    )
                                    Text(
                                        "Carbohidratos (por 100g): ${nutriments.carbohydrates_100g ?: "No disponible"} g",
                                        color = Color.White
                                    )
                                    Text(
                                        "Azúcares (por 100g): ${nutriments.sugars_100g ?: "No disponible"} g",
                                        color = Color.White
                                    )
                                    Text(
                                        "Sales (por 100g) ${nutriments.salt_100g ?: "No disponible"} g",
                                        color = Color.White
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}