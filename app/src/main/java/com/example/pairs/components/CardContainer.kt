package com.example.pairs.components

import android.content.Context
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.slideInVertically
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.pairs.R
import com.example.pairs.viewmodel.ViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun CardsContainer(
    viewModel: ViewModel,
    modifier: Modifier = Modifier
) {
    val cardsListState by viewModel.cardsListState.collectAsStateWithLifecycle()
    val juegoTerminado by viewModel.juegoTerminado.collectAsStateWithLifecycle()
    val id2 by viewModel.idCard2.collectAsStateWithLifecycle()
    val contex: Context = LocalContext.current
    LazyVerticalGrid(
        contentPadding = PaddingValues(1.dp),
        columns = GridCells.Fixed(4),
        horizontalArrangement = Arrangement.spacedBy(20.dp),
        verticalArrangement = Arrangement.SpaceAround,
        modifier = Modifier
            .fillMaxSize()
            .then(modifier)
    ) {
        items(cardsListState) { item ->
            val cardFaceState = rememberSaveable { mutableStateOf(CardFace.Back) }
            val play = rememberSaveable { mutableStateOf(false) }
            val matched = rememberSaveable { mutableStateOf(false) }
            var isCardVisible by rememberSaveable { mutableStateOf(false) }
            if (juegoTerminado) {
                cardFaceState.value = CardFace.Back
                play.value = false
            }
            item.callBack = { correct ->
                var card: CardFace?
                var isGood = false
                if (!correct) {
                    card = CardFace.Back
                } else {
                    isGood = true
                    card = CardFace.Front
                }
                matched.value = isGood
                play.value = true
                delay(800)
                cardFaceState.value = card
                play.value = false
            }
            AnimatedVisibility(
                visible = isCardVisible,
                enter = fadeIn(animationSpec = tween(delayMillis = (item.id * 100)))+ slideInVertically(animationSpec = tween(delayMillis = (item.id * 100)))
            ) {
                FlipCard(
                    cardFace = cardFaceState.value,
                    onClick = {
                        if (!viewModel.areBothSet() && !item.found && !viewModel.isSelectedCard(item)) {
                            cardFaceState.value = it.next
                            viewModel.makeSound(contex, R.raw.flip_sound_effect)
                            viewModel.setCard(item)
                        }
                    },
                    front = { ContentCard(cardImage = item.image, text = item.text) },
                    back = { BackCard(backImage = R.drawable.card_back_option2) },
                    play.value,
                    matched.value
                )
            }
            LaunchedEffect(key1 = Unit) {
                isCardVisible = true
            }

        }
    }
    LaunchedEffect(key1 = id2) {
        launch {
            if (viewModel.areBothSet()) {
                viewModel.checkContent(contex)
            }
        }
    }
}