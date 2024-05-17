package com.example.myapplication

import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.annotation.DrawableRes
import androidx.compose.ui.platform.LocalContext
import com.example.myapplication.ui.theme.MyApplicationTheme
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.ui.graphics.Color
import androidx.compose.foundation.background



// Constantes para identificar las acciones y permisos
private const val REQUEST_IMAGE_CAPTURE = 1
private const val REQUEST_IMAGE_PICK = 2
private const val PERMISSION_REQUEST_CODE = 3

@OptIn(ExperimentalMaterial3Api::class)
class PerfilView : ComponentActivity() {
    private var correo by mutableStateOf("")
    private var peso by mutableStateOf("")
    private var altura by mutableStateOf("")
    private var imc by mutableStateOf(0f)
    private var profileImageUrl by mutableStateOf("")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        fetchUserData()

        // Asegúrate de que estos launchers coincidan con los tipos esperados en PerfilScreen
        val takePictureLauncher = registerForActivityResult(ActivityResultContracts.TakePicture()) { success ->
            if (success) {
                // La foto fue tomada con éxito, manejar la foto aquí
            }
        }

        val pickImageLauncher = registerForActivityResult(ActivityResultContracts.GetContent()) { uri ->
            // Manejar la URI de la imagen seleccionada
        }

        setContent {
            MyApplicationTheme {
                PerfilScreen()
            }
        }
    }

    @Composable
    fun PerfilScreen() {
        var showDialog by remember { mutableStateOf(false) }
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
                        title = "Perfil del Usuario",
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
                        .fillMaxSize()
                        .padding(innerPadding),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {

                    if (profileImageUrl.isNotEmpty()) {
                        // TODO: Cargar imagen desde URL
                    } else {
                        Image(
                            painter = painterResource(id = imageResId),
                            contentDescription = "Foto de perfil",
                            modifier = Modifier
                                .size(150.dp)
                                .padding(8.dp)
                        )
                    }
                    /*
                    if (showDialog) {
                        ChangeProfilePicOptionsDialog(
                            onDismiss = { showDialog = false },
                            onTakePicture = { takePictureLauncher.launch(null) },
                            onPickImage = { pickImageLauncher.launch("image/*") }
                        )
                    }

    */
                    */

                    Button(
                        onClick = { showDialog = true },
                        modifier = Modifier.padding(top = 10.dp, bottom = 10.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color(0xFF19A89A),
                            contentColor = Color.White // Cambia el color del texto si es necesario
                        )
                    ) {
                        Text("Cambiar Foto de Perfil")
                    }


                    // Parte superior: Imagen y correo
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        // Imagen de perfil basada en IMC
                        Image(
                            painter = painterResource(id = getProfileImageBasedOnIMC(imc)),
                            contentDescription = "Foto de perfil",
                            modifier = Modifier.size(200.dp)
                        )

                        // Correo del usuario
                        Text(
                            text = correo,
                            style = MaterialTheme.typography.headlineMedium
                        )
                    }

                    Spacer(modifier = Modifier.height(16.dp))
                    // Datos del usuario
                    Text(text = "Peso: $peso kg", style = MaterialTheme.typography.headlineMedium)
                    Text(
                        text = "Altura: $altura m",
                        style = MaterialTheme.typography.headlineMedium
                    )
                    Text(text = "IMC: $imc", style = MaterialTheme.typography.headlineMedium)

                    // Mensaje basado en el IMC
                    when {
                        imc < 18f -> Text(
                            text = "Te encuentras en: Bajo peso",
                            style = MaterialTheme.typography.headlineMedium
                        )

                        imc in 18f..24.9f -> Text(
                            text = "Te encuentras en: Peso normal",
                            style = MaterialTheme.typography.headlineMedium
                        )

                        imc in 25f..29.9f -> Text(
                            text = "Te encuentras en: Sobrepeso",
                            style = MaterialTheme.typography.headlineMedium
                        )

                        imc >= 30f -> Text(
                            text = "Te encuentras en: Obesidad",
                            style = MaterialTheme.typography.headlineMedium
                        )

                        else -> Text(
                            text = "Valor de IMC no válido",
                            style = MaterialTheme.typography.headlineMedium
                        )

                    }
                }
            }
        }
    }

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


    // Función para mostrar opciones
    @Composable
    fun ChangeProfilePicOptionsDialog(
        onDismiss: () -> Unit,
        onTakePicture: () -> Unit,
        onPickImage: () -> Unit
    ) {
        AlertDialog(
            onDismissRequest = onDismiss,
            title = { Text(text = "Cambiar foto de perfil") },
            text = {
                Column {
                    TextButton(onClick = {
                        onTakePicture() // Llama al launcher para tomar foto
                        onDismiss()     // Cierra el diálogo después de seleccionar la opción
                    }) {
                        Text("Tomar foto")
                    }
                    TextButton(onClick = {
                        onPickImage()   // Llama al launcher para elegir de galería
                        onDismiss()     // Cierra el diálogo después de seleccionar la opción
                    }) {
                        Text("Elegir de galería")
                    }
                }
            },
            confirmButton = {
                Button(onClick = onDismiss) {
                    Text("Cancelar")
                }
            }
        )
    }


    // Función para obtener la imagen de perfil basada en el IMC
    @DrawableRes
    fun getProfileImageBasedOnIMC(imc: Float): Int {
        return when {
            imc < 18f -> R.drawable.imagen_delgada
            imc >= 30f -> R.drawable.imagen_obesa
            else -> R.drawable.imagen_normal
        }
    }

    private fun fetchUserData() {
        val user = FirebaseAuth.getInstance().currentUser
        user?.let {
            val userId = user.uid
            val database = FirebaseDatabase.getInstance().getReference("users/$userId")

            database.addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(dataSnapshot: DataSnapshot) {
                    correo = user.email ?: "No disponible"
                    peso = dataSnapshot.child("peso").value?.toString() ?: "0"
                    altura = dataSnapshot.child("altura").value?.toString() ?: "0"
                    imc = dataSnapshot.child("imc").value?.toString()?.toFloatOrNull() ?: 0f
                }

                override fun onCancelled(databaseError: DatabaseError) {
                    // Error al recuperar datos
                }
            })
        }
    }




    @Preview
    @Composable
    fun PerfilScreenPreview() {
        MyApplicationTheme {
            PerfilScreen()
        }
    }
}

