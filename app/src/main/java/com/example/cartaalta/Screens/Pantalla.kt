package com.example.cartaalta.Screens

import android.content.Context
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.cartaalta.funciones.Baraja
import com.example.cartaalta.R
import com.example.cartaalta.funciones.Jugador
import com.example.cartaalta.modelo.Routes

//Imagen de fondo
@Composable
fun Wallpaper() {
    Image(
        painter = painterResource(id = R.drawable.fondo_poker), contentDescription = "",
        modifier = Modifier
            .fillMaxSize(), contentScale = ContentScale.FillBounds
    )
}

@Composable
fun Modo2vs2(navController: NavHostController) {
    Row(Modifier.padding(10.dp)) {
        Button(
            onClick = { navController.navigate(Routes.Pantalla2.route) },
            Modifier
                .padding(10.dp)
                .border(2.dp, color = Color.Red, shape = CircleShape),
            colors = ButtonDefaults.textButtonColors(Color.White)
        ) {
            Text(
                text = "2 jugadores",
                color = Color.Black,
                fontWeight = FontWeight.Bold,
                fontSize = 20.sp
            )
        }
    }
}

@Composable
fun ModoVsBanca(navController: NavHostController) {
    Row(Modifier.padding(10.dp)) {
        Button(
            onClick = { navController.navigate(Routes.Pantalla3.route) },
            Modifier
                .padding(10.dp)
                .border(2.dp, color = Color.Red, shape = CircleShape),
            colors = ButtonDefaults.textButtonColors(Color.White)
        ) {
            Text(
                text = "Contra maquina",
                color = Color.Black,
                fontWeight = FontWeight.Bold,
                fontSize = 20.sp
            )
        }
    }
}

@Composable
fun ChooseGameMode(navController: NavHostController) {
    Wallpaper()

    Column(
        Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row {
            Text(text = "Seleccione modo de juego")
        }
        Modo2vs2(navController = navController)
        ModoVsBanca(navController = navController)
    }
}

//funcion que nos va imprimiendo las cartas en base a la mano y un contexto
@Composable
fun BucleCarta(mano: MutableList<Int>, context: Context) {
    LazyRow {
        items(mano) { item ->
            val intCarta = context.resources.getIdentifier(
                "c${item}",
                "drawable",
                context.packageName,
            )
            Image(
                painter = painterResource(id = intCarta),
                contentDescription = "",
                modifier = Modifier.size(100.dp)
            )
        }
    }
}

/*Imprimimos y damos formato para dos bucles, uno para cada jugador,
haciendo uso de la funcion de bucles anterior*/
@Composable
fun ImprimeBucles(player1: Jugador, player2: Jugador, context: Context) {
    Column(
        Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Bottom,
    ) {
        BucleCarta(mano = player1.mano, context = context)
    }

    Column(
        Modifier
            .fillMaxSize()
            .padding(10.dp),
        verticalArrangement = Arrangement.Top,
    ) {
        BucleCarta(mano = player2.mano, context = context)
    }

}

