package com.example.cartaalta.Screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CutCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.cartaalta.funciones.Jugador
import com.example.cartaalta.funciones.ViewModel
import com.example.cartaalta.modelo.Routes

/**
 * Metodo que navega hasta la pantalla de fin de partida
 *
 * @param navController navegador de rutas entre pantallas
 */
@Composable
fun navegafinPartidaScreen(navController: NavHostController) {
    navController.navigate(Routes.Pantalla4.route)
}


/**
 * Interfaz del reinicio del juego
 *
 * @param onResetClick lamba que ejecuta al hacer click en el boton Restart
 */
@Composable
fun ResetGame(onResetClick: () -> Unit) {
    Row(Modifier.padding(top = 60.dp)) {
        Button(
            onClick = {
                onResetClick()
            },
            colors = ButtonDefaults.textButtonColors(Color.Black),
            modifier = Modifier
                .background(color = Color.Black)
                .border(2.dp, color = Color.White, shape = CutCornerShape(24.dp))
        ) {

            Text(
                text = "Restart",
                modifier = Modifier.padding(15.dp),
                color = Color.White,
                fontFamily = FontFamily.Serif,
                fontWeight = FontWeight.Bold,
                fontSize = 15.sp
            )
        }
    }
}

/**
 * Interfaz de nuestra pantalla final de partida. En ella contamos con todos los resultados y con
 * un boton para reiniciar la partida, en la que reiniciamos todos los valores del juego, y
 * navegamos hasta la pagina inicial de ChooseGameMode
 *
 * @param navController navegador de rutas entre pantallas
 * @param viewModel clase viewmodel con la logica del programa
 */
@Composable
fun PantallaFinPartida(navController: NavHostController,viewModel: ViewModel) {
    Wallpaper()

    Column(
        Modifier
            .fillMaxSize()
            .padding(10.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Row(
            Modifier
                .padding(bottom = 60.dp)
                .offset(y = 30.dp)
        ) {
            Box(
                modifier = Modifier
                    .background(color = Color.Black)
                    .border(2.dp, color = Color.White, shape = CutCornerShape(24.dp))
            ) {
                Text(
                    text = "PARTIDA FINALIZADA",
                    modifier = Modifier.padding(20.dp),
                    color = Color.White,
                    fontFamily = FontFamily.Serif,
                    fontWeight = FontWeight.Bold,
                    fontSize = 20.sp
                )
            }
        }


        Row(Modifier.padding(bottom = 20.dp)) {
            Box(
                modifier = Modifier
                    .background(color = Color.Black)
                    .border(2.dp, color = Color.White, shape = CutCornerShape(24.dp))
            ) {
                if (viewModel.jugador1Ganador) {
                    Text(
                        text = "GANADOR JUGADOR 1 !!!",
                        modifier = Modifier.padding(20.dp),
                        color = Color.White,
                        fontFamily = FontFamily.Serif,
                        fontWeight = FontWeight.Bold,
                        fontSize = 20.sp
                    )
                } else if (viewModel.jugador2Ganador) {
                    Text(
                        text = "GANADOR JUGADOR 2 !!!",
                        modifier = Modifier.padding(20.dp),
                        color = Color.White,
                        fontFamily = FontFamily.Serif,
                        fontWeight = FontWeight.Bold,
                        fontSize = 20.sp
                    )
                } else if (viewModel.empateJugadores){
                    Text(
                        text = "EMPATE !!!",
                        modifier = Modifier.padding(20.dp),
                        color = Color.White,
                        fontFamily = FontFamily.Serif,
                        fontWeight = FontWeight.Bold,
                        fontSize = 20.sp
                    )
                }
            }
        }
        Row(Modifier.padding(bottom = 20.dp)) {
            Box(
                modifier = Modifier
                    .background(color = Color.Black)
                    .border(2.dp, color = Color.White, shape = CutCornerShape(24.dp))
            ) {
                Text(
                    text = "Resultados",
                    modifier = Modifier.padding(20.dp),
                    color = Color.White,
                    fontFamily = FontFamily.Serif,
                    fontWeight = FontWeight.Bold,
                    fontSize = 20.sp
                )
            }
        }


        Row {
            ShowPuntosApuestasPlayer(
                puntPlayer1 = viewModel.puntosJug1,
                puntPlayer2 = viewModel.puntosJug2,
                apuestaJ1 = viewModel.apuestaJ1,
                apuestaJ2 = viewModel.apuestaJ2
            )

        }

        Row(Modifier.padding(top = 20.dp)) {
            Box(
                modifier = Modifier
                    .background(color = Color.Black)
                    .border(2.dp, color = Color.White, shape = CutCornerShape(24.dp))
            ) {
                if (viewModel.jugador1Ganador) {
                    Text(
                        text = "J1 se lleva la suma de ${viewModel.apuestaJ1 + viewModel.apuestaJ2} fichas!!",
                        modifier = Modifier.padding(20.dp),
                        color = Color.White,
                        fontFamily = FontFamily.Serif,
                        fontWeight = FontWeight.Bold,
                        fontSize = 20.sp
                    )
                } else if (viewModel.jugador2Ganador){
                    Text(
                        text = "J2 se lleva la suma de ${viewModel.apuestaJ1 + viewModel.apuestaJ2} fichas!!",
                        modifier = Modifier.padding(20.dp),
                        color = Color.White,
                        fontFamily = FontFamily.Serif,
                        fontWeight = FontWeight.Bold,
                        fontSize = 20.sp
                    )
                }
                else {
                    Text(
                        text = "Se reparten las fichas a partes iguales!! " +
                                "J1 -> ${(viewModel.apuestaJ1 + viewModel.apuestaJ2) / 2} " +
                                "J2 -> ${(viewModel.apuestaJ1 + viewModel.apuestaJ2) / 2}",
                        modifier = Modifier.padding(20.dp),
                        color = Color.White,
                        fontFamily = FontFamily.Serif,
                        fontWeight = FontWeight.Bold,
                        fontSize = 20.sp
                    )
                }

            }
        }

        Row {
            ResetGame(onResetClick = {
                viewModel.apuestaJ1 = 0
                viewModel.apuestaJ2 = 0
                viewModel.puntosJug1 = 0
                viewModel.puntosJug2 = 0
                viewModel.jugador1.value = Jugador("jugador1", mutableListOf())
                viewModel.jugador2.value = Jugador("jugador2", mutableListOf())
                viewModel.turnoJugador = 1
                viewModel.boolSalirPartida = false
                viewModel.jugador1Ganador = false
                viewModel.jugador2Ganador = false
                viewModel.btnPasarJ1IsClicked.value = false
                viewModel.btnPasarJ2IsClicked.value = false
                navController.navigate(Routes.Pantalla1.route) })
        }

    }
}
