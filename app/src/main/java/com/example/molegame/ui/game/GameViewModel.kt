package com.example.molegame.ui.game

import android.os.CountDownTimer
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.molegame.core.UiEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.jvm.internal.Intrinsics.Kotlin
import kotlin.random.Random
import kotlin.random.nextInt

@HiltViewModel
class GameViewModel @Inject constructor():ViewModel() {
    var state by mutableStateOf(GameState())
        private set

    private val _uiEvent = Channel<UiEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()

    private val timer = object : CountDownTimer(30000, 1000) {
        override fun onTick(millisUntilFinished: Long) {
            state = state.copy(time = millisUntilFinished)
        }

        override fun onFinish() {
            state = state.copy(time = 0L)
            //game finished
        }
    }
    private var replaceMoleJob: Job? = null

    fun onEvent(event: GameEvent){
        when(event){
            is GameEvent.OnHoleClick ->{
                smashHole(event.id)
            }
        }
    }
    private fun smashHole(position:Int){
        if(state.molePosition==position){
            if(!state.isGameStarted){
                state=state.copy(isGameStarted = true,molePosition = generateNewMolePosition(position))
                timer.start()
            }else{
                state = state.copy(molePosition = generateNewMolePosition(position), score = state.score+1)
            }
            refreshReplaceMoleJob()
        }
    }
    private fun generateNewMolePosition(currentPosition:Int):Int{
        val list = List(9){it}.minus(currentPosition)
        return list[Random.nextInt(range = IntRange(0,7))]
    }

    private fun refreshReplaceMoleJob(){
        replaceMoleJob?.cancel()
        replaceMoleJob = viewModelScope.launch {
            while(state.time>0) {
                delay(800) //around 150 millis for the mole to appear/hide in hole
                state = state.copy(molePosition = generateNewMolePosition(state.molePosition))
            }
        }
    }
}