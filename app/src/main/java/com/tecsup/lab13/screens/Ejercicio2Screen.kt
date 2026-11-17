package com.tecsup.lab13.screens

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun Ejercicio2Screen() {
    var esAzul by remember { mutableStateOf(true) }
    var usarSpring by remember { mutableStateOf(false) }

    val colorAnimado by animateColorAsState(
        targetValue = if (esAzul) Color(0xFF1976D2) else Color(0xFF2E7D32),
        animationSpec = if (usarSpring) {
            spring(dampingRatio = Spring.DampingRatioMediumBouncy, stiffness = Spring.StiffnessLow)
        } else {
            tween(durationMillis = 800)
        },
        label = "colorBoxAnimation"
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "Ejercicio 2: animateColorAsState",
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = "Modo actual: ${if (usarSpring) "Spring (Rebote)" else "Tween (800ms)"}",
            fontSize = 14.sp,
            color = Color.Gray
        )

        Spacer(modifier = Modifier.height(24.dp))

        Box(
            modifier = Modifier
                .size(180.dp)
                .clip(RoundedCornerShape(20.dp))
                .background(colorAnimado),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = if (esAzul) "AZUL" else "VERDE",
                color = Color.White,
                fontWeight = FontWeight.Bold,
                fontSize = 18.sp
            )
        }

        Spacer(modifier = Modifier.height(32.dp))

        Button(
            onClick = { esAzul = !esAzul },
            modifier = Modifier.fillMaxWidth(0.75f)
        ) {
            Text("Cambiar Color (${if (esAzul) "a Verde" else "a Azul"})")
        }

        Spacer(modifier = Modifier.height(12.dp))

        OutlinedButton(
            onClick = { usarSpring = !usarSpring },
            modifier = Modifier.fillMaxWidth(0.75f)
        ) {
            Text("Cambiar Spec (${if (usarSpring) "Usar Tween" else "Usar Spring"})")
        }
    }
}