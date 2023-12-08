package com.example.pairs.components
import android.util.Log
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
    val id2 by viewModel.idCard2.collectAsStateWithLifecycle()
    LazyVerticalGrid(
        contentPadding = PaddingValues(1.dp),
        columns = GridCells.Adaptive(minSize = 70.dp),
        horizontalArrangement = Arrangement.spacedBy(10.dp),
        verticalArrangement = Arrangement.SpaceAround,
        modifier = Modifier
            .fillMaxSize()
            .then(modifier)
    ) {
        items(cardsListState) { item ->
            Log.d("PRUEBA", "Hay que voltear ${item.hayQueVoltear} nombre: ${item.text}")
            var cardFaceState by rememberSaveable { mutableStateOf(CardFace.Back) }
            FlipCard(
                cardFace =  if (item.hayQueVoltear) CardFace.Back else cardFaceState,
                onClick = {
                    if(!viewModel.areBothSet()){
                        cardFaceState = it.next
                        viewModel.setCard(cardsListState,item.id)
                    }
                },
                front = { ContentCard(cardImage = item.image, text = item.text) },
                back = { BackCard(backImage = R.drawable.card_back_option2) })
            LaunchedEffect(key1 = id2){
                launch {
                    if(viewModel.areBothSet()){
                        delay(1000)
                        viewModel.checkContent(cardsListState)
                    }
                }
            }
        }
    }

}

/*
@Preview(showBackground = true)
@Composable
fun CardsContainerPreview() {
    Column(Modifier.fillMaxSize()) {
        val list: MutableList<@Composable () -> Unit> = mutableListOf()
        for (card in Cards.items) {
            repeat(2) {
                var cardFaceState by remember { mutableStateOf(CardFace.Front) }
                list.add {
                    FlipCard(
                        cardFace = cardFaceState,
                        onClick = { cardFaceState = cardFaceState.next },
                        front = {
                            ContentCard(
                                cardImage = card.image,
                                text = card.text,
                            )
                        },
                        back = {
                            BackCard(
                                backImage = R.drawable.card_back_option2,

                                )
                        },
                        Modifier.weight(1f)
                    )
                }
            }
        }
    }

}

*/