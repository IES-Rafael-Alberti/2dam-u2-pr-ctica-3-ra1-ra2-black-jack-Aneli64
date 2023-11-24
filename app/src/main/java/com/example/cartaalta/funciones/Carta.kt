package com.example.cartaalta.funciones

/**
 * Clase donde se almacena nuestra carta
 * @param nombre Numero del naipe
 * @param palo Figura a la que hace referencia
 * @param puntosMin Puntuacion minima que tiene (Solo cambia en caso de AS)
 * @param puntosMax Puntuacion maxima (Solo cambia en caso de AS)
 * @param idDrawable Id que identifica nuestra carta en la baraja
 */
class Carta(var nombre: Naipes, var palo: Palos, var puntosMin: Int = 0,
            var puntosMax: Int = 0, var idDrawable: Int = 0) {
    override fun toString(): String {
        return "Carta(nombre=$nombre, palo=$palo, puntosMin=$puntosMin, puntosMax=$puntosMax, idDrawable=$idDrawable)"
    }

}