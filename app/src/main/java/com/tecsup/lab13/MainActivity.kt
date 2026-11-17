package com.tecsup.lab13

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.tecsup.lab13.screens.*
import com.tecsup.lab13.ui.theme.Lab13_AnimacionesTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Lab13_AnimacionesTheme {
                Lab13App()
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Lab13App() {
    var pantallaActual by remember { mutableIntStateOf(1) }
    val opciones = listOf("Ej 1", "Ej 2", "Ej 3", "Ej 4", "Juego", "Codelab")

    Scaffold(
        topBar = {
            Column {
                CenterAlignedTopAppBar(
                    title = { Text("Lab 13: Animaciones", fontWeight = FontWeight.Bold) },
                    colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                        containerColor = MaterialTheme.colorScheme.primaryContainer
                    )
                )
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .horizontalScroll(rememberScrollState())
                        .padding(horizontal = 8.dp, vertical = 4.dp),
                    horizontalArrangement = Arrangement.spacedBy(6.dp)
                ) {
                    opciones.forEachIndexed { index, titulo ->
                        val num = index + 1
                        FilterChip(
                            selected = pantallaActual == num,
                            onClick = { pantallaActual = num },
                            label = { Text(titulo) }
                        )
                    }
                }
            }
        }
    ) { innerPadding ->
        Box(modifier = Modifier.padding(innerPadding).fillMaxSize()) {
            when (pantallaActual) {
                1 -> Ejercicio1Screen()
                2 -> Ejercicio2Screen()
                3 -> Ejercicio3Screen()
                4 -> Ejercicio4Screen()
                else -> {
                    Box(modifier = Modifier.fillMaxSize().padding(24.dp)) {
                        Text("Falta implementar Juego Final y Codelab...")
                    }
                }
            }
        }
    }
}