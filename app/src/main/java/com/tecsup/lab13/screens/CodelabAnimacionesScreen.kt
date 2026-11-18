package com.tecsup.lab13.screens

import androidx.compose.animation.animateColor
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.*
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

enum class TabPage { Home, Work }

@Composable
fun CodelabAnimacionesScreen() {
    var tabSeleccionada by remember { mutableStateOf(TabPage.Home) }
    var tarjetaExpandida by remember { mutableStateOf(false) }

    val backgroundColor by animateColorAsState(
        targetValue = if (tabSeleccionada == TabPage.Home) Color(0xFFFFF8E1) else Color(0xFFE0F7FA),
        animationSpec = tween(500),
        label = "codelabBgColor"
    )

    val transition = updateTransition(targetState = tabSeleccionada, label = "tabTransition")
    val indicatorColor by transition.animateColor(label = "indicatorColor") { page ->
        if (page == TabPage.Home) Color(0xFFFF8F00) else Color(0xFF00838F)
    }
    val indicatorOffset by transition.animateDp(
        transitionSpec = {
            if (TabPage.Home isTransitioningTo TabPage.Work) {
                spring(stiffness = Spring.StiffnessVeryLow)
            } else {
                spring(stiffness = Spring.StiffnessLow)
            }
        },
        label = "indicatorOffset"
    ) { page ->
        if (page == TabPage.Home) 0.dp else 160.dp
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(backgroundColor)
            .verticalScroll(rememberScrollState())
            .padding(20.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Codelab: Animating Elements",
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold
        )
        Text(
            text = "updateTransition + animateContentSize + animateColorAsState",
            fontSize = 12.sp,
            color = Color.DarkGray
        )

        Spacer(modifier = Modifier.height(20.dp))

        Box(
            modifier = Modifier
                .width(320.dp)
                .height(48.dp)
                .background(Color.White, RoundedCornerShape(24.dp))
        ) {
            Box(
                modifier = Modifier
                    .offset(x = indicatorOffset)
                    .width(160.dp)
                    .fillMaxHeight()
                    .background(indicatorColor.copy(alpha = 0.2f), RoundedCornerShape(24.dp))
            )
            Row(modifier = Modifier.fillMaxSize()) {
                Row(
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxHeight()
                        .clickable { tabSeleccionada = TabPage.Home },
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text("🏠 Home", fontWeight = FontWeight.Bold, color = Color(0xFFFF8F00))
                }
                Row(
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxHeight()
                        .clickable { tabSeleccionada = TabPage.Work },
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text("💼 Work", fontWeight = FontWeight.Bold, color = Color(0xFF00838F))
                }
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        Surface(
            modifier = Modifier
                .fillMaxWidth()
                .clickable { tarjetaExpandida = !tarjetaExpandida },
            shape = RoundedCornerShape(16.dp),
            color = Color.White,
            border = BorderStroke(1.dp, indicatorColor),
            shadowElevation = 4.dp
        ) {
            Column(
                modifier = Modifier
                    .padding(16.dp)
                    .animateContentSize(
                        animationSpec = spring(
                            dampingRatio = Spring.DampingRatioLowBouncy,
                            stiffness = Spring.StiffnessLow
                        )
                    )
            ) {
                Text(
                    text = if (tabSeleccionada == TabPage.Home)
                        "Resumen del Codelab (Toca para expandir)"
                    else
                        "Tareas de Trabajo (Toca para expandir)",
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp
                )
                if (tarjetaExpandida) {
                    Spacer(modifier = Modifier.height(10.dp))
                    Text(
                        text = "Este componente utiliza Modifier.animateContentSize() para interpolar " +
                                "suavemente el cambio de altura cuando se muestra u oculta contenido adicional, " +
                                "combinado con updateTransition para sincronizar el color y la posición del indicador.",
                        fontSize = 14.sp,
                        color = Color.DarkGray
                    )
                }
            }
        }
    }
}