package com.example.molegame.ui.finish

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Scaffold
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.molegame.R
import com.example.molegame.navigation.Route
import com.example.molegame.navigation.Route.start
import com.example.molegame.ui.components.ElevatedButton
import com.example.molegame.ui.game.GameEvent
import com.example.molegame.ui.game.GameViewModel
import kotlinx.coroutines.NonDisposableHandle.parent

@Composable
fun FinishScreen(
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

            if(viewModel.state.score>viewModel.state.recordScore){
                Spacer(modifier = Modifier.height(180.dp))
                Surface(
                    modifier = Modifier.wrapContentSize(),
                    border = BorderStroke(1.dp, Color(0xffb4d259)),
                    shape = RoundedCornerShape(15.dp)
                ) {
                    Column(modifier = Modifier.padding(8.dp),horizontalAlignment = Alignment.CenterHorizontally) {
                        Text(
                            text = "New record!",
                            textAlign = TextAlign.Center,
                            fontSize = 22.sp,
                        )
                        Text(
                            text = stringResource(id = R.string.congratulations),
                            fontSize = 16.sp,
                            textAlign = TextAlign.Center,
                        )
                    }

                }
                Spacer(modifier = Modifier.height(24.dp))
            }else{
                Spacer(modifier = Modifier.height(260.dp))
            }
            Text(text = "Record: ${viewModel.state.recordScore}", fontSize = 20.sp)
            Text(text = "Your score: ${viewModel.state.score}", fontSize = 24.sp)
            Spacer(modifier = Modifier.height(16.dp))
            ElevatedButton(
                Modifier
                    .fillMaxWidth(),
                onClick = {
                    viewModel.onEvent(GameEvent.OnPlayNewGame)
                    onNavigate(Route.game, false)
                }
            ) {
                Text(text = "Play again", color = Color.White, fontSize = 18.sp)
            }
            Spacer(modifier = Modifier.height(16.dp))
            ElevatedButton(
                Modifier
                    .fillMaxWidth(),
                onClick = { onNavigate(Route.start, true) }
            ) {
                Text(text = "Menu", color = Color.White, fontSize = 18.sp)
            }
        }
    }
}