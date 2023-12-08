package com.example.pairs.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.pairs.R
import com.example.pairs.components.CardsContainer
import com.example.pairs.components.PlayersCard
import com.example.pairs.components.PlayersPointsRow
import com.example.pairs.components.VolumeButton
import com.example.pairs.ui.theme.PairsTheme
import com.example.pairs.viewmodel.ViewModel


@Composable
fun MainScreen(
    viewModel: ViewModel,
) {
    val idCard1State by viewModel.idCard1.collectAsStateWithLifecycle()
    val idCard2State by viewModel.idCard2.collectAsStateWithLifecycle()
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

                Spacer(modifier = Modifier.height(50.dp))
                CardsContainer(
                    viewModel, modifier = Modifier
                        .weight(0.5f)
                )
            }

        }
    }

}


@Preview(showSystemUi = true)
@Composable
fun MainScreenPreview() {
    PairsTheme {

    }
}