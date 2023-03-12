package com.example.molegame.ui.game

sealed class GameEvent {
    data class OnHoleClick(val id:Int):GameEvent()
    object OnPlayNewGame:GameEvent()

    object OnPause:GameEvent()
    object OnResume:GameEvent()
    object OnGameCanceled:GameEvent()
}