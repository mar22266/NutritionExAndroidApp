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
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.myapplication.ui.theme.MyApplicationTheme
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*

@OptIn(ExperimentalMaterial3Api::class)
class IMCActivity : ComponentActivity() {
    private var peso by mutableStateOf("")
    private var altura by mutableStateOf("")
    private var imc by mutableStateOf(0f)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        fetchUserDataAndUpdateFields()

        setContent {
            IMCScreen()
        }
    }

    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    fun IMCScreen() {
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
                        title = "CALCULADORA IMC",
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

                    // Campo para ingresar el peso
                    OutlinedTextField(
                        value = peso,
                        onValueChange = { peso = it },
                        label = { Text("Peso (kg)") },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = 8.dp)
                    )

                    // Campo para ingresar la altura
                    OutlinedTextField(
                        value = altura,
                        onValueChange = { altura = it },
                        label = { Text("Altura (m)") },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = 16.dp)
                    )

                    // Botón para calcular IMC
                    Button(
                        onClick = {
                            val pesoValue = peso.toFloatOrNull() ?: 0f
                            val alturaValue = altura.toFloatOrNull() ?: 0f
                            if (alturaValue != 0f) {
                                imc = pesoValue / (alturaValue * alturaValue)

                                // Almacenar datos en Firebase Realtime Database
                                val user = FirebaseAuth.getInstance().currentUser
                                user?.let {
                                    val userId = user.uid
                                    val database =
                                        FirebaseDatabase.getInstance().getReference("users/$userId")
                                    val userData = hashMapOf(
                                        "peso" to peso,
                                        "altura" to altura,
                                        "imc" to imc.toString()
                                    )

                                    database.setValue(userData).addOnSuccessListener {
                                        // Datos almacenados con éxito
                                    }.addOnFailureListener {
                                        // Error al almacenar datos
                                    }
                                }
                            }
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = 16.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color(0xFF19A89A),
                            contentColor = Color.White
                        )

                    ) {
                        Text(text = "Calcular IMC")
                    }

                    // Mostrar IMC calculado
                    Text(
                        text = "Indice de masa corporal: $imc",
                        style = MaterialTheme.typography.headlineMedium
                    )

                    Button(
                        onClick = {
                            // Crear un Intent para iniciar IMCInfoActivity
                            val intent = Intent(context, IMCInfoActivity::class.java)
                            // Iniciar la nueva actividad
                            context.startActivity(intent)
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 16.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color(0xFF19A89A),
                            contentColor = Color.White
                        )
                    ) {
                        Text(text = "Información sobre IMC")
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
    private fun fetchUserDataAndUpdateFields() {
        val user = FirebaseAuth.getInstance().currentUser
        user?.let {
            val userId = user.uid
            val database = FirebaseDatabase.getInstance().getReference("users/$userId")

            database.addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(dataSnapshot: DataSnapshot) {
                    val pesoValue = dataSnapshot.child("peso").value?.toString() ?: "0"
                    val alturaValue = dataSnapshot.child("altura").value?.toString() ?: "0"
                    val imcValue = dataSnapshot.child("imc").value?.toString()?.toFloatOrNull() ?: 0f

                    // Actualizar los campos con los valores recuperados
                    peso = pesoValue
                    altura = alturaValue
                    imc = imcValue
                }

                override fun onCancelled(databaseError: DatabaseError) {
                    // Error al recuperar datos
                }
            })
        }
    }

    @Preview
    @Composable
    fun IMCScreenPreview() {
        MyApplicationTheme {
            IMCScreen()
        }
    }
}




