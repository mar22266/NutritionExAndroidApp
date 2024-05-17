package com.example.myapplication

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
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
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.myapplication.ui.theme.MyApplicationTheme

import androidx.compose.runtime.*


open class SleepTrackerViewModel : ViewModel() {
    // Mutable state for the sleep data input
    var sleepDataInput by mutableStateOf("")

    // MutableState list to store sleep records
    var sleepRecords by mutableStateOf(listOf<Float>())

    // Derived state to get the max sleep value
    val maxSleepValue: Float
        get() = sleepRecords.maxOrNull() ?: 0f

    fun saveSleepData() {
        val hours = sleepDataInput.toFloatOrNull()
        hours?.let {
            sleepRecords = sleepRecords + it
            sleepDataInput = ""
        }
        // TODO: Add validation or user feedback if input is invalid
    }
    val sleepRecommendation: String
        get() {
            val recentSleep = sleepRecords.lastOrNull() ?: return "No sleep data recorded yet."
            return when {
                recentSleep == null -> "No sleep data recorded yet. 🤔"
                recentSleep < 6 -> "Deberías considerar ajustar tus horarios de sueño. \uD83D\uDE34 "
                recentSleep in 6f..9f -> "¡Excelente! ¡Tu patrón de sueño es impresionante y saludable!\uD83D\uDE03"
                else -> "You're sleeping longer than usual. If you're always feeling sleepy, consider consulting a doctor.\uD83D\uDECC"
            }
        }
}


class SleepTrackerActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            SleepTracker()
        }
    }
    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    fun SleepTracker() {
        val viewModel: SleepTrackerViewModel = viewModel()
        BoxWithConstraints {
            if (maxWidth < 600.dp) {
                ColumnLayout(viewModel = viewModel)
            } else {
                RowLayout(viewModel = viewModel)
            }
        }
    }
    @Composable
    fun ColumnLayout(viewModel: SleepTrackerViewModel) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            SleepTrackerContent(viewModel = viewModel)
            // Logo at the bottom
            Logo()
        }
    }
    @Composable
    fun RowLayout(viewModel: SleepTrackerViewModel) {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            Column(
                modifier = Modifier
                    .weight(1f)
                    .padding(16.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                SleepTrackerContent(viewModel = viewModel)
            }
            // Logo at the end of the row
            Logo()
        }
    }
    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    fun SleepTrackerContent(viewModel: SleepTrackerViewModel) {

                Text(
                    text = "Registro de Sueño",
                    style = MaterialTheme.typography.headlineSmall,
                    modifier = Modifier.padding(vertical = 16.dp)
                )

                OutlinedTextField(
                    value = viewModel.sleepDataInput,
                    onValueChange = {
                        viewModel.sleepDataInput = it
                    },
                    label = { Text("Introduce horas de sueño") },
                    modifier = Modifier.fillMaxWidth()
                )

                Button(
                    onClick = { viewModel.saveSleepData() },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFF19A89A),
                        contentColor = Color.White
                    ),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 16.dp)
                ) {
                    Text(text = "Guardar Sueño")
                }

                // Sleep Summary
                Spacer(modifier = Modifier.height(16.dp))
                Text("Resumen de Sueño", style = MaterialTheme.typography.headlineSmall)
                Text("Último registro de Sueño: ${viewModel.sleepDataInput} hrs")

                // Sleep Chart
                Spacer(modifier = Modifier.height(16.dp))
                Text("Gráficos de Sueño", style = MaterialTheme.typography.headlineSmall)
                LineChart(records = viewModel.sleepRecords, maxValue = viewModel.maxSleepValue)

                //Sleep Recommendations
                Spacer(modifier = Modifier.height(16.dp))
                Text("Recomendaciones de Sueño", style = MaterialTheme.typography.headlineSmall)
                Text(viewModel.sleepRecommendation)


                // SleepRecommendations
                Spacer(modifier = Modifier.height(16.dp))
                Text("Recomendaciones de Sueño", style = MaterialTheme.typography.headlineSmall)
                Text(viewModel.sleepRecommendation)
            }

            // Logo at the bottom

        }





@OptIn(ExperimentalMaterial3Api::class)
@Preview
@Composable
fun SleepTrackerPreview() {
    MyApplicationTheme {
        // Mocked ViewModel
        val mockViewModel = @SuppressLint("StaticFieldLeak")
        object : SleepTrackerViewModel() {
            init {
                sleepDataInput = "8"
                sleepRecords = listOf(6f, 7.5f, 8f, 6.5f, 7f)
            }
        }
        // Deduce recommendation based on the latest sleep record
        val latestSleepRecord = mockViewModel.sleepRecords.lastOrNull()
        val recommendation = when {
            latestSleepRecord == null -> "No sleep data recorded yet. 🤔"
            latestSleepRecord < 6 -> "You should consider getting more sleep for better health. 😴"
            latestSleepRecord in 6f..9f -> "Great! You're maintaining a healthy sleep pattern. \uD83D\uDE03"
            else -> "You're sleeping longer than usual. If you're always feeling sleepy, consider consulting a doctor. 🛌"
        }

        // Mocked content based on the ViewModel
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Column(
                modifier = Modifier
                    .weight(1f)
                    .padding(16.dp),
                verticalArrangement = Arrangement.Top
            ) {
                Text(
                    text = "Registro de Sueño",
                    style = MaterialTheme.typography.headlineSmall,
                    modifier = Modifier.padding(vertical = 16.dp)
                )

                OutlinedTextField(
                    value = mockViewModel.sleepDataInput,
                    onValueChange = { },
                    label = { Text("Introduce horas de sueño") },
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(16.dp))
                Text("Resumen de Sueño", style = MaterialTheme.typography.headlineSmall)
                Text("Último registro de Sueño: ${mockViewModel.sleepDataInput} hrs")

                Spacer(modifier = Modifier.height(16.dp))
                Text("Gráficos de Sueño", style = MaterialTheme.typography.headlineSmall)
                LineChart(records = mockViewModel.sleepRecords, maxValue = mockViewModel.maxSleepValue)

                Spacer(modifier = Modifier.height(16.dp))
                Text("Recomendaciones de Sueño", style = MaterialTheme.typography.headlineSmall)
                Text(recommendation)

            }

            Image(
                painter = painterResource(id = R.drawable.logofin),
                contentDescription = null,
                modifier = Modifier
                    .size(50.dp)
                    .clickable { /* Handle logo click if needed */ }
            )
        }
    }
}

    @Composable
    fun LineChart(records: List<Float>, maxValue: Float) {
        val chartHeight = 200.dp
        val chartWidth = 300.dp

        Canvas(
            modifier = Modifier
                .size(chartWidth, chartHeight)
                .border(1.dp, Color.Gray),
            onDraw = {
                val pointWidth = size.width / (records.size + 1) // Divide the total width by the number of points
                var lastPoint: Offset? = null

                records.forEachIndexed { index, value ->
                    val x = index * pointWidth + pointWidth / 2
                    val y = size.height - (size.height * (value / maxValue))
                    val point = Offset(x, y)

                    if (lastPoint != null) {
                        drawLine(
                            color = Color.Blue,
                            start = lastPoint!!,
                            end = point,
                            strokeWidth = 2f
                        )
                    }
                    drawCircle(
                        color = Color.Red,
                        center = point,
                        radius = 4f
                    )

                    lastPoint = point
                }
            }
        )
    }

@Composable
fun Logo() {
    Image(
        painter = painterResource(id = R.drawable.logofin),
        contentDescription = null,
        modifier = Modifier
            .size(50.dp)
            .clickable { /* Handle logo click if needed */ }
    )
}