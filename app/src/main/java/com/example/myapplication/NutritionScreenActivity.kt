package com.example.myapplication

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SmallTopAppBar
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.material3.ExperimentalMaterial3Api
import com.example.myapplication.ui.theme.MyApplicationTheme

class NutritionScreenActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            NutritionScreen()
        }
    }

    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    fun NutritionScreen() {
        val context = LocalContext.current
        val imageResId = R.drawable.logofin
        val appBarColor = Color(0xFF0A3D38)
        val colorDeFondo = Color(0xFFF4F4F4)



        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(colorDeFondo)
        ) {
            Scaffold(

                topBar = {
                    CustomTopAppBar(
                        title = "NUTRICION",
                        backgroundColor = appBarColor,
                        onBack = {
                            val intent = Intent(context, HomeScreenActivity::class.java)
                            intent.flags =
                                Intent.FLAG_ACTIVITY_CLEAR_TOP // Esto asegura no acumular actividades
                            context.startActivity(intent)
                        }
                    )
                },
                bottomBar = {
                    CompositionLocalProvider(
                        LocalContentColor provides Color.White
                    ) {
                        Surface(color = appBarColor) {
                            BottomAppBar {
                                // Contenido del BottomAppBar
                            }
                        }
                    }
                }
            ) { innerPadding ->
                Column(
                    modifier = Modifier
                        .padding(innerPadding) // Aquí aplicamos el innerPadding
                        .padding(16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {


                    Image(
                        painter = painterResource(id = imageResId),
                        contentDescription = null,
                        modifier = Modifier
                            .size(200.dp)
                            .padding(8.dp).padding(bottom = 50.dp)
                    )

                    // Botón para Calculador de IMC
                    Button(
                        onClick = {
                            // Crear un Intent para iniciar la nueva actividad
                            val intent = Intent(context, IMCActivity::class.java)

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
                        Text(text = "Calculador de IMC")
                    }

                    // Botón para Valores nutricionales
                    Button(
                        onClick = {
                            val intent = Intent(context, FoodSearchActivity::class.java)
                            startActivity(intent)
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
                        Text(text = "Valores nutricionales")
                    }
                }
            }
        }
    }

    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    fun CustomTopAppBar(title: String, backgroundColor: Color, onBack: () -> Unit) {
        SmallTopAppBar(
            title = {
                Box(
                    modifier = Modifier.fillMaxWidth(),
                    contentAlignment = Alignment.Center
                ) {
                    Text(text = title, color = Color.White) // Establece el color del texto directamente
                }
            },
            navigationIcon = {
                IconButton(onClick = onBack) {
                    Icon(
                        imageVector = Icons.Filled.ArrowBack,
                        contentDescription = "Back",
                        tint = Color.White // Establece el color del ícono directamente
                    )
                }
            },
            colors = TopAppBarDefaults.smallTopAppBarColors(containerColor = backgroundColor)
        )
    }
    @Preview
    @Composable
    fun NutritionScreenPreview() {
        MyApplicationTheme {
            NutritionScreen()
        }
    }
}
