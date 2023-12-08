package com.example.pairs.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import com.example.pairs.R
import com.example.pairs.Turno
import com.example.pairs.components.CardData
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
class ViewModel : ViewModel() {
    var cardsList = mutableListOf<CardData>()

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
        CardData(it.second, it.first, count, false)
    }

    private val _cardsListState = MutableStateFlow(cardsList)
    val cardsListState:StateFlow<List<CardData>> = _cardsListState.asStateFlow()

    private var _idCard1 = MutableStateFlow(0)
    val idCard1:StateFlow<Int> = _idCard1.asStateFlow()

    private val _idCard2 = MutableStateFlow(0)
    val idCard2:StateFlow<Int> = _idCard2.asStateFlow()

    private var _turno = MutableStateFlow(Turno.Jugador1)
    val turno:StateFlow<Turno> = _turno.asStateFlow()

    fun areBothSet():Boolean{
        return _idCard1.value != 0 && _idCard2.value!=0
    }

    fun checkContent(list:List<CardData>){
        val card1 = list.firstOrNull(){data -> data.id==idCard1.value}
        val card2 = list.firstOrNull(){data -> data.id==idCard2.value}
        if(card1?.text != card2?.text){
            card1?.hayQueVoltear = true
            card2?.hayQueVoltear = true
            Log.d("PRUEBA", "${card1?.id}-${card2?.id}")
        }
        _idCard1.value=0
        _idCard2.value=0
    }

    fun setCard(cardsList:List<CardData>, id:Int){
            if (_idCard1.value==0){
                _idCard1.value=id
            }else if(_idCard2.value==0){
                _idCard2.value=id
                Log.d("PRUEBA","HECHO")
                _cardsListState.value = cardsList.toMutableList()
            }

    }

    init {
        cardsList.addAll(cardsDataList)
        cardsList.shuffle()
    }
}