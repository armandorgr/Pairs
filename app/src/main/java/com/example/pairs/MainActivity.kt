package com.example.pairs

import android.graphics.Color
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.SystemBarStyle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.LifecycleOwner
import com.example.pairs.screens.MainScreen
import com.example.pairs.viewmodel.ViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.pairs.ui.theme.PairsTheme
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


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
        enableEdgeToEdge(
            statusBarStyle = SystemBarStyle.light(
                Color.TRANSPARENT, Color.TRANSPARENT
            ),
            navigationBarStyle = SystemBarStyle.light(
                Color.TRANSPARENT, Color.TRANSPARENT
            )
        )
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
    val context = LocalContext.current
    val lifeCycleOwner:LifecycleOwner = LocalLifecycleOwner.current
    DisposableEffect(lifeCycleOwner){
        val observer = LifecycleEventObserver{_,event ->
            when(event){
                Lifecycle.Event.ON_PAUSE -> {
                    CoroutineScope(Dispatchers.IO).launch {
                        viewModel.stopMusic()
                    }
                }
                Lifecycle.Event.ON_RESUME -> {
                    CoroutineScope(Dispatchers.IO).launch {
                        viewModel.backgroundMusic(context)
                    }
                }
                else -> {}
            }
        }
        lifeCycleOwner.lifecycle.addObserver(observer)
        onDispose {
            lifeCycleOwner.lifecycle.removeObserver(observer)
        }
    }
    MainScreen(j1, j2, viewModel)
}