package com.tecsup.lab13.screens

import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.border
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

@Composable
fun Ejercicio3Screen() {
    var expandidoYMovido by remember { mutableStateOf(false) }
    var offsetPrimero by remember { mutableStateOf(true) }

    val tamano by animateDpAsState(
        targetValue = if (expandidoYMovido) 140.dp else 80.dp,
        animationSpec = tween(durationMillis = 600),
        label = "sizeAnim"
    )
    val desplazamientoX by animateDpAsState(
        targetValue = if (expandidoYMovido) 90.dp else 0.dp,
        animationSpec = tween(durationMillis = 600),
        label = "offsetXAnim"
    )
    val desplazamientoY by animateDpAsState(
        targetValue = if (expandidoYMovido) 40.dp else 0.dp,
        animationSpec = tween(durationMillis = 600),
        label = "offsetYAnim"
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Ejercicio 3: Tamaño y Posición",
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold
        )
        Text(
            text = "animateDpAsState + Modifier.offset",
            fontSize = 14.sp,
            color = Color.Gray
        )

        Spacer(modifier = Modifier.height(16.dp))

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(240.dp)
                .border(1.dp, Color.LightGray, RoundedCornerShape(12.dp))
                .padding(16.dp)
        ) {
            val modCuadro = if (offsetPrimero) {
                Modifier
                    .offset(x = desplazamientoX, y = desplazamientoY)
                    .size(tamano)
                    .background(Color(0xFFE65100), RoundedCornerShape(12.dp))
            } else {
                Modifier
                    .size(tamano)
                    .background(Color(0xFF6A1B9A), RoundedCornerShape(12.dp))
                    .offset(x = desplazamientoX, y = desplazamientoY)
            }

            Box(
                modifier = modCuadro,
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "${tamano.value.toInt()}dp",
                    color = Color.White,
                    fontWeight = FontWeight.Bold
                )
            }
        }

        Spacer(modifier = Modifier.height(20.dp))

        Button(
            onClick = { expandidoYMovido = !expandidoYMovido },
            modifier = Modifier.fillMaxWidth(0.8f)
        ) {
            Text(if (expandidoYMovido) "Restaurar Posición y Tamaño" else "Mover y Escalar Cuadro")
        }

        Spacer(modifier = Modifier.height(12.dp))

        OutlinedButton(
            onClick = { offsetPrimero = !offsetPrimero },
            modifier = Modifier.fillMaxWidth(0.8f)
        ) {
            Text(
                if (offsetPrimero) "Orden: offset() -> size() -> bg()"
                else "Orden: size() -> bg() -> offset()"
            )
        }
    }
}