package com.example.cartaalta.Screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.cartaalta.funciones.ViewModel

@Composable
fun PantallaFinPartida(viewModel: ViewModel) {
    Wallpaper()
    Column(
        Modifier
            .fillMaxSize()
            .padding(10.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Row {
            Text(text = "PARTIDA FINALIZADA")
        }
        Row {
            ShowPlayerPoints(
                puntPlayer1 = viewModel.puntosJug1,
                puntPlayer2 = viewModel.puntosJug2,
                apuestaJ1 = viewModel.apuestaJ1,
                apuestaJ2 = viewModel.apuestaJ2
            )
        }
    }
}