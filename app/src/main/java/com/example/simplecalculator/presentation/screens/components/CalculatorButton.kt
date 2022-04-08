package com.example.simplecalculator.presentation.screens.components

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun CalculatorButton(
    number: String,
    onClick : () -> Unit,
    modifier: Modifier
) {

    TextButton(
        shape = RoundedCornerShape(8),
        onClick = onClick,
        modifier = modifier.aspectRatio(6/4f).padding(8.dp)
    ) {
        Text(number, fontSize = 20.sp)
    }
}