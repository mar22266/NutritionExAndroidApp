package com.example.myapplication

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.myapplication.ui.theme.MyApplicationTheme


class HomeScreenActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            HomeScreen()
            }
        }

    @Composable
    fun HomeScreen() {
        val context = LocalContext.current
        val imageResId = R.drawable.logofin

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            // Mostrar la imagen del logo
            Image(
                painter = painterResource(id = imageResId),
                contentDescription = null,
                modifier = Modifier
                    .size(200.dp)
                    .padding(8.dp)
            )
            // Texto que pregunta "¿Qué necesitas hoy?" con estilo de tipografía de MaterialTheme
            Text(
                text = "¿Qué necesitas hoy?",
                style = MaterialTheme.typography.headlineMedium,
                modifier = Modifier.padding(bottom = 16.dp)
            )

            // Botones para diferentes opciones
            Button(
                onClick = {
                    // Crear un Intent para iniciar la nueva actividad
                    val intent = Intent(context, NutritionScreenActivity::class.java)

                    // Iniciar la nueva actividad
                    context.startActivity(intent)
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(100.dp)
                    .padding(bottom = 8.dp),

                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFF19A89A),
                    contentColor = Color.White
                )

            ) {
                Text(text = "Nutricion")
            }

            Button(
                onClick = { /* Lógica para chequeo emocional */ },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(100.dp)
                    .padding(bottom = 8.dp),

                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFF19A89A),
                    contentColor = Color.White
                )
            ) {
                Text(text = "Chequeo Emocional")
            }

            Button(
                onClick = {
                    // Crear un Intent para iniciar la nueva actividad
                    val intent = Intent(context, SleepTrackerActivity::class.java)

                    // Iniciar la nueva actividad
                    context.startActivity(intent)
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(100.dp)
                    .padding(bottom = 8.dp),

                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFF19A89A),
                    contentColor = Color.White
                )
            ) {
                Text(text = "Monitoreo de sueño")
            }

            Button(
                onClick = {
                    // Crear un Intent para iniciar la nueva actividad
                    val intent = Intent(context, RegistroActividadActivity::class.java)

                    // Iniciar la nueva actividad
                    context.startActivity(intent)
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(100.dp)
                    .padding(bottom = 8.dp),

                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFF19A89A),
                    contentColor = Color.White
                )
            ) {
                Text(text = "Registro de Actividad")
            }

            Button(
                onClick = {
                    // Crear un Intent para iniciar la nueva actividad
                    val intent = Intent(context, PerfilView::class.java)

                    // Iniciar la nueva actividad
                    context.startActivity(intent)
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(100.dp)
                    .padding(bottom = 8.dp),

                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFF19A89A),
                    contentColor = Color.White
                )
            ) {
                Text(text = "Ver Perfil")
            }
        }
    }

    @Preview
    @Composable
    fun HomeScreenPreview() {
        MyApplicationTheme {
            Surface {
                // Vista previa de HomeScreen
                HomeScreen()
            }
        }
    }
    }
