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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
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
            //onClick = { navController.navigate(Routes.Pantalla2.route) },
            onClick = { navController.navigate(Routes.Pantalla3.route) },
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
fun navegafinPartidaScreen(navController: NavHostController) {
    navController.navigate(Routes.Pantalla4.route)
}

fun navegaApuestasScreen(navController: NavHostController){
    navController.navigate(Routes.Pantalla4.route)
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

    Column(
        Modifier
            .fillMaxSize()
            .padding(10.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {

//ESTOS 4 BOTONES QUE ESTEN EN FUNCIONES FUERA PARA QUITAR DE AQUI LOS ROW
        Row {
            DameCartaJugador(onDameCartaClick = { viewModel.addCardToHandPlayer(2) })
            Pasar(onPasaClick = { viewModel.pasarPlayer(2) })
        }

        Row {
            DameCartaJugador(onDameCartaClick = { viewModel.addCardToHandPlayer(1) })
            Pasar(onPasaClick = { viewModel.pasarPlayer(1) })
        }

        ShowPlayerPoints(
            puntPlayer1 = viewModel.puntosJug1,
            puntPlayer2 = viewModel.puntosJug2,
            apuestaJ1 = viewModel.apuestaJ1,
            apuestaJ2 = viewModel.apuestaJ2
        )

    }
}

@Composable
fun ShowPlayerPoints(puntPlayer1: Int, puntPlayer2: Int, apuestaJ1: Int, apuestaJ2: Int) {
    Row {
        Text(text = "Puntos Jugador 1 -> $puntPlayer1")
        Spacer(modifier = Modifier.width(60.dp))
        Text(text = "Puntos Jugador 2 -> $puntPlayer2")
    }
    Row {
        Text(text = "J1 -> $apuestaJ1 fichas")
        Spacer(modifier = Modifier.width(60.dp))
        Text(text = "J2 -> $apuestaJ2 fichas")
    }
}

//Funcion lambda que al pulsar en el boton dame carta, obtiene una carta de la baraja
@Composable
fun DameCartaJugador(onDameCartaClick: () -> Unit) {
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
fun Pasar(onPasaClick: () -> Unit) {
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