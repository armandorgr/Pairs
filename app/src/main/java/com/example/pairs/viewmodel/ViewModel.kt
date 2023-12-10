package com.example.pairs.viewmodel

import android.content.Context
import android.media.MediaPlayer
import android.provider.MediaStore.Audio.Media
import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.example.pairs.R
import com.example.pairs.Turno
import com.example.pairs.components.CardData
import com.example.pairs.components.CardFace
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

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
    private var cartasEncontradas = 0

    private var mMediaPlayer:MediaPlayer? = null
    private var sMediaPlayer:MediaPlayer? = null

    private val _juegoTerminado = MutableStateFlow(false)
    var juegoTerminado:StateFlow<Boolean> = _juegoTerminado.asStateFlow()

    private val _puntosJugador1 = MutableStateFlow(0)
    var puntosJugador1:StateFlow<Int> = _puntosJugador1.asStateFlow()

    private val _puntosJugador2 = MutableStateFlow(0)
    var puntosJugador2:StateFlow<Int> = _puntosJugador2.asStateFlow()

    private val _cardsListState = MutableStateFlow(cardsList)
    val cardsListState: StateFlow<List<CardData>> = _cardsListState.asStateFlow()

    private var _idCard1 = MutableStateFlow(CardData())
    val idCard1: StateFlow<CardData> = _idCard1.asStateFlow()

    private val _idCard2 = MutableStateFlow(CardData())
    val idCard2: StateFlow<CardData> = _idCard2.asStateFlow()

    private var _turno = MutableStateFlow(Turno.Jugador1)
    val turno: StateFlow<Turno> = _turno.asStateFlow()

    private var _isMueted = MutableStateFlow(false)
    val isMuted:StateFlow<Boolean> = _isMueted.asStateFlow()


    fun reset(){
        _juegoTerminado.value = false
        _puntosJugador1.value = 0
        _puntosJugador2.value = 0
        cartasEncontradas = 0
        _turno.value = Turno.Jugador1
        _idCard1.value = CardData()
        _idCard2.value = CardData()
        _cardsListState.value.forEach { card ->
            card.found = false
        }
        _cardsListState.value.shuffle()
    }

    fun backgroundMusic(context: Context){
        mMediaPlayer = MediaPlayer.create(context,R.raw.background_music)
        mMediaPlayer?.setVolume(0.5f,0.5f)
        mMediaPlayer?.start()
    }

    fun stopMusic(){
        sMediaPlayer?.release()
        mMediaPlayer?.stop()
        mMediaPlayer?.release()
    }

    fun mute(){
        if(!_isMueted.value){
            mMediaPlayer?.pause()
            _isMueted.value = true
        }else{
            Log.d("PRUEBA", "PLAYBACK")
            mMediaPlayer?.start()
            _isMueted.value = false
        }

    }
    fun makeSound(context: Context, audio:Int){
        if(!_isMueted.value){
            sMediaPlayer =  MediaPlayer.create(context, audio)
            sMediaPlayer?.setVolume(1f,1f)
            sMediaPlayer?.setOnCompletionListener { media->
                media.release()
            }
            sMediaPlayer?.start()
        }

    }

    fun areBothSet(): Boolean {
        return _idCard1.value.id != -1 && _idCard2.value.id != -1
    }

    private fun cambiarTurno() {
        _turno.value = _turno.value.next
    }

    private fun sumarPunto(turno:Turno){
        when(turno){
            Turno.Jugador1 -> _puntosJugador1.value = _puntosJugador1.value+1
            Turno.Jugador2 -> _puntosJugador2.value = _puntosJugador2.value+1
        }
    }

    suspend fun checkContent(context: Context) {
        if (_idCard1.value.text == _idCard2.value.text) {
            sumarPunto(turno.value)
            _idCard1.value.found = true
            _idCard2.value.found = true
            cartasEncontradas++
            if(cartasEncontradas==(_cardsListState.value.size/2)){
                _juegoTerminado.value = true
                makeSound(context, R.raw.won_sound_effect)
            }else{
                makeSound(context, R.raw.good_sound_effect)
                withContext(Dispatchers.Main) {
                    val job1 = async {
                        _idCard1.value.callBack(true)
                    }
                    val job2 = async {
                        _idCard2.value.callBack(true)
                    }
                    job2.await()
                    job1.await()

                }
            }
        } else {
            makeSound(context, R.raw.bad_sound_effect)
            withContext(Dispatchers.Main) {
                val job1 = async {
                    _idCard1.value.callBack(false)
                }
                val job2 = async {
                    _idCard2.value.callBack(false)
                }
                job2.await()
                job1.await()

            }
            cambiarTurno()
        }
        _idCard1.value = CardData()
        _idCard2.value = CardData()
    }

    fun setCard(card: CardData) {
        if (_idCard1.value.id == -1) {
            _idCard1.value = card
        } else if (_idCard2.value.id == -1) {
            _idCard2.value = card
        }

    }

    init {
        cardsList.addAll(cardsDataList)
        cardsList.shuffle()
    }
}