package com.example.molegame.ui.game

sealed class GameEvent {
    data class OnHoleClick(val id:Int):GameEvent()
}