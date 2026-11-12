package com.example.app21_jc_calculadora_financiera_mvvm.view.screens

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.app21_jc_calculadora_financiera_mvvm.R
import kotlinx.coroutines.delay

// Colores de la marca (los mismos del icono)
private val FondoSplash = Color(0xFF0F2B33)
private val Verde = Color(0xFF22FF88)
private val Ambar = Color(0xFFFFC23D)


@Composable
fun SplashPlanPagosScreen(onTerminado: () -> Unit) {


    val escalaLogo = remember { Animatable(0.6f) }   // el logo crece
    val alfaTexto = remember { Animatable(0f) }      // el texto aparece
    val progreso = remember { Animatable(0f) }       // la barra se llena


    LaunchedEffect(Unit) {
        escalaLogo.animateTo(1f, spring(dampingRatio = 0.45f, stiffness = 180f))
        alfaTexto.animateTo(1f, tween(durationMillis = 500))
        progreso.animateTo(1f, tween(durationMillis = 900, easing = FastOutSlowInEasing))
        delay(200)
        onTerminado()                                // avisa a MainActivity
    }

    Box(
        modifier = Modifier.fillMaxSize().background(FondoSplash),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {

            Image(
                painter = painterResource(R.drawable.ic_launcher_foreground),
                contentDescription = "Logo Plan de Pagos",
                modifier = Modifier.size(180.dp).scale(escalaLogo.value)
            )

            Text(
                "Plan de Pagos",
                color = Color.White,
                fontSize = 30.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.alpha(alfaTexto.value)
            )
            Text(
                "Simulador de cuotas",
                color = Verde,
                fontSize = 16.sp,
                modifier = Modifier.alpha(alfaTexto.value)
            )

            Spacer(Modifier.height(28.dp))

            LinearProgressIndicator(
                progress = { progreso.value },
                color = Ambar,
                trackColor = Color.White.copy(alpha = 0.15f),
                modifier = Modifier.width(180.dp).alpha(alfaTexto.value)
            )
        }


        Text(
            "StrategyMGE",
            color = Color.White.copy(alpha = 0.5f),
            fontSize = 13.sp,
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(bottom = 40.dp)
                .alpha(alfaTexto.value)
        )
    }
}