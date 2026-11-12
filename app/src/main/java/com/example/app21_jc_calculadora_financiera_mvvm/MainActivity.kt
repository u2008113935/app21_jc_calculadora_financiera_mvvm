package com.example.app21_jc_calculadora_financiera_mvvm

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.Crossfade
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.example.app21_jc_calculadora_financiera_mvvm.view.screens.PlanPagosScreen
import com.example.app21_jc_calculadora_financiera_mvvm.view.screens.SplashPlanPagosScreen
import com.example.app21_jc_calculadora_financiera_mvvm.view.theme.App21_jc_calculadora_financiera_mvvmTheme

// Punto de entrada: splash del sistema -> splash animado -> pantalla principal
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        installSplashScreen()                  // splash del sistema (mismo fondo y logo)
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            App21_jc_calculadora_financiera_mvvmTheme {
                // true = mostrando splash. rememberSaveable: no se repite al girar
                var mostrarSplash by rememberSaveable { mutableStateOf(true) }

                // Crossfade: transicion suave entre splash y pantalla principal
                Crossfade(
                    targetState = mostrarSplash,
                    animationSpec = tween(durationMillis = 600),
                    label = "splash"
                ) { enSplash ->
                    if (enSplash) {
                        SplashPlanPagosScreen(onTerminado = { mostrarSplash = false })
                    } else {
                        Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                            PlanPagosScreen(modifier = Modifier.padding(innerPadding))
                        }
                    }
                }
            }
        }
    }
}