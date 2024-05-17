package com.example.myapplication

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

class RegistroActividadActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MaterialTheme {
                RegistroActividadScreen()
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RegistroActividadScreen() {
    var edad by remember { mutableStateOf(0) }
    var peso by remember { mutableStateOf(0) }
    var altura by remember { mutableStateOf(0) }
    var isMasculino by remember { mutableStateOf(true) }
    var actividadSeleccionada by remember { mutableStateOf(0) }
    var resultado by remember { mutableStateOf(0.0) }
    var weightInKg by remember { mutableStateOf(true) }
    var heightInCm by remember { mutableStateOf(true) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        TextField(
            value = edad.toString(),
            onValueChange = { edad = it.toIntOrNull() ?: 0 },
            label = { Text("Edad") },
            keyboardOptions = KeyboardOptions.Default.copy(
                keyboardType = KeyboardType.Number
            ),
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
        )

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            TextField(
                value = peso.toString(),
                onValueChange = { peso = it.toIntOrNull() ?: 0 },
                label = { Text("Peso") },
                keyboardOptions = KeyboardOptions.Default.copy(
                    keyboardType = KeyboardType.Number
                ),
                modifier = Modifier.weight(1f)
            )

            Spacer(modifier = Modifier.width(8.dp))

            Switch(
                checked = weightInKg,
                onCheckedChange = { weightInKg = it },
                modifier = Modifier
                    .padding(start = 8.dp)
                    .width(60.dp)
            )
            Text(text = if (weightInKg) "kg" else "lb", modifier = Modifier.padding(start = 4.dp))
        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            TextField(
                value = altura.toString(),
                onValueChange = { altura = it.toIntOrNull() ?: 0 },
                label = { Text("Altura") },
                keyboardOptions = KeyboardOptions.Default.copy(
                    keyboardType = KeyboardType.Number
                ),
                modifier = Modifier.weight(1f)
            )

            Spacer(modifier = Modifier.width(8.dp))

            Switch(
                checked = heightInCm,
                onCheckedChange = { heightInCm = it },
                modifier = Modifier
                    .padding(start = 8.dp)
                    .width(60.dp)
            )
            Text(text = if (heightInCm) "cm" else "m", modifier = Modifier.padding(start = 4.dp))
        }

        // Checkbox to select gender
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Checkbox(
                checked = isMasculino,
                onCheckedChange = { isMasculino = it },
                modifier = Modifier.padding(end = 8.dp)
            )
            Text(text = "Masculino")

            Checkbox(
                checked = !isMasculino,
                onCheckedChange = { isMasculino = !it },
                modifier = Modifier.padding(start = 16.dp)
            )
            Text(text = "Femenino")
        }

        val actividades = listOf("Actividad ligera", "Actividad moderada", "Actividad intensa")

        // Column to display and handle the selection of radio buttons
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
        ) {
            actividades.forEachIndexed { index, actividad ->
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(4.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    RadioButton(
                        selected = (actividadSeleccionada == index),
                        onClick = {
                            actividadSeleccionada = index
                        }
                    )
                    Text(
                        text = actividad,
                        modifier = Modifier.padding(start = 8.dp)
                    )
                }
            }
        }

        // Button to calculate
        Button(
            onClick = {
                resultado = calcularResultado(edad, peso, altura, isMasculino, actividadSeleccionada, weightInKg, heightInCm)
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
        ) {
            Text(text = "Calcular")
        }

        // Display the result
        Text(
            text = "Tasa Metabólica Basal (TMB): $resultado kcal/día",
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
        )
    }
}

fun calcularResultado(edad: Int, peso: Int, altura: Int, isMasculino: Boolean, actividad: Int, weightInKg: Boolean, heightInCm: Boolean): Double {
    // Ecuación de Harris-Benedict para calcular la Tasa Metabólica Basal (TMB)
    val weight = if (weightInKg) peso.toDouble() else peso.toDouble() * 0.453592 // Convertir lb a kg
    val height = if (heightInCm) altura.toDouble() else altura.toDouble() * 0.01 // Convertir cm a m

    val tmb: Double = if (isMasculino) {
        88.362 + (13.397 * weight) + (4.799 * height) - (5.677 * edad)
    } else {
        447.593 + (9.247 * weight) + (3.098 * height) - (4.330 * edad)
    }

    // Aplicar el factor de actividad
    val factorActividad = when (actividad) {
        0 -> 1.2 // Sedentario
        1 -> 1.375 // Levemente activo
        2 -> 1.55 // Moderadamente activo
        3 -> 1.725 // Muy activo
        4 -> 1.9 // Extremadamente activo
        else -> 1.0
    }

    return tmb * factorActividad
}

@Preview(showBackground = true)
@Composable
fun PreviewRegistroActividadScreen() {
    RegistroActividadScreen()
}