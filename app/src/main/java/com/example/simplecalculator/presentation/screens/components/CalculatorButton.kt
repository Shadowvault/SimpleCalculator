package com.example.simplecalculator.presentation.screens.components

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun CalculatorButton(
    number: String,
    onClick : () -> Unit,
    modifier: Modifier,
    buttonColor: Color = Color.White,
    textColor: Color = Color.Black
) {

    TextButton(
        shape = RoundedCornerShape(4.dp),
        onClick = onClick,
        modifier = modifier
            .aspectRatio(6 / 4f)
            .padding(4.dp),
        colors = ButtonDefaults.textButtonColors(backgroundColor = buttonColor)
    ) {
        Text(number, style = TextStyle(fontSize = 24.sp, color = textColor, textAlign = TextAlign.Center),
            modifier = Modifier.align(Alignment.CenterVertically))
    }
}