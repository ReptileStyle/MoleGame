package com.example.molegame.ui.game

data class GameState(
    val molePosition:Int = 4,
    val score:Int = 0,
    val time: Long = 30000,
    val isGameStarted:Boolean = false
)