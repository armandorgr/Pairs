package com.example.pairs.screens

import android.content.Context
import android.util.Log
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.animate
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.animation.slideIn
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOut
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.blur
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shadow
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.pairs.R
import com.example.pairs.components.CardsContainer
import com.example.pairs.components.FinalCard
import com.example.pairs.components.PlayersCard
import com.example.pairs.components.PlayersPointsRow
import com.example.pairs.components.VolumeButton
import com.example.pairs.dataclasses.Ganador
import com.example.pairs.ui.theme.PairsTheme
import com.example.pairs.ui.theme.gwentFontFamily
import com.example.pairs.viewmodel.ViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.system.exitProcess


@OptIn(ExperimentalAnimationApi::class)
@Composable
fun MainScreen(
    picj1: Int,
    picj2: Int,
    viewModel: ViewModel) {
    val juegoState by viewModel.juegoTerminado.collectAsStateWithLifecycle()
    val turnoActual by viewModel.turno.collectAsStateWithLifecycle()
    val puntos1 by viewModel.puntosJugador1.collectAsStateWithLifecycle()
    val puntos2 by viewModel.puntosJugador2.collectAsStateWithLifecycle()
    val isMuted by viewModel.isMuted.collectAsStateWithLifecycle()

    var textVisible by rememberSaveable { mutableStateOf(false) }
    val alpha = 0.7f
    val playersModifier: Modifier = Modifier.size(height = 80.dp, width = 160.dp)
    PairsTheme {
        Box(
            Modifier
                .fillMaxSize()
        ) {
            Image(
                painterResource(id = R.drawable.background_1),
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier.fillMaxSize()
            )
            Column(
                Modifier
                    .fillMaxSize()
                    .statusBarsPadding()
                    .navigationBarsPadding()
            ) {
                PlayersPointsRow(
                    player1 = {
                        PlayersCard(
                            image = picj1,
                            text = stringResource(id = R.string.jugador, 1, puntos1),
                            isReversed = true,
                            playersModifier.weight(0.35f)
                        )
                    },
                    player2 = {
                        PlayersCard(
                            image = picj2,
                            text = stringResource(id = R.string.jugador, 2, puntos2),
                            isReversed = false,
                            playersModifier.weight(0.35f)
                        )
                    },
                    button = {
                        VolumeButton(
                            isMuted,
                            onClick = { viewModel.mute()},
                            modifier = Modifier.weight(0.1f)
                        )
                    },
                    Modifier
                        .weight(0.2f, fill = false)
                )
                CardsContainer(
                    viewModel, modifier = Modifier
                        .weight(0.8f)
                )
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center,
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(0.1f)
                ) {
                    LaunchedEffect(key1 = Unit) {
                        launch {
                            delay(100)
                            textVisible = true
                        }
                    }
                    AnimatedVisibility(
                        visible = textVisible,
                        enter = slideInHorizontally() + scaleIn()
                    ) {
                        AnimatedContent(
                            targetState = turnoActual,
                            label = ""
                        ) { targetTurno ->
                            Text(
                                stringResource(id = targetTurno.mensaje, targetTurno.jugador),
                                fontSize = 28.sp,
                                color = MaterialTheme.colorScheme.onPrimary,
                                fontFamily = gwentFontFamily,
                                textAlign = TextAlign.Center,
                                style = LocalTextStyle.current.plus(
                                    TextStyle(
                                        color = Color.Black,
                                        shadow = Shadow(Color.Black, Offset(6f, 6f))
                                    )
                                ),
                            )
                        }

                    }

                }
            }
            AnimatedVisibility(
                visible = juegoState,
                enter = fadeIn(),
                exit = fadeOut()
            ) {
                Box(
                    Modifier
                        .fillMaxSize()
                        .background(Color.Black.copy(alpha = alpha))
                )
            }
            AnimatedVisibility(visible = juegoState) {
                val pic = if (puntos1 > puntos2) picj1 else picj2
                val puntos = if (puntos1 > puntos2) puntos1 else puntos2
                val id = if (puntos1 > puntos2) 1 else 2
                FinalCard(
                    ganador = Ganador(pic, puntos, id),
                    onSalir = { exitProcess(0) },
                    onVolverJugar = {
                        viewModel.reset()
                    })
            }

        }
    }

}


@Preview(showSystemUi = true)
@Composable
fun MainScreenPreview(viewModel: ViewModel = ViewModel()) {
    PairsTheme {
        MainScreen(
            viewModel = viewModel,
            picj1 = R.drawable.geralt_profile,
            picj2 = R.drawable.ciri_profile
        )
    }
}
