package com.example.molegame.navigation

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.molegame.ui.finish.FinishScreen
import com.example.molegame.ui.game.GameScreen
import com.example.molegame.ui.game.GameViewModel
import com.example.molegame.ui.start.StartScreen

@Composable
fun NavGraph(
    navController: NavHostController,
    viewModel: GameViewModel = hiltViewModel(),
) {
    NavHost(
        navController = navController,
        startDestination = Route.start
    ) {
        composable(route = Route.start){
            StartScreen(onNavigateUp = { navController.navigateUp() }, onNavigate = navController::navigate)
        }
        composable(route = Route.game){
            GameScreen(onNavigateUp = { navController.navigateUp() }, onNavigate = navController::navigate, viewModel = viewModel)
        }
        composable(route = Route.finish){
            FinishScreen(onNavigateUp = { navController.navigateUp() }, onNavigate = navController::navigate, viewModel = viewModel)
        }
    }
}

fun NavHostController.navigate(
    route: String,
    popBackStack: Boolean = false
) {
    navigate(route) {
        if (popBackStack) {
            popUpTo(graph.id) {
                inclusive = true
            }
        }
    }
}