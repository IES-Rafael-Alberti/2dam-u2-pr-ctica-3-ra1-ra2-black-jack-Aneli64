package com.example.cartaalta.funciones

/**
 * Enum de palos de cartas
 */
enum class Palos {
    CORAZONES, DIAMANTES, TREBOLES, PICAS
}

/**
 * Enum de naipes de cartas
 */
enum class Naipes {
    AS, DOS, TRES, CUATRO, CINCO, SEIS, SIETE, OCHO, NUEVE, DIEZ, JOTA, REINA, REY

}
class Baraja {
    //meteremos todos los metodos dentro de nuestro companion object,
    // ya que lo llamaremos estaticamente
    companion object{
     var listaCartas = arrayListOf<Carta>()
        /**
         * Generar la lista de 52 cartas de la baraja
         * Para ello, iteramos tanto en enum naipes, como palo, y vamos generando las cartas
         */
        fun crearBaraja(){
            var idCarta = 1
            for (palo in Palos.values()) {
                for (numero in Naipes.values()) {
                    val newCarta = Carta(numero, palo, numero.ordinal + 1, numero.ordinal + 1, idCarta)
                    //Definimos el doble valor de AS
                    if (numero.name == "AS") newCarta.puntosMax = 11
                    listaCartas.add(newCarta)
                    idCarta++
                }
            }
        }

        /**
         *  Desordenar las cartas de la lista de cartas de la baraja (shuffle)
         */
        fun barajar() = listaCartas.shuffle()

        /**
         * Retorna la última carta de la lista de cartas y la elimina de la baraja.
         */
        fun dameCarta() = listaCartas.removeLast()

    }
}