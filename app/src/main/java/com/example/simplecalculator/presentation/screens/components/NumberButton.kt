package com.example.simplecalculator.presentation.screens.components

import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.sp

@Composable
fun NumberButton(
    number: String,
    onClick : () -> Unit,
    modifier: Modifier? = null
) {
    TextButton(
        shape = RoundedCornerShape(0),
        onClick = onClick,
        modifier = modifier?: Modifier
            .aspectRatio(1f)
    ) {
        Text(number, fontSize = 30.sp)
    }
}