@Preview
@Composable
fun Juego() {
    Wallpaper()

    //Variables necesarias para las cartas de nuestro juego
    val context = LocalContext.current
    var dorsoCarta by rememberSaveable { mutableStateOf("detras") }
    var idCarta by rememberSaveable {
        mutableStateOf(
            context.resources.getIdentifier(
                dorsoCarta,
                "drawable",
                context.packageName
            )
        )
    }

    //Variables necesarias para los jugadores
    val handPlayer1 by remember { mutableStateOf(mutableListOf<Int>()) }
    val player1 = Jugador("player1", handPlayer1)
    val handPlayer2 by remember { mutableStateOf(mutableListOf<Int>()) }
    val player2 = Jugador("player2", handPlayer2)
    var playerTurn1 by remember { mutableStateOf(true) }
    var playerTurn2 by remember { mutableStateOf(false) }
    var puntPlayer1 by remember { mutableStateOf(0) }
    var puntPlayer2 by remember { mutableStateOf(0) }


    //Variable que controla si nuestros botones son pulsados para almacenar la opcion de pasar
    var btnPasarP1IsClicked by remember { mutableStateOf(false) }
    var btnPasarP2IsClicked by remember { mutableStateOf(false) }

    ImprimeBucles(player1 = player1, player2 = player2, context = context)

    Column(
        Modifier
            .fillMaxSize()
            .padding(10.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {

        Row {
            dameCarta(onDameCartaClick = {
                val carta = Baraja.dameCarta()

                if (playerTurn1 && !btnPasarP1IsClicked && puntPlayer1 < 21) {
                    dorsoCarta = "c${carta.idDrawable}"
                    player1.mano.add(carta.idDrawable)
                    if (!btnPasarP2IsClicked) {
                        playerTurn1 = false
                        playerTurn2 = true
                    }
                    if (carta.puntosMin == 1) {
                        if (puntPlayer1 + carta.puntosMax < 21) puntPlayer1 += carta.puntosMax else puntPlayer1 += carta.puntosMin
                    } else puntPlayer1 += carta.puntosMin


                } else if (playerTurn2 && !btnPasarP2IsClicked && puntPlayer2 < 21) {
                    dorsoCarta = "c${carta.idDrawable}"
                    player2.mano.add(carta.idDrawable)
                    if (!btnPasarP1IsClicked) {
                        playerTurn1 = true
                        playerTurn2 = false
                    }
                    if (carta.puntosMin == 1) {
                        if (puntPlayer2 + carta.puntosMax < 21) puntPlayer2 += carta.puntosMax else puntPlayer2 += carta.puntosMin
                    } else puntPlayer2 += carta.puntosMin
                }
            })

            pasar(
                onPasaClick = {
                    if (playerTurn1) {
                        playerTurn1 = false
                        playerTurn2 = true
                        //definimos isClicked a false para que el jug no pueda pedir cartas de nuevo
                        btnPasarP1IsClicked = true
                    } else if (playerTurn2) {
                        playerTurn1 = true
                        playerTurn2 = false
                        //definimos isClicked a false para que el jug no pueda pedir cartas de nuevo
                        btnPasarP2IsClicked = true
                    }
                })
        }

        ShowPuntosJugadores(puntPlayer1 = puntPlayer1, puntPlayer2 = puntPlayer2)

        TurnoJugador(turnoJug1 = playerTurn1)

        puntuacionesPartida(puntPlayer1 = puntPlayer1, manoSizeP1 = handPlayer1, puntPlayer2 = puntPlayer2, manoSizeP2 = handPlayer2)


    }

    //Metodo que nos permite ir actualizando las cartas
    LaunchedEffect(dorsoCarta) {
        val id = context.resources.getIdentifier(dorsoCarta, "drawable", context.packageName)
        idCarta = id
    }


}

@Composable
fun puntuacionesPartida(
    puntPlayer1: Int,
    manoSizeP1: MutableList<Int>,
    puntPlayer2: Int,
    manoSizeP2: MutableList<Int>
) {

    var boolPuntosValidos = false
    var boolPuntosMayor = false
    var boolNumCartas = false

    when {
        puntPlayer1 <= 21 -> boolPuntosValidos = true
        puntPlayer1 > puntPlayer2 -> boolPuntosMayor = true
        manoSizeP1.size < manoSizeP2.size -> boolNumCartas = true
    }

    when {
        boolPuntosValidos && boolPuntosMayor -> Text(text = "GANADOR JUGADOR 1!!!")
        boolPuntosValidos && !boolPuntosMayor -> Text(text = "GANADOR JUGADOR 2!!!")
        puntPlayer1 == puntPlayer2 -> {
            if (boolNumCartas) Text(text = "GANADOR JUGADOR 1!!!") else Text(text = "GANADOR JUGADOR 1!!!")
        }
    }
}

//Funcion que nos muestra el turno de cada jugador
@Composable
fun TurnoJugador(turnoJug1: Boolean) {
    if (turnoJug1) {
        Row(Modifier.padding(30.dp)) {
            Text(text = "Turno Jugador 1")
        }
    } else {
        Row(Modifier.padding(30.dp)) {
            Text(text = "Turno Jugador 2")
        }
    }

}

@Composable
fun PuntosJug1(puntosJug1: Int) {
    Text(text = "Puntos Jugador 1 -> $puntosJug1")
}

@Composable
fun PuntosJug2(puntosJug2: Int) {
    Text(text = "Puntos Jugador 2 -> $puntosJug2")
}

@Composable
fun ShowPuntosJugadores(puntPlayer1: Int, puntPlayer2: Int) {
    Row {
        PuntosJug1(puntosJug1 = puntPlayer1)
        Spacer(modifier = Modifier.width(40.dp))
        PuntosJug2(puntosJug2 = puntPlayer2)
    }
}

//Funcion lambda que al pulsar en el boton dame carta, obtiene una carta de la baraja
@Composable
fun dameCarta(onDameCartaClick: () -> Unit) {
    Row(Modifier.padding(10.dp)) {
        Button(
            onClick = {
                onDameCartaClick()
            },
            Modifier
                .padding(10.dp)
                .border(2.dp, color = Color.Red, shape = CircleShape),
            colors = ButtonDefaults.textButtonColors(Color.White)
        ) {
            Text(
                text = "Dame carta",
                color = Color.Black,
                fontWeight = FontWeight.Bold,
                fontSize = 20.sp
            )
        }
    }
}

@Composable
fun pasar(onPasaClick: () -> Unit) {
    Row(Modifier.padding(10.dp)) {
        Button(
            onClick = {
                onPasaClick()
            },
            Modifier
                .padding(10.dp)
                .border(2.dp, color = Color.Red, shape = CircleShape),
            colors = ButtonDefaults.textButtonColors(Color.White)
        ) {
            Text(
                text = "Pasar",
                color = Color.Black,
                fontWeight = FontWeight.Bold,
                fontSize = 20.sp
            )
        }
    }
}