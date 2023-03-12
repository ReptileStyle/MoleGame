package com.example.molegame.ui.start

import androidx.compose.foundation.layout.*
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.molegame.navigation.Route
import com.example.molegame.ui.components.ElevatedButton

@Composable
fun StartScreen(
    onNavigateUp: () -> Unit,
    onNavigate: (route: String, popBackStack: Boolean) -> Unit
) {



    Scaffold() {
        Box(modifier = Modifier.padding(it).fillMaxSize(),
        contentAlignment = Alignment.Center){
            ElevatedButton(
                Modifier.fillMaxWidth().padding(horizontal = 46.dp),
                onClick = {onNavigate(Route.game,false)}
            ){
                Text(text="Play", color = Color.White, fontSize = 18.sp)
            }
        }
    }
}