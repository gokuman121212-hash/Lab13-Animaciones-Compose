package com.tecsup.lab13.screens

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

enum class EstadoCarga {
    CARGANDO, CONTENIDO, ERROR
}

@Composable
fun Ejercicio4Screen() {
    var estadoActual by remember { mutableStateOf(EstadoCarga.CARGANDO) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "Ejercicio 4: AnimatedContent",
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold
        )
        Text(
            text = "Transición suave entre estados de interfaz",
            fontSize = 14.sp,
            color = Color.Gray
        )

        Spacer(modifier = Modifier.height(28.dp))

        AnimatedContent(
            targetState = estadoActual,
            transitionSpec = {
                fadeIn(animationSpec = tween(600)) togetherWith
                        fadeOut(animationSpec = tween(400))
            },
            label = "estadoContentAnim"
        ) { estado ->
            when (estado) {
                EstadoCarga.CARGANDO -> {
                    Card(
                        modifier = Modifier.fillMaxWidth().height(160.dp),
                        colors = CardDefaults.cardColors(containerColor = Color(0xFFE3F2FD))
                    ) {
                        Column(
                            modifier = Modifier.fillMaxSize(),
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.Center
                        ) {
                            CircularProgressIndicator()
                            Spacer(modifier = Modifier.height(12.dp))
                            Text("Cargando datos del servidor...", fontWeight = FontWeight.Medium)
                        }
                    }
                }
                EstadoCarga.CONTENIDO -> {
                    Card(
                        modifier = Modifier.fillMaxWidth().height(160.dp),
                        colors = CardDefaults.cardColors(containerColor = Color(0xFFE8F5E9))
                    ) {
                        Column(
                            modifier = Modifier.fillMaxSize().padding(16.dp),
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.Center
                        ) {
                            Text("¡Datos Cargados!", fontSize = 18.sp, fontWeight = FontWeight.Bold, color = Color(0xFF2E7D32))
                            Spacer(modifier = Modifier.height(8.dp))
                            Text("Usuario: Cristhian Flores | Estado: Activo")
                        }
                    }
                }
                EstadoCarga.ERROR -> {
                    Card(
                        modifier = Modifier.fillMaxWidth().height(160.dp),
                        colors = CardDefaults.cardColors(containerColor = Color(0xFFFFEBEE))
                    ) {
                        Column(
                            modifier = Modifier.fillMaxSize().padding(16.dp),
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.Center
                        ) {
                            Text("Error de Conexión", fontSize = 18.sp, fontWeight = FontWeight.Bold, color = Color(0xFFC62828))
                            Spacer(modifier = Modifier.height(8.dp))
                            Text("No se pudo conectar con el servicio.")
                        }
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(28.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Button(
                onClick = { estadoActual = EstadoCarga.CARGANDO },
                modifier = Modifier.weight(1f)
            ) { Text("Cargando", fontSize = 12.sp) }

            Button(
                onClick = { estadoActual = EstadoCarga.CONTENIDO },
                modifier = Modifier.weight(1f)
            ) { Text("Contenido", fontSize = 12.sp) }

            Button(
                onClick = { estadoActual = EstadoCarga.ERROR },
                modifier = Modifier.weight(1f)
            ) { Text("Error", fontSize = 12.sp) }
        }
    }
}