package com.example.molegame.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.LinearProgressIndicator
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import com.example.molegame.R

@Preview
@Composable
fun ProgressIndicator(
    modifier: Modifier = Modifier,
    progress: Float = 0.0f
) {
    ConstraintLayout(modifier) {
        val (bar, container,test) = createRefs()
        Image(
            painter = painterResource(id = R.drawable.energycontainer),
            contentDescription = null,
            modifier = Modifier.constrainAs(container){
                end.linkTo(parent.end)
                start.linkTo(parent.start)
                top.linkTo(parent.top)
                bottom.linkTo(parent.bottom)
            }
        )
        Image(
            painter = painterResource(id = R.drawable.energybar),
            contentDescription = null,
            modifier = Modifier.constrainAs(bar){
                start.linkTo(container.start,42.dp)
                top.linkTo(container.top)
                bottom.linkTo(container.bottom)
            }.clip(
                RoundedCornerShape(50)
            ).offset(-(painterResource(id = R.drawable.energybar).intrinsicSize.width * (1-progress) / 2.75).dp)
        )
    }
}