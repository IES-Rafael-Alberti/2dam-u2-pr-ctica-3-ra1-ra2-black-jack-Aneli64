package com.example.cartaalta

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.cartaalta.Screens.ChooseGameMode
import com.example.cartaalta.Screens.Juego
import com.example.cartaalta.funciones.Baraja
import com.example.cartaalta.modelo.Routes
import com.example.cartaalta.ui.theme.CartaAltaTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CartaAltaTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Baraja.crearBaraja()
                    Baraja.barajar()
                    //creamos variable controller para movernos por las pantallas
                    val navController = rememberNavController()
                    NavHost(
                        navController = navController, startDestination = Routes.Pantalla1.route
                    ) {
                        composable(Routes.Pantalla1.route) { ChooseGameMode(navController) }
                        composable(Routes.Pantalla2.route) { Juego() }
                        composable(Routes.Pantalla3.route) { Juego() } //hay que poner el modo banca
                    }
                }
            }
        }
    }
}