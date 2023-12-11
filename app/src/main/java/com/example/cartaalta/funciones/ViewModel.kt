package com.example.cartaalta.funciones

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData

class ViewModel(app: Application) : AndroidViewModel(app) {
    private val jugador1 = MutableLiveData<Jugador>()
    private val jugador2 = MutableLiveData<Jugador>()

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

    fun addCardToHandPlayer(id: Int){
        val carta = Baraja.dameCarta().idDrawable
        //dorsoCarta = "c${carta.idDrawable}"
        when (id) {
            1 -> {
                jugador1.value?.mano?.add(carta)
                println(jugador1.value?.mano)
            }
            2 -> jugador2.value?.mano?.add(carta)
        }
    }

    fun repitaCarta(dorsoCarta:() -> Unit){
    }


}