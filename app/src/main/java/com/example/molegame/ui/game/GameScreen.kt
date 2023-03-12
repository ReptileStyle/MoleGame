package com.example.molegame.ui.game

import android.util.Log
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.spring
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material.Scaffold
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.geometry.toRect
import androidx.compose.ui.graphics.*
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.*
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.Lifecycle
import com.example.molegame.R
import com.example.molegame.core.UiEvent
import com.example.molegame.ui.components.OnLifecycleEvent
import com.example.molegame.ui.components.ProgressIndicator

@Composable
fun GameScreen(
    viewModel: GameViewModel = hiltViewModel(),
    onNavigateUp: () -> Unit,
    onNavigate: (route: String, popBackStack: Boolean) -> Unit
) {
    LaunchedEffect(key1 = true) {
        viewModel.uiEvent.collect {
            when (it) {
                is UiEvent.NavigateUp -> onNavigateUp()
                is UiEvent.Navigate -> onNavigate(it.route, it.popBackStack)
                is UiEvent.Message -> {}
            }
        }
    }
    OnLifecycleEvent { owner, event ->
        // do stuff on event
        when (event) {
            Lifecycle.Event.ON_PAUSE -> {
                viewModel.onEvent(GameEvent.OnPause)
            }
            Lifecycle.Event.ON_RESUME -> {
                if (viewModel.state.time != 30000L && viewModel.state.time != 0L)
                    viewModel.onEvent(GameEvent.OnResume)
            }
            else -> { /* other stuff */
            }
        }
    }
    DisposableEffect(key1 = true){
        onDispose {
            if(viewModel.state.time!=0L)
                viewModel.onEvent(GameEvent.OnGameCanceled)
        }
    }
    val image = ImageBitmap.imageResource(id = R.drawable.grass)
    val brush = remember(image) {
        ShaderBrush(ImageShader(image, TileMode.Repeated, TileMode.Repeated))
    }
    Scaffold(
    ) {
        Column(
            Modifier
                .padding(it)
                .background(brush)
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {

            Spacer(modifier = Modifier.height(88.dp))
            Text(text = "Score: ${viewModel.state.score}", fontSize = 24.sp)
            Spacer(modifier = Modifier.height(12.dp))
            ProgressIndicator(progress = viewModel.state.time.toFloat() / 30000)
            Spacer(modifier = Modifier.height(100.dp))
            LazyVerticalGrid(
                columns = GridCells.Fixed(3),
                verticalArrangement = Arrangement.spacedBy(16.dp),
                horizontalArrangement = Arrangement.spacedBy(16.dp),
                userScrollEnabled = false
            ) {
                for (i in 0..8) {
                    item {
                        HoleContainer(
                            i == viewModel.state.molePosition,
                            onClick = { viewModel.onEvent(GameEvent.OnHoleClick(i)) })
                    }
                }
            }
        }
    }
}

@Preview
@Composable
fun HoleContainer(
    isMoleHere: Boolean = true,
    onClick: () -> Unit = {}
) {
    var isPunched by remember {
        mutableStateOf(false)
    }
    LaunchedEffect(key1 = isMoleHere) {
        if (isMoleHere) isPunched = false
    }
    class RectangleShapeToClip(private val heightPart: Float) : Shape {
        override fun createOutline(
            size: Size,
            layoutDirection: LayoutDirection,
            density: Density
        ): Outline = Outline.Generic(Path().apply {
            addRect(
                Rect(
                    Offset(x = 0f, y = -30f),
                    Size(size.width, size.height * heightPart + 30f)
                )
            )
        })
    }

    val moleState by animateFloatAsState(
        targetValue = if (isMoleHere) 0f else 56f,
        animationSpec = spring(
            dampingRatio = 0.63f,
            stiffness = Spring.StiffnessMediumLow
        )
    )
    ConstraintLayout(
        modifier = Modifier
            .height(80.dp)
            .width(50.dp)
            .clickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = null,
            ) {
                if (moleState < 25f) {//we see half of the mole
                    onClick()
                    isPunched = true
                }
            },
    ) {
        val (hole, mole) = createRefs()
        Surface(
            shape = remember {
                OvalCornerShape()
            } ,
            color = Color(0xff303030),
            modifier = Modifier
                .width(50.dp)
                .height(30.dp)
                .constrainAs(hole) {
                    bottom.linkTo(parent.bottom)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                }
        ) {

        }
        Image(
            painter = painterResource(id = if (isPunched) R.drawable.punched_mole else R.drawable.mole),
            modifier = Modifier
                .width(50.dp)
                .height(56.dp)
                .constrainAs(mole) {
                    bottom.linkTo(parent.bottom, 10.dp)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                }
                .clip(
                    remember {
                        RectangleShapeToClip(1f)
                    }
                )
                .offset(y = moleState.dp),
            contentScale = ContentScale.FillWidth,
            contentDescription = null,
        )
    }


}

class OvalCornerShape(

) : Shape {

    override fun createOutline(
        size: Size,
        layoutDirection: LayoutDirection,
        density: Density
    ): Outline {
        val rect = size.toRect()
        val path = Path().apply {
            addOval(rect)

        }
        return Outline.Generic(path)
    }
}
