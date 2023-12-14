package com.example.cartaalta.funciones

import android.app.Activity
import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.navigation.NavHostController


class ViewModel(app: Application) : AndroidViewModel(app) {
    private val jugador1 = MutableLiveData<Jugador>()
    private val jugador2 = MutableLiveData<Jugador>()

    private val numCartasJug1 = MutableLiveData<Int>()
    val _numCartasJug1: LiveData<Int> = numCartasJug1

    private val numCartasJug2 = MutableLiveData<Int>()
    val _numCartasJug2: LiveData<Int> = numCartasJug2

    var puntosJug1 = 0
    var puntosJug2 = 0

    private var btnPedirCartaJ1IsClicked = MutableLiveData<Boolean>()
    private var btnPedirCartaJ2IsClicked = MutableLiveData<Boolean>()

    private var btnPasarJ1IsClicked = MutableLiveData<Boolean>()
    private var btnPasarJ2IsClicked = MutableLiveData<Boolean>()

    var boolSalirPartidaJ1 = MutableLiveData<Boolean>()
    var boolSalirPartidaJ2 = MutableLiveData<Boolean>()


    init {
        Baraja.crearBaraja()
        Baraja.barajar()

        jugador1.value = Jugador("jugador1", mutableListOf())
        jugador2.value = Jugador("jugador2", mutableListOf())

        btnPedirCartaJ1IsClicked.value = false
        btnPedirCartaJ2IsClicked.value = false

        btnPasarJ1IsClicked.value = false
        btnPasarJ2IsClicked.value = false

        boolSalirPartidaJ1.value = false
        boolSalirPartidaJ2.value = false


    }

    fun getHandPlayer(id: Int): MutableList<Int> {
        return when (id) {
            1 -> jugador1.value?.mano ?: mutableListOf()
            2 -> jugador2.value?.mano ?: mutableListOf()
            else -> throw IllegalArgumentException("ID de jugador no válido: $id")
        }
    }

    fun addCardToHandPlayer(id: Int) {
        val carta = Baraja.dameCarta()
        when (id) {
            1 -> {
                if (!btnPedirCartaJ1IsClicked.value!! && puntosJug1 < 21 && btnPasarJ1IsClicked.value == false) {
                    jugador1.value?.mano?.add(carta.idDrawable)
                    numCartasJug1.value = jugador1.value?.mano?.size
                    puntosJug1 += carta.puntosMin //hay que controlar que sea 1 u 11 la carta
                    btnPedirCartaJ1IsClicked.value = true
                    btnPedirCartaJ2IsClicked.value = false
                } else if (btnPasarJ2IsClicked.value == true && puntosJug1 < 21) {
                    jugador1.value?.mano?.add(carta.idDrawable)
                    numCartasJug1.value = jugador1.value?.mano?.size
                    puntosJug1 += carta.puntosMin //hay que controlar que sea 1 u 11 la carta
                } else {
                    boolSalirPartidaJ1.value = true
                    pasarPlayer(1)
                }
            }

            2 -> {
                if (!btnPedirCartaJ2IsClicked.value!! && puntosJug2 < 21 && btnPasarJ2IsClicked.value == false) {
                    jugador2.value?.mano?.add(carta.idDrawable)
                    numCartasJug2.value = jugador2.value?.mano?.size
                    puntosJug2 += carta.puntosMin
                    btnPedirCartaJ2IsClicked.value = true
                    btnPedirCartaJ1IsClicked.value = false
                }
                else if(btnPasarJ1IsClicked.value == true && puntosJug2 < 21){
                    jugador2.value?.mano?.add(carta.idDrawable)
                    numCartasJug2.value = jugador2.value?.mano?.size
                    puntosJug2 += carta.puntosMin
                } else {
                    boolSalirPartidaJ2.value = true
                    pasarPlayer(2)
                }
            }
        }
    }

    fun pasarPlayer(id: Int) { //REVISAR MIRAR USAR CONTADORES MEJOR!
        if (id == 1) {
            if (btnPasarJ1IsClicked.value == false || btnPasarJ2IsClicked.value == true) btnPasarJ1IsClicked.value = true
        } else {
            if (btnPasarJ2IsClicked.value == false || btnPasarJ1IsClicked.value == true) btnPasarJ2IsClicked.value = true
        }
    }
}