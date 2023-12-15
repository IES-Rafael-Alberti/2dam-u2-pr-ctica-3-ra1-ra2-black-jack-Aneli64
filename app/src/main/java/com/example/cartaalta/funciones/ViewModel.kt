package com.example.cartaalta.funciones

import android.app.Application
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import java.lang.NumberFormatException

class ViewModel(app: Application) : AndroidViewModel(app) {
    val jugador1 = MutableLiveData<Jugador>()
    val jugador2 = MutableLiveData<Jugador>()

    private val numCartasJug1 = MutableLiveData<Int>()
    val _numCartasJug1: LiveData<Int> = numCartasJug1

    private val numCartasJug2 = MutableLiveData<Int>()
    val _numCartasJug2: LiveData<Int> = numCartasJug2

    var puntosJug1 = 0
    var puntosJug2 = 0

    var apuestaJ1 = 0
    var apuestaJ2 = 0

    private var _btnPasarJ1IsClicked = MutableLiveData<Boolean>()
    var btnPasarJ1IsClicked = _btnPasarJ1IsClicked
    private var _btnPasarJ2IsClicked = MutableLiveData<Boolean>()
    var btnPasarJ2IsClicked = _btnPasarJ2IsClicked

    private var turnoJugador = 1

    var boolSalirPartida = false


    init {
        Baraja.crearBaraja()
        Baraja.barajar()

        jugador1.value = Jugador("jugador1", mutableListOf())
        jugador2.value = Jugador("jugador2", mutableListOf())

        _btnPasarJ1IsClicked.value = false
        _btnPasarJ2IsClicked.value = false

    }

    fun apuestaCorrecta(apuesta: String): Boolean{
        return try {
            apuesta.toInt() in 0..400
        }catch (e:NumberFormatException) {
            false
        }
    }

    fun getHandPlayer(id: Int): MutableList<Int> {
        return when (id) {
            1 -> jugador1.value?.mano ?: mutableListOf()
            2 -> jugador2.value?.mano ?: mutableListOf()
            else -> throw IllegalArgumentException("ID de jugador no válido: $id")
        }
    }

    fun salirPartida(btnPasarJ1: Boolean, btnPasarJ2: Boolean) {
        if ((btnPasarJ1 || puntosJug1 >= 21) && (btnPasarJ2 || puntosJug2 >= 21)) {
            boolSalirPartida = true
        }
    }

    fun ganadorPartida() { //revisar a parte de que no controla el empate
        if (puntosJug1 <= 21) {
            if (puntosJug1 > puntosJug2) {
                println("GANA JUGADOR 1")
            } else if (puntosJug1 == puntosJug2) {
                if (jugador1.value?.mano?.size!! < jugador2.value?.mano?.size!!) {
                    println("GANA JUGADOR 1")
                } else {
                    println("GANA JUGADOR 2")
                }
            } else {
                println("GANA JUGADOR 2")
            }
        }
    }


    fun addCardToHandPlayer(id: Int) {
        val carta = Baraja.dameCarta()
        when (id) {
            1 -> {
                if (!btnPasarJ1IsClicked.value!!) {
                    if (puntosJug1 < 21 && turnoJugador == 1 && !btnPasarJ2IsClicked.value!!) {
                        jugador1.value?.mano?.add(carta.idDrawable)
                        numCartasJug1.value = jugador1.value?.mano?.size
                        //miramos que valor es para añadirle o bien 1 u 11
                        puntosJug1 += chooseValor(puntosJug1, carta)
                        turnoJugador = 2
                    } else if (puntosJug1 < 21 && turnoJugador == 1 || puntosJug2 >= 21 && puntosJug1 < 21) {
                        jugador1.value?.mano?.add(carta.idDrawable)
                        numCartasJug1.value = jugador1.value?.mano?.size
                        puntosJug1 += carta.puntosMin //hay que controlar que sea 1 u 11 la carta
                    }
                }
            }

            2 -> {
                if (!btnPasarJ2IsClicked.value!!) {
                    if (puntosJug2 < 21 && turnoJugador == 2 && !btnPasarJ1IsClicked.value!!) {
                        jugador2.value?.mano?.add(carta.idDrawable)
                        numCartasJug2.value = jugador2.value?.mano?.size
                        //miramos que valor es para añadirle o bien 1 u 11
                        puntosJug2 += chooseValor(puntosJug2, carta)
                        turnoJugador = 1
                    } else if (puntosJug2 < 21 && turnoJugador == 2 || puntosJug1 >= 21 && puntosJug2 < 21) {
                        jugador2.value?.mano?.add(carta.idDrawable)
                        numCartasJug2.value = jugador2.value?.mano?.size
                        puntosJug2 += carta.puntosMin
                    }
                }
            }
        }

    }

    fun chooseValor(puntosJugador: Int, carta: Carta): Int {
        var valor = 0
        if (carta.puntosMin == 1) {
            if ((puntosJugador + carta.puntosMax) <= 21) valor = carta.puntosMax
        } else valor = carta.puntosMin
        return valor
    }

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