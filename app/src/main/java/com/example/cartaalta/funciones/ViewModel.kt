package com.example.cartaalta.funciones

import android.app.Activity
import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData


class ViewModel(app: Application) : AndroidViewModel(app) {
    private val jugador1 = MutableLiveData<Jugador>()
    private val jugador2 = MutableLiveData<Jugador>()

    private val numCartasJug1 = MutableLiveData<Int>()
    val _numCartasJug1: LiveData<Int> = numCartasJug1

    private val numCartasJug2 = MutableLiveData<Int>()
    val _numCartasJug2: LiveData<Int> = numCartasJug2

    var puntosJug1 = 0
    var puntosJug2 = 0

    var btnPedirCartaJ1IsClicked = MutableLiveData<Boolean>()
    var btnPedirCartaJ2IsClicked = MutableLiveData<Boolean>()

    var btnPasarJ1IsClicked = MutableLiveData<Boolean>()
    var btnPasarJ2IsClicked = MutableLiveData<Boolean>()


    init {
        Baraja.crearBaraja()
        Baraja.barajar()

        jugador1.value = Jugador("jugador1", mutableListOf())
        jugador2.value = Jugador("jugador2", mutableListOf())

        btnPedirCartaJ1IsClicked.value = false
        btnPedirCartaJ2IsClicked.value = false

        btnPasarJ1IsClicked.value = false
        btnPasarJ2IsClicked.value = false

    }

    fun getHandPlayer(id: Int): MutableList<Int> {
        return when (id) {
            1 -> jugador1.value?.mano ?: mutableListOf()
            2 -> jugador2.value?.mano ?: mutableListOf()
            else -> throw IllegalArgumentException("ID de jugador no válido: $id")
        }
    }

    fun turnosTerminados(){
        if (btnPasarJ1IsClicked.value == true && btnPasarJ2IsClicked.value == true){
            btnPedirCartaJ1IsClicked.value = true
            btnPedirCartaJ2IsClicked.value = true
            btnPasarJ1IsClicked.value = false
            btnPasarJ2IsClicked.value = false

        }
    }


    fun addCardToHandPlayer(id: Int) {
        val carta = Baraja.dameCarta()
        when (id) {
            1 -> {
                turnosTerminados()
                if (!btnPedirCartaJ1IsClicked.value!! && !btnPasarJ1IsClicked.value!!) {
                    jugador1.value?.mano?.add(carta.idDrawable)
                    numCartasJug1.value = jugador1.value?.mano?.size
                    puntosJug1 += carta.puntosMin //hay que controlar que sea 1 u 11 la carta
                    btnPedirCartaJ1IsClicked.value = true
                    btnPedirCartaJ2IsClicked.value = false
                }
                else if(btnPasarJ2IsClicked.value == true){
                    jugador1.value?.mano?.add(carta.idDrawable)
                    numCartasJug1.value = jugador1.value?.mano?.size
                    puntosJug1 += carta.puntosMin
                }

            }

            2 -> {
                turnosTerminados()
                if (!btnPedirCartaJ2IsClicked.value!! && !btnPasarJ2IsClicked.value!!) {
                    jugador2.value?.mano?.add(carta.idDrawable)
                    numCartasJug2.value = jugador2.value?.mano?.size
                    puntosJug2 += carta.puntosMin
                    btnPedirCartaJ2IsClicked.value = true
                    btnPedirCartaJ1IsClicked.value = false
                }
                else if(btnPasarJ1IsClicked.value == true){
                    jugador2.value?.mano?.add(carta.idDrawable)
                    numCartasJug2.value = jugador2.value?.mano?.size
                    puntosJug2 += carta.puntosMin
                }
            }
        }
    }
}