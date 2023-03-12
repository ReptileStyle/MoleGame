package com.example.molegame.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.molegame.ui.start.StartScreen

@Composable
fun NavGraph(navController: NavHostController) {
    NavHost(
        navController = navController,
        startDestination = Route.start
    ) {
        composable(route = Route.start){
            StartScreen(onNavigateUp = { navController.navigateUp() }, onNavigate = navController::navigate)
        }
        composable(route = Route.game){

        }
        composable(route = Route.result){

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