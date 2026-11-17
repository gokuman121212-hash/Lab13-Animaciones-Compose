package com.tecsup.lab13.screens

import androidx.compose.animation.*
import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.delay

@Composable
fun JuegoFinalScreen() {
    var saltando by remember { mutableStateOf(false) }
    var escudoActivo by remember { mutableStateOf(false) }
    var puntos by remember { mutableIntStateOf(0) }
    var vidas by remember { mutableIntStateOf(3) }
    var mensajeEvento by remember { mutableStateOf("¡Listo para despegar!") }
    var mostrarAlerta by remember { mutableStateOf(false) }

    // 1. Animación infinita de fondo / pulso de energía
    val infiniteTransition = rememberInfiniteTransition(label = "bgPulse")
    val pulsoEscudo by infiniteTransition.animateFloat(
        initialValue = 0.92f,
        targetValue = 1.08f,
        animationSpec = infiniteRepeatable(
            animation = tween(600, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "shieldScale"
    )
    val estrellaOffset by infiniteTransition.animateFloat(
        initialValue = 280f,
        targetValue = -40f,
        animationSpec = infiniteRepeatable(
            animation = tween(1800, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        ),
        label = "starMove"
    )

    // 2. Animación física de salto con spring
    val alturaSalto by animateDpAsState(
        targetValue = if (saltando) (-110).dp else 0.dp,
        animationSpec = spring(
            dampingRatio = Spring.DampingRatioMediumBouncy,
            stiffness = Spring.StiffnessMediumLow
        ),
        label = "jumpOffset"
    )

    // 3. Rotación en el aire
    val rotacionJugador by animateFloatAsState(
        targetValue = if (saltando) 360f else 0f,
        animationSpec = tween(durationMillis = 550),
        label = "playerSpin"
    )

    // 4. Cambio de color reactivo según estado
    val colorJugador by animateColorAsState(
        targetValue = when {
            vidas <= 0 -> Color(0xFFD32F2F)
            escudoActivo -> Color(0xFF00E5FF)
            saltando -> Color(0xFFFFD600)
            else -> Color(0xFF76FF03)
        },
        animationSpec = tween(350),
        label = "playerColor"
    )

    // Temporizador automático para aterrizar después de saltar
    LaunchedEffect(saltando) {
        if (saltando) {
            delay(550)
            saltando = false
        }
    }

    // Temporizador del escudo turbo
    LaunchedEffect(escudoActivo) {
        if (escudoActivo) {
            delay(2500)
            escudoActivo = false
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Ejercicio Final: Cyber Runner",
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold
        )
        Text(
            text = "Combinación de 5 animaciones en Jetpack Compose",
            fontSize = 13.sp,
            color = Color.Gray
        )

        Spacer(modifier = Modifier.height(12.dp))

        // HUD Superior con AnimatedContent para el puntaje
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color(0xFF1A1A2E), RoundedCornerShape(12.dp))
                .padding(horizontal = 16.dp, vertical = 10.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text("PUNTOS: ", color = Color.White, fontWeight = FontWeight.Bold)
                AnimatedContent(
                    targetState = puntos,
                    transitionSpec = {
                        (slideInVertically { -it } + fadeIn()) togetherWith
                                (slideOutVertically { it } + fadeOut())
                    },
                    label = "scoreAnim"
                ) { score ->
                    Text(
                        text = "$score",
                        color = Color(0xFFFFD600),
                        fontWeight = FontWeight.ExtraBold,
                        fontSize = 20.sp
                    )
                }
            }
            Text(
                text = "VIDAS: ${"❤️".repeat(vidas.coerceAtLeast(0))}",
                color = Color.White,
                fontWeight = FontWeight.Bold
            )
        }

        Spacer(modifier = Modifier.height(12.dp))

        // Escenario del Videojuego
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(260.dp)
                .clip(RoundedCornerShape(16.dp))
                .background(
                    Brush.verticalGradient(
                        listOf(Color(0xFF0F0C29), Color(0xFF302B63), Color(0xFF24243E))
                    )
                )
                .padding(16.dp)
        ) {
            // Obstáculo / Meteoro en movimiento continuo
            Box(
                modifier = Modifier
                    .align(Alignment.BottomStart)
                    .offset(x = estrellaOffset.dp, y = (-8).dp)
                    .size(28.dp)
                    .background(Color(0xFFFF3D00), CircleShape)
            )

            // Banner de evento con AnimatedVisibility
            androidx.compose.animation.AnimatedVisibility(
                visible = mostrarAlerta,
                enter = fadeIn() + expandVertically(),
                exit = fadeOut() + shrinkVertically(),
                modifier = Modifier.align(Alignment.TopCenter)
            ) {
                Surface(
                    color = Color(0xAA000000),
                    shape = RoundedCornerShape(8.dp)
                ) {
                    Text(
                        text = mensajeEvento,
                        color = Color.White,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp)
                    )
                }
            }

            // Personaje Principal (Nave / Cubo Cyber)
            Box(
                modifier = Modifier
                    .align(Alignment.BottomStart)
                    .offset(x = 36.dp, y = alturaSalto)
                    .graphicsLayer {
                        rotationZ = rotacionJugador
                        scaleX = if (escudoActivo) pulsoEscudo else 1f
                        scaleY = if (escudoActivo) pulsoEscudo else 1f
                    }
                    .size(56.dp)
                    .clip(RoundedCornerShape(14.dp))
                    .background(colorJugador)
                    .then(
                        if (escudoActivo) Modifier.border(3.dp, Color.White, RoundedCornerShape(14.dp))
                        else Modifier
                    ),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = if (vidas <= 0) "X_X" else "^_^",
                    fontWeight = FontWeight.ExtraBold,
                    color = Color.Black
                )
            }

            // Suelo
            Box(
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .fillMaxWidth()
                    .height(6.dp)
                    .background(Color(0xFF00E5FF))
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Controles del Jugador
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Button(
                onClick = {
                    if (vidas > 0) {
                        saltando = true
                        puntos += if (escudoActivo) 20 else 10
                        mensajeEvento = if (escudoActivo) "¡SALTO TURBO +20!" else "¡SALTO +10!"
                        mostrarAlerta = true
                    }
                },
                modifier = Modifier.weight(1f)
            ) {
                Text("Saltar")
            }

            Button(
                onClick = {
                    if (vidas > 0) {
                        escudoActivo = true
                        mensajeEvento = "¡ESCUDO ACTIVO (x2 Puntos)!"
                        mostrarAlerta = true
                    }
                },
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF00838F)),
                modifier = Modifier.weight(1f)
            ) {
                Text("Escudo")
            }
        }

        Spacer(modifier = Modifier.height(8.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            OutlinedButton(
                onClick = {
                    if (!escudoActivo && vidas > 0) {
                        vidas -= 1
                        mensajeEvento = if (vidas == 0) "GAME OVER" else "¡IMPACTO! -1 Vida"
                        mostrarAlerta = true
                    } else if (escudoActivo) {
                        mensajeEvento = "¡Escudo bloqueó el daño!"
                        mostrarAlerta = true
                    }
                },
                modifier = Modifier.weight(1f)
            ) {
                Text("Simular Impacto")
            }

            OutlinedButton(
                onClick = {
                    vidas = 3
                    puntos = 0
                    escudoActivo = false
                    saltando = false
                    mostrarAlerta = false
                },
                modifier = Modifier.weight(1f)
            ) {
                Text("Reiniciar")
            }
        }
    }
}