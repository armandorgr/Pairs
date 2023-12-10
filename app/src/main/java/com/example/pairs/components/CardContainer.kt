package com.example.pairs.components
import android.content.Context
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
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.layout.positionInParent
import androidx.compose.ui.layout.positionInRoot
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.pairs.R
import com.example.pairs.viewmodel.ViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

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
        horizontalArrangement = Arrangement.spacedBy(10.dp),
        verticalArrangement = Arrangement.SpaceAround,
        modifier = Modifier
            .fillMaxSize()
            .then(modifier)
    ) {
        items(cardsListState) { item ->
            val cardFaceState = rememberSaveable { mutableStateOf(CardFace.Back) }
            val play = rememberSaveable { mutableStateOf(false) }
            if(juegoTerminado) {
                cardFaceState.value = CardFace.Back
                play.value=false
            }
            FlipCard(
                cardFace =  cardFaceState.value,
                onClick = {
                          if (!viewModel.areBothSet() && !item.found){
                              cardFaceState.value = it.next
                              viewModel.makeSound(contex, R.raw.flip_sound_effect)
                              item.callBack = {correct ->
                                  if(!correct) {
                                      play.value = true
                                      delay(700)
                                      cardFaceState.value = CardFace.Back
                                      play.value=false
                                  }else{

                                  }}
                              viewModel.setCard(item)
                          }
                },
                front = { ContentCard(cardImage = item.image, text = item.text) },
                back = { BackCard(backImage = R.drawable.card_back_option2) },
                play.value)
        }
    }
    LaunchedEffect(key1 = id2){
        launch {
            if(viewModel.areBothSet()){
                viewModel.checkContent(contex)
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