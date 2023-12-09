package com.example.pairs.screens

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.animation.slideIn
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOut
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shadow
import androidx.compose.ui.layout.ContentScale
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
import com.example.pairs.components.PlayersCard
import com.example.pairs.components.PlayersPointsRow
import com.example.pairs.components.VolumeButton
import com.example.pairs.ui.theme.PairsTheme
import com.example.pairs.ui.theme.gwentFontFamily
import com.example.pairs.viewmodel.ViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


@OptIn(ExperimentalAnimationApi::class)
@Composable
fun MainScreen(
    viewModel: ViewModel,
) {
    val turnoActual by viewModel.turno.collectAsStateWithLifecycle()
    var textVisible by rememberSaveable { mutableStateOf(false) }
    val playersModifier: Modifier = Modifier.size(height = 80.dp, width = 160.dp)
    PairsTheme {
        Box(Modifier.fillMaxSize()) {
            Image(
                painterResource(id = R.drawable.background_1),
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier.fillMaxSize()
            )
            Column(
                Modifier.fillMaxSize()
            ) {
                Spacer(modifier = Modifier.height(30.dp))
                PlayersPointsRow(
                    player1 = {
                        PlayersCard(
                            image = R.drawable.geralt_profile,
                            text = stringResource(id = R.string.jugador, 1, 0),
                            isReversed = true,
                            playersModifier.weight(0.35f)
                        )
                    },
                    player2 = {
                        PlayersCard(
                            image = R.drawable.ciri_profile,
                            text = stringResource(id = R.string.jugador, 2, 0),
                            isReversed = false,
                            playersModifier.weight(0.35f)
                        )
                    },
                    button = {
                        VolumeButton(
                            onClick = { },
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
                    LaunchedEffect(key1 = Unit){
                        launch {
                            delay(100)
                            textVisible=true
                        }
                    }
                    AnimatedVisibility(
                        visible = textVisible,
                        enter = slideInHorizontally() + scaleIn()
                    ) {
                        AnimatedContent(
                            targetState = turnoActual,
                            label = "") {targetTurno ->
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

        }
    }

}


@Preview(showSystemUi = true)
@Composable
fun MainScreenPreview(viewModel: ViewModel = ViewModel()) {
    PairsTheme {
        MainScreen(viewModel = viewModel)
    }
}