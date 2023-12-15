package com.example.cartaalta.Screens

import android.widget.Toast
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.cartaalta.funciones.ViewModel
import com.example.cartaalta.modelo.Routes

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ApuestasScreen(viewModel: ViewModel, navController: NavHostController) {
    var context = LocalContext.current
    Wallpaper()

    var apuestaJ1 by remember { mutableStateOf("") }
    var apuestaJ2 by remember { mutableStateOf("") }

    Column(
        Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row {
            Text(text = "Seleccione su cantidad a apostar")
        }
        Spacer(modifier = Modifier.width(80.dp))
        Row {
            TextField(
                value = apuestaJ1,
                onValueChange = { apuestaJ1 = it },
                label = { Text("Apuesta del jugador 1") }
            )
        }

        Row {
            TextField(
                value = apuestaJ2,
                onValueChange = { apuestaJ2 = it },
                label = { Text("Apuesta del jugador 2") }
            )
        }

        Row(Modifier.padding(10.dp)) {
            Button(onClick = {
                viewModel.apuestaCorrecta(apuestaJ1)
                if (viewModel.apuestaCorrecta(apuestaJ1) && viewModel.apuestaCorrecta(apuestaJ2)) {
                    viewModel.apuestaJ1 = apuestaJ1.toInt()
                    viewModel.apuestaJ2 = apuestaJ2.toInt()
                    navController.navigate(Routes.Pantalla2.route)
                } else Toast.makeText(context,"Apuesta no válida",Toast.LENGTH_LONG).show()
                },
                Modifier
                    .padding(10.dp)
                    .border(2.dp, color = Color.Red, shape = CircleShape),
                colors = ButtonDefaults.textButtonColors(Color.White)
            ) {
                Text(
                    text = "Aceptar",
                    color = Color.Black,
                    fontWeight = FontWeight.Bold,
                    fontSize = 20.sp
                )
            }
        }
    }
}

