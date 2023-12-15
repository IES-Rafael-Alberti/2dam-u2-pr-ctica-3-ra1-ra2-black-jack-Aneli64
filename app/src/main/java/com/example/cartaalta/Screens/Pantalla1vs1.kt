package com.example.cartaalta.Screens

import android.content.Context
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.CutCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.cartaalta.R
import com.example.cartaalta.funciones.ViewModel
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
fun Modo1vs1(navController: NavHostController) {
    Row(Modifier.padding(10.dp)) {
        Button(
            onClick = { navController.navigate(Routes.Pantalla3.route) },
            colors = ButtonDefaults.textButtonColors(Color.Black),
            modifier = Modifier
                .background(color = Color.Black)
                .border(2.dp, color = Color.White, shape = CutCornerShape(24.dp))
        ) {
            Text(
                text = "1 Vs 1",
                modifier = Modifier.padding(20.dp),
                color = Color.White,
                fontFamily = FontFamily.Serif,
                fontWeight = FontWeight.Bold,
                fontSize = 20.sp
            )
        }
    }
}

@Composable
fun PintaCartasManoJugador(context: Context, cartasMano: MutableList<Int>, cartas: Int) {

    LazyRow {
        items(cartasMano) { item ->
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

@Composable
fun ImprimeCartasJugadores(viewModel: ViewModel, context: Context, cartasJ1: Int, cartasJ2: Int) {
    Column(
        Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Bottom,
    ) {
        PintaCartasManoJugador(
            context = context,
            cartasMano = viewModel.getHandPlayer(1),
            cartas = cartasJ1
        )
    }
    Column(
        Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Top,
    ) {
        PintaCartasManoJugador(
            context = context,
            cartasMano = viewModel.getHandPlayer(2),
            cartas = cartasJ2
        )

    }

}

@Composable
fun BlackJack(viewModel: ViewModel, navController: NavHostController) {
    Wallpaper()

    val context = LocalContext.current
    val cartasJ1: Int by viewModel._numCartasJug1.observeAsState(0)
    val cartasJ2: Int by viewModel._numCartasJug2.observeAsState(0)

    val btnPasarJ1: Boolean by viewModel.btnPasarJ1IsClicked.observeAsState(false)
    val btnPasarJ2: Boolean by viewModel.btnPasarJ2IsClicked.observeAsState(false)

    viewModel.salirPartida(btnPasarJ1, btnPasarJ2)

    if (viewModel.boolSalirPartida) {
        navegafinPartidaScreen(navController = navController)
        viewModel.ganadorPartida()
    }

    ImprimeCartasJugadores(viewModel = viewModel, context = context, cartasJ1, cartasJ2)
    InteraccionBotones(viewModel = viewModel)

}

@Composable
fun InteraccionBotones(viewModel: ViewModel) {
    Column(
        Modifier
            .fillMaxSize()
            .padding(10.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {

        Row(Modifier.padding(bottom = 50.dp)) {
            DameCartaJugador(id = 2, onDameCartaClick = { viewModel.addCardToHandPlayer(2) })
            Pasar(id = 2, onPasaClick = { viewModel.pasarPlayer(2) })
        }

        Row {
            ShowPlayerPoints(
                puntPlayer1 = viewModel.puntosJug1,
                puntPlayer2 = viewModel.puntosJug2,
                apuestaJ1 = viewModel.apuestaJ1,
                apuestaJ2 = viewModel.apuestaJ2
            )
        }

        Row(Modifier.padding(top = 50.dp, bottom = 30.dp)) {
            DameCartaJugador(id = 1, onDameCartaClick = { viewModel.addCardToHandPlayer(1) })
            Pasar(id = 1, onPasaClick = { viewModel.pasarPlayer(1) })
        }

        Row {
            Box(
                modifier = Modifier
                    .background(color = Color.Black)
                    .border(2.dp, color = Color.White, shape = CutCornerShape(24.dp))
            ) {
                Text(
                    text = "Turno Jugador ${viewModel.turnoJugador}",
                    modifier = Modifier.padding(15.dp),
                    color = Color.White,
                    fontFamily = FontFamily.Serif,
                    fontWeight = FontWeight.Bold,
                    fontSize = 15.sp
                )
            }
        }
    }
}

@Composable
fun ShowPlayerPoints(puntPlayer1: Int, puntPlayer2: Int, apuestaJ1: Int, apuestaJ2: Int) {
    Column {
        Row {
            Box(
                modifier = Modifier
                    .background(color = Color.Black)
                    .border(2.dp, color = Color.White, shape = CutCornerShape(24.dp))

            ) {
                Text(
                    text = "Puntos J2 -> $puntPlayer2",
                    modifier = Modifier.padding(20.dp),
                    color = Color.White,
                    fontFamily = FontFamily.Serif,
                    fontWeight = FontWeight.Bold,
                    fontSize = 15.sp
                )
            }

        }
        Row(Modifier.padding(top = 10.dp)) {
            Box(
                modifier = Modifier
                    .background(color = Color.Black)
                    .border(2.dp, color = Color.White, shape = CutCornerShape(24.dp))
            ) {
                Text(
                    text = "Puntos J1 -> $puntPlayer1",
                    modifier = Modifier.padding(20.dp),
                    color = Color.White,
                    fontFamily = FontFamily.Serif,
                    fontWeight = FontWeight.Bold,
                    fontSize = 15.sp
                )
            }

        }
    }

    Column {
        Row {
            Box(
                modifier = Modifier
                    .background(color = Color.Black)
                    .border(2.dp, color = Color.White, shape = CutCornerShape(24.dp))
            ) {
                Text(
                    text = "J2 -> $apuestaJ2 fichas",
                    modifier = Modifier.padding(20.dp),
                    color = Color.White,
                    fontFamily = FontFamily.Serif,
                    fontWeight = FontWeight.Bold,
                    fontSize = 15.sp
                )
            }

        }
        Row(Modifier.padding(top = 10.dp)) {
            Box(
                modifier = Modifier
                    .background(color = Color.Black)
                    .border(2.dp, color = Color.White, shape = CutCornerShape(24.dp))
            ) {
                Text(
                    text = "J1 -> $apuestaJ1 fichas",
                    modifier = Modifier.padding(20.dp),
                    color = Color.White,
                    fontFamily = FontFamily.Serif,
                    fontWeight = FontWeight.Bold,
                    fontSize = 15.sp
                )
            }
        }
    }
}

//Funcion lambda que al pulsar en el boton dame carta, obtiene una carta de la baraja
@Composable
fun DameCartaJugador(id: Int, onDameCartaClick: () -> Unit) {
    Row(Modifier.padding(10.dp)) {
        Button(
            onClick = {
                onDameCartaClick()
            },
            colors = ButtonDefaults.textButtonColors(Color.Black),
            modifier = Modifier
                .background(color = Color.Black)
                .border(2.dp, color = Color.White, shape = CutCornerShape(24.dp))
        ) {
            if (id == 1) {
                Text(
                    text = "Dame carta J1",
                    modifier = Modifier.padding(15.dp),
                    color = Color.White,
                    fontFamily = FontFamily.Serif,
                    fontWeight = FontWeight.Bold,
                    fontSize = 15.sp
                )
            } else {
                Text(
                    text = "Dame carta J2",
                    modifier = Modifier.padding(15.dp),
                    color = Color.White,
                    fontFamily = FontFamily.Serif,
                    fontWeight = FontWeight.Bold,
                    fontSize = 15.sp
                )
            }
        }
    }
}

@Composable
fun Pasar(id: Int, onPasaClick: () -> Unit) {
    Row(Modifier.padding(10.dp)) {
        Button(
            onClick = {
                onPasaClick()
            },
            colors = ButtonDefaults.textButtonColors(Color.Black),
            modifier = Modifier
                .background(color = Color.Black)
                .border(2.dp, color = Color.White, shape = CutCornerShape(24.dp))
        ) {
            if (id == 1) {
                Text(
                    text = "Pasar J1",
                    modifier = Modifier.padding(15.dp),
                    color = Color.White,
                    fontFamily = FontFamily.Serif,
                    fontWeight = FontWeight.Bold,
                    fontSize = 15.sp
                )
            } else {
                Text(
                    text = "Pasar J2",
                    modifier = Modifier.padding(15.dp),
                    color = Color.White,
                    fontFamily = FontFamily.Serif,
                    fontWeight = FontWeight.Bold,
                    fontSize = 15.sp
                )
            }
        }
    }
}