package com.example.cartaalta.funciones

import android.app.Application
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CutCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import java.lang.NumberFormatException

/**
 * Clase Viewmodel que almacena toda la logica operacional de nuestro juego
 *
 * @param app aplicacion a la que hacemos referencia en nuestro juego
 * @property jugador1 liveData que almacena nuestro J1
 * @property jugador2 liveData que almacena nuestro J2
 * @property _numCartasJug1 liveData que almacena las cartas del J1
 * @property numCartasJug1 liveData publico que apunta a nuestro liveData privado J1
 * @property _numCartasJug2 liveData que almacena las cartas del J2
 * @property numCartasJug2 liveData publico que apunta a nuestro liveData privado J2
 * @property puntosJug1 puntos totales J1
 * @property puntosJug2 puntos totales J2
 * @property apuestaJ1 apuesta total J1
 * @property apuestaJ2 apuesta total J2
 * @property jugador1Ganador boolean para la victoria del J1
 * @property jugador2Ganador boolean para la victoria del J2
 * @property empateJugadores boolean para el empate entre jugadores
 * @property _btnPasarJ1IsClicked liveData booleano que almacena el estado de nustro boton pasar J1
 * @property btnPasarJ1IsClicked liveData booleano que apunta a nuestro btn privdo J1
 * @property @property _btnPasarJ2IsClicked liveData booleano que almacena el estado de nustro boton pasar J2
 * @property btnPasarJ2IsClicked liveData booleano que apunta a nuestro btn privdo J2
 * @property turnoJugador turno del jugador en cuestión
 * @property boolSalirPartida booleano que controla el estado de la partida en curso o para para finalizarla
 */
class ViewModel(app: Application) : AndroidViewModel(app) {
    val jugador1 = MutableLiveData<Jugador>()
    val jugador2 = MutableLiveData<Jugador>()

    private val _numCartasJug1 = MutableLiveData<Int>()
    val numCartasJug1: LiveData<Int> = _numCartasJug1

    private val _numCartasJug2 = MutableLiveData<Int>()
    val numCartasJug2: LiveData<Int> = _numCartasJug2

    var puntosJug1 = 0
    var puntosJug2 = 0

    var apuestaJ1 = 0
    var apuestaJ2 = 0

    var jugador1Ganador: Boolean = false
    var jugador2Ganador: Boolean = false
    var empateJugadores: Boolean = false

    private var _btnPasarJ1IsClicked = MutableLiveData<Boolean>()
    var btnPasarJ1IsClicked = _btnPasarJ1IsClicked
    private var _btnPasarJ2IsClicked = MutableLiveData<Boolean>()
    var btnPasarJ2IsClicked = _btnPasarJ2IsClicked

    var turnoJugador = 1

    var boolSalirPartida = false

    init {
        Baraja.crearBaraja()
        Baraja.barajar()

        jugador1.value = Jugador("jugador1", mutableListOf())
        jugador2.value = Jugador("jugador2", mutableListOf())

        _btnPasarJ1IsClicked.value = false
        _btnPasarJ2IsClicked.value = false

    }

    /**
     * Comprobación del correcto formato de la apuesta introducida
     *
     * @param apuesta valor de apuesta escrita por el usuario
     * @return true/false dependiendo del correcto formato de apuesta introducido
     */
    fun apuestaCorrecta(apuesta: String): Boolean {
        return try {
            apuesta.toInt() in 0..400
        } catch (e: NumberFormatException) {
            false
        }
    }

    /**
     * Obtiene la mano del jugador en cuestión
     *
     * @param id identificador del jugador
     * @return lista de cartas del jugador
     */
    fun getHandPlayer(id: Int): MutableList<Int> {
        return when (id) {
            1 -> jugador1.value?.mano ?: mutableListOf()
            2 -> jugador2.value?.mano ?: mutableListOf()
            else -> throw IllegalArgumentException("ID de jugador no válido: $id")
        }
    }

    /**
     * Metodo que fija nuestra variable boolSalirPartida a true en base a el estado de
     * los botones pasar y sus puntuaciones
     *
     * @param btnPasarJ1 estado del boton pasar J1
     * @param btnPasarJ2 estado del boton pasar J2
     */
    fun salirPartida(btnPasarJ1: Boolean, btnPasarJ2: Boolean) {
        if ((btnPasarJ1 || puntosJug1 >= 21) && (btnPasarJ2 || puntosJug2 >= 21)) {
            boolSalirPartida = true
        }
    }

