package com.example.myapplication

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.myapplication.ui.theme.MyApplicationTheme
import android.content.Intent
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.myapplication.ui.theme.MyApplicationTheme
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*


class IMCInfoActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MyApplicationTheme {
                IMCInfoScreen()
            }
        }
    }

    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    fun IMCInfoScreen() {
        val context = LocalContext.current

        val backgroundColor = colorResource(id = R.color.backgroundcolorviews)
        val secundaryColor = colorResource(id = R.color.secundary)
        val principalColor = colorResource(id = R.color.principal)
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
                        title = "INFORMACION IMC",
                        backgroundColor = appBarColor,
                        onBack = {
                            val intent = Intent(context, NutritionScreenActivity::class.java)
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
                            .padding(8.dp)

                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    val imcData = listOf(
                        Triple(
                            "Menos de 18.5",
                            "Bajo peso",
                            "Sería recomendable acudir a un nutricionista."
                        ),
                        Triple("18.5 - 24.9", "Normal", "Mantén una dieta equilibrada."),
                        Triple("25 - 29.9", "Sobrepeso", "Considera hacer más ejercicio."),
                        Triple("30 o más", "Obesidad", "Consulta a un especialista.")
                    )

                    imcData.forEach { data ->
                        Text(text = data.first, color = secundaryColor) // Set the color of the text
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            text = "${data.second}, ${data.third}",
                            color = principalColor
                        ) // Set the color of the text
                        Spacer(modifier = Modifier.height(16.dp))
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
    fun IMCInfoScreenPreview() {
        MyApplicationTheme {
            IMCInfoScreen()
        }
    }
}



