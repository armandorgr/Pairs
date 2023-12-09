package com.example.pairs.viewmodel

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.example.pairs.R
import com.example.pairs.Turno
import com.example.pairs.components.CardData
import com.example.pairs.components.CardFace
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow


data class SelectedCard(
    var id: Int = 0,
    var cardState: MutableState<CardFace> = mutableStateOf(CardFace.Back)
)

class ViewModel : ViewModel() {
    private var cardsList = mutableListOf<CardData>()

    private var count = 0
    private val cardsDataList = listOf(
        R.drawable.geralt_card to R.string.geralt,
        R.drawable.ciri_card to R.string.ciri,
        R.drawable.yennefer_card to R.string.yennefer,
        R.drawable.vesemir_card to R.string.vesemir,
        R.drawable.eredin_card to R.string.eredin,
        R.drawable.dandelion_card to R.string.dandelion,
        R.drawable.geralt_card to R.string.geralt,
        R.drawable.ciri_card to R.string.ciri,
        R.drawable.yennefer_card to R.string.yennefer,
        R.drawable.vesemir_card to R.string.vesemir,
        R.drawable.eredin_card to R.string.eredin,
        R.drawable.dandelion_card to R.string.dandelion,
    ).map {
        count++
        CardData(it.second, it.first, count)
    }

    private val _puntosJugador1 = MutableStateFlow(0)
    var puntosJugador1:StateFlow<Int> = _puntosJugador1.asStateFlow()

    private val _puntosJugador2 = MutableStateFlow(0)
    var puntosJugador2:StateFlow<Int> = _puntosJugador2.asStateFlow()

    private val _cardsListState = MutableStateFlow(cardsList)
    val cardsListState: StateFlow<List<CardData>> = _cardsListState.asStateFlow()

    private var _idCard1 = MutableStateFlow(SelectedCard())
    val idCard1: StateFlow<SelectedCard> = _idCard1.asStateFlow()

    private val _idCard2 = MutableStateFlow(SelectedCard())
    val idCard2: StateFlow<SelectedCard> = _idCard2.asStateFlow()

    private var _turno = MutableStateFlow(Turno.Jugador1)
    val turno: StateFlow<Turno> = _turno.asStateFlow()

    fun areBothSet(): Boolean {
        return _idCard1.value.id != 0 && _idCard2.value.id != 0
    }

    fun cambiarTurno() {
        _turno.value = _turno.value.next
    }

    fun checkContent() {
        val card1 = _cardsListState.value.firstOrNull() { data -> data.id == idCard1.value.id }
        val card2 = _cardsListState.value.firstOrNull() { data -> data.id == idCard2.value.id }
        if (card1?.text != card2?.text) {
            _idCard1.value.cardState.value = CardFace.Back
            _idCard2.value.cardState.value = CardFace.Back
            Log.d("PRUEBA", "${card1?.id}-${card2?.id}")
            cambiarTurno()
        } else {
            card1?.found = true
            card2?.found = true
        }
        _idCard1.value.id = 0
        _idCard2.value.id = 0
    }

    fun setCard(card: SelectedCard) {
        if (_idCard1.value.id == 0) {
            _idCard1.value = card
        } else if (_idCard2.value.id == 0) {
            _idCard2.value = card
            Log.d("PRUEBA", "HECHO")
        }

    }

    init {
        cardsList.addAll(cardsDataList)
        cardsList.shuffle()
    }
}