    /**
     * Ganador de la partida en base a la alteración de tres booleanos
     * (jugador1Ganador, jugador2Ganador o empateJugadores)
     */
    fun ganadorPartida() {
        if (puntosJug1 <= 21 && puntosJug2 <= 21) {
            when {
                puntosJug1 > puntosJug2 -> jugador1Ganador = true
                puntosJug1 < puntosJug2 -> jugador2Ganador = true
                puntosJug1 == puntosJug2 -> {
                    if (jugador1.value?.mano?.size!! < jugador2.value?.mano?.size!!) {
                        jugador1Ganador = true
                    } else if (jugador1.value?.mano?.size!! > jugador2.value?.mano?.size!!) {
                        jugador2Ganador = true
                    } else {
                        empateJugadores = true
                    }
                }
            }
        } else if (puntosJug1 <= 21) {
            jugador1Ganador = true
        } else if (puntosJug2 <= 21) {
            jugador2Ganador = true
        } else {
            empateJugadores = true
        }
    }

    /**
     * Añade carta a la mano del jugador en cuestión
     *
     * @id identificador del jugador
     */
    fun addCardToHandPlayer(id: Int) {
        val carta = Baraja.dameCarta()
        when (id) {
            1 -> {
                if (!btnPasarJ1IsClicked.value!!) {
                    if (puntosJug1 < 21 && turnoJugador == 1 && !btnPasarJ2IsClicked.value!!) {
                        jugador1.value?.mano?.add(carta.idDrawable)
                        _numCartasJug1.value = jugador1.value?.mano?.size
                        //miramos que valor es para añadirle o bien 1 u 11
                        puntosJug1 += chooseValor(puntosJug1, carta)
                        turnoJugador = 2
                    } else if (puntosJug1 < 21 && turnoJugador == 1 || puntosJug2 >= 21 && puntosJug1 < 21) {
                        jugador1.value?.mano?.add(carta.idDrawable)
                        _numCartasJug1.value = jugador1.value?.mano?.size
                        puntosJug1 += carta.puntosMin //hay que controlar que sea 1 u 11 la carta
                    }
                }
            }

            2 -> {
                if (!btnPasarJ2IsClicked.value!!) {
                    if (puntosJug2 < 21 && turnoJugador == 2 && !btnPasarJ1IsClicked.value!!) {
                        jugador2.value?.mano?.add(carta.idDrawable)
                        _numCartasJug2.value = jugador2.value?.mano?.size
                        //miramos que valor es para añadirle o bien 1 u 11
                        puntosJug2 += chooseValor(puntosJug2, carta)
                        turnoJugador = 1
                    } else if (puntosJug2 < 21 && turnoJugador == 2 || puntosJug1 >= 21 && puntosJug2 < 21) {
                        jugador2.value?.mano?.add(carta.idDrawable)
                        _numCartasJug2.value = jugador2.value?.mano?.size
                        puntosJug2 += carta.puntosMin
                    }
                }
            }
        }

    }

    /**
     * Metodo privado que nos cambia el valor de la carta a sus puntos maximos, siempre que sea
     * AS y la puntuación de la mano lo permita
     *
     * @param puntosJugador puntos del jugador totales
     * @param carta carta pedida
     */
    private fun chooseValor(puntosJugador: Int, carta: Carta): Int {
        var valor = 0
        if (carta.puntosMin == 1) {
            if ((puntosJugador + carta.puntosMax) <= 21) valor = carta.puntosMax
        } else valor = carta.puntosMin
        return valor
    }

    /**
     * Paso de los turnos y manejo del estado de los booleanos
     * btnPasarJ1IsClicked y btnPasarJ2IsClicked
     *
     * @id identificador del jugador
     */
    fun pasarPlayer(id: Int) {
        when {
            id == 1 && turnoJugador == 1 -> {
                btnPasarJ1IsClicked.value = true
                turnoJugador = 2
            }

            id == 2 && turnoJugador == 2 -> {
                btnPasarJ2IsClicked.value = true
                turnoJugador = 1
            }
        }
    }

}