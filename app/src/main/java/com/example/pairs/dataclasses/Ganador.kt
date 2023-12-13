package com.example.pairs.dataclasses

import androidx.annotation.DrawableRes

data class Ganador(
    @DrawableRes val picture: Int,
    val points: Int,
    val jugadorId: Int
)
