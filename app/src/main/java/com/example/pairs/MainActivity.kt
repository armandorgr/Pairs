package com.example.pairs

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.DrawableRes
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import com.example.pairs.screens.MainScreen
import com.example.pairs.viewmodel.ViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.pairs.ui.theme.PairsTheme


class MainActivity : ComponentActivity() {

    private val jugadores = mutableListOf(
        R.drawable.geralt_profile,
        R.drawable.ciri_profile,
        R.drawable.yennerfer_profile,
        R.drawable.triss_profile,
        R.drawable.dandelion_profile
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            PairsTheme {
                val jugador1Pic = jugadores.random()
                jugadores.removeIf { it == jugador1Pic }
                val jugador2Pic = jugadores.random()
                ScreenSetUp(jugador1Pic, jugador2Pic)
            }

        }
    }
}


@Composable
fun ScreenSetUp(j1: Int, j2: Int, viewModel: ViewModel = viewModel()) {
    MainScreen(j1, j2, viewModel)
}