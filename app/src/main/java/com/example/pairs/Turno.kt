package com.example.pairs


enum class Turno(val mensaje:Int, val jugador:Int) {
    Jugador1(R.string.turno, 1){
        override val next: Turno
            get() = Jugador2
    },
    Jugador2(R.string.turno, 2){
        override val next: Turno
            get() = Jugador1
    };
    abstract val next: Turno
}