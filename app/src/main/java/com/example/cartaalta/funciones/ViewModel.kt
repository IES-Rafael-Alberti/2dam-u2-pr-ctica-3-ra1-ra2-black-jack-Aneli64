package com.example.cartaalta.funciones

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData


class ViewModel(app: Application) : AndroidViewModel(app) {
    private val jugador1 = MutableLiveData<Jugador>()
    private val jugador2 = MutableLiveData<Jugador>()

    private val numCartasJug = MutableLiveData<Int>()
    val _numCartasJug: LiveData<Int> = numCartasJug

    var puntosJug1 = 0
    var puntosJug2 = 0

    init {
        jugador1.value = Jugador("jugador1", mutableListOf())
        jugador2.value = Jugador("jugador2", mutableListOf())
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
                jugador1.value?.mano?.add(carta.idDrawable)
                numCartasJug.value = jugador1.value?.mano?.size
                puntosJug1 += carta.puntosMin //hay que controlar que sea 1 u 11 la carta
            }

            2 -> {
                jugador2.value?.mano?.add(carta.idDrawable)
                numCartasJug.value = jugador2.value?.mano?.size
                puntosJug2 += carta.puntosMin
            }
        }
    }
}