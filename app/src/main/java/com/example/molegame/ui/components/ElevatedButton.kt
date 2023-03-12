package com.example.molegame.ui.components

import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.example.molegame.ui.components.extensions.innerShadow

@Preview
@Composable
fun ElevatedButton(
    modifier: Modifier = Modifier,
    onClick:()->Unit = {},
    enabled: Boolean = true,
    contentPadding: PaddingValues = PaddingValues(vertical = 16.dp, horizontal = 20.dp),
    cornerSize: Dp = 20.dp,
    elevation: Dp = 4.dp,
    content: @Composable RowScope.() -> Unit = { Text(text="press", color = Color.Green) }
) {
    val interactionSource = remember { MutableInteractionSource() }
    val pressed by interactionSource.collectIsPressedAsState()

    androidx.compose.material.Button(
        modifier = modifier.let {
            if (pressed || !enabled)
                it.innerShadow(
                    blur = 1.dp,
                    color = Color(0xff000000).copy(alpha = 0.6f),
                    cornersRadius = cornerSize,
                    offsetY = 2.5.dp,
                    // spread = -0.5.dp
                )
            else it
        },
        contentPadding = contentPadding,
        shape = RoundedCornerShape(cornerSize),
        colors = androidx.compose.material.ButtonDefaults.buttonColors(
            backgroundColor = Color(0xffb4d259),
            contentColor = Color.White,
            disabledBackgroundColor = Color.White,
            disabledContentColor = Color.White
        ),
        elevation = androidx.compose.material.ButtonDefaults.elevation(
            defaultElevation = elevation,
            disabledElevation = 0.dp,
            pressedElevation = 0.dp
        ),
        interactionSource = interactionSource,
        onClick = onClick,
        enabled = enabled,
        content = content
    )
}