package com.example.molegame.ui.game

import android.content.SharedPreferences
import android.os.CountDownTimer
import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.molegame.core.Constants
import com.example.molegame.core.UiEvent
import com.example.molegame.navigation.Route
import com.orbitalsonic.sonictimer.SonicCountDownTimer
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.random.Random
import kotlin.random.nextInt

@HiltViewModel
class GameViewModel @Inject constructor(
    private val sharedPref: SharedPreferences
) : ViewModel() {
    var state by mutableStateOf(GameState())
        private set

    private val _uiEvent = Channel<UiEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()

    init {
        state = state.copy(
            recordScore = sharedPref.getInt(Constants.savedRecordScore, 0)
        )
    }

    private val timer = object : SonicCountDownTimer(3000, 50) {
        override fun onTimerFinish() {
            state = state.copy(time = 0L)
            if (state.score > state.recordScore) {
                with(sharedPref.edit()) {
                    putInt(Constants.savedRecordScore, state.score)
                    apply()
                }
            }
            viewModelScope.launch {
                _uiEvent.send(UiEvent.Navigate(Route.finish, true))
            }
        }

        override fun onTimerTick(timeRemaining: Long) {
            state = state.copy(time = timeRemaining)
        }
    }
    private var replaceMoleJob: Job? = null

    fun onEvent(event: GameEvent) {
        when (event) {
            is GameEvent.OnHoleClick -> {
                smashHole(event.id)
            }
            is GameEvent.OnPlayNewGame -> {
                clearState()
            }
            is GameEvent.OnPause -> {
                timer.pauseCountDownTimer()
            }
            is GameEvent.OnResume -> {
                timer.resumeCountDownTimer()
            }
            is GameEvent.OnGameCanceled ->{
                clearState()
                timer.cancelCountDownTimer()
            }
        }
    }

    private fun smashHole(position: Int) {//we check if smash was correct in HoleContainer(if we see more, than half of the mole)
        if (!state.isGameStarted) {
            state = state.copy(
                isGameStarted = true,
                molePosition = generateNewMolePosition(position)
            )
            timer.startCountDownTimer()
        } else {
            state = state.copy(
                molePosition = generateNewMolePosition(position),
                score = state.score + 1
            )
        }
        refreshReplaceMoleJob()
    }

    private fun generateNewMolePosition(currentPosition: Int): Int {
        val list = List(9) { it }.minus(currentPosition)
        return list[Random.nextInt(range = IntRange(0, 7))]
    }

    private fun refreshReplaceMoleJob() {
        replaceMoleJob?.cancel()
        replaceMoleJob = viewModelScope.launch {
            while (state.time > 0) {
                delay(800) //around 150 millis for the mole to appear/hide in hole
                state = state.copy(molePosition = generateNewMolePosition(state.molePosition))
            }
        }
    }

    private fun clearState() {
        replaceMoleJob?.cancel()
        state = GameState().copy(
            recordScore = sharedPref.getInt(Constants.savedRecordScore, 0)
        )
    }
}