package com.example.molegame.ui.start

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Scaffold
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.molegame.navigation.Route
import com.example.molegame.ui.components.ElevatedButton
import com.example.molegame.ui.game.GameEvent
import com.example.molegame.ui.game.GameViewModel
import com.example.molegame.R

@Composable
fun StartScreen(
    viewModel: GameViewModel = hiltViewModel(),
    onNavigateUp: () -> Unit,
    onNavigate: (route: String, popBackStack: Boolean) -> Unit
) {
    Scaffold() {
        Column(
            modifier = Modifier
                .padding(it)
                .fillMaxSize()
                .padding(horizontal = 46.dp), horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(180.dp))
            Surface(
                modifier = Modifier.wrapContentSize(),
                border = BorderStroke(1.dp, Color(0xff7b1fa2)),
                shape = RoundedCornerShape(15.dp)
            ) {
                Text(
                    text = stringResource(id = R.string.description),
                    fontSize = 16.sp,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.padding(8.dp)
                )
            }

            Spacer(modifier = Modifier.height(32.dp))
            Text(text = "Record: ${viewModel.state.recordScore}", fontSize = 20.sp)
            Spacer(modifier = Modifier.height(16.dp))
            ElevatedButton(
                Modifier
                    .fillMaxWidth(),
                onClick = {
                    viewModel.onEvent(GameEvent.OnPlayNewGame)
                    onNavigate(Route.game, false)
                }
            ) {
                Text(text = "Play", color = Color.White, fontSize = 18.sp)
            }
        }
    }
}