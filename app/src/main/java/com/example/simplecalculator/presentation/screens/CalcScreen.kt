package com.example.simplecalculator.presentation.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.unit.toSize
import androidx.hilt.navigation.compose.hiltViewModel
import com.adwi.neumorph.android.MorphButtonRounded
import com.example.simplecalculator.presentation.screens.components.CalculatorButton
import com.github.keelar.exprk.Expressions


@Preview
@Composable
fun CalcScreen(
    viewModel: CalcScreenViewModel = hiltViewModel(),

    ) {

    var textFieldState by remember { mutableStateOf("") }
    var textFieldHistoryState by remember { mutableStateOf("") }
    var hasBeenEval = false


    var fromCurr: String by remember { mutableStateOf("EUR") }
    var fromExpanded by remember { mutableStateOf(false) }
    var fromTextfieldSize by remember { mutableStateOf(Size.Zero) }
    val fromIcon = if (fromExpanded)
        Icons.Filled.KeyboardArrowUp
    else
        Icons.Filled.KeyboardArrowDown


    var toCurr: String by remember { mutableStateOf("USD") }
    var toExpanded by remember { mutableStateOf(false) }
    var toTextfieldSize by remember { mutableStateOf(Size.Zero) }
    val toIcon = if (fromExpanded)
        Icons.Filled.KeyboardArrowUp
    else
        Icons.Filled.KeyboardArrowDown


    val currencyList = listOf<String>("EUR", "USD", "CHF", "SEK", "GBP", "JPY", "AUD", "CAD")


    Surface() {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.SpaceBetween,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(IntrinsicSize.Min)
                    .padding(16.dp),
                contentAlignment = Alignment.BottomEnd
            ) {
                Column(
                    horizontalAlignment = Alignment.End
                ) {
                    ClickableText(
                        AnnotatedString(textFieldHistoryState),
                        style = TextStyle(fontSize = 30.sp),
                        onClick = {
                            textFieldState = Expressions().eval(textFieldHistoryState).toString()
                        })
                    Spacer(modifier = Modifier.height(20.dp))
                    Text(
                        textFieldState, textAlign = TextAlign.End,
                        fontSize = 40.sp
                    )
                }
            }
            Column {
                Row(
                    modifier = Modifier.padding(8.dp),
                    horizontalArrangement = Arrangement.SpaceEvenly,
                    verticalAlignment = Alignment.CenterVertically
                ) {


                    Column(modifier = Modifier.weight(2f)) {
                        OutlinedTextField(
                            label = { Text("From") },
                            singleLine = true,
                            readOnly = true,
                            value = fromCurr,
                            onValueChange = { fromCurr = it },
                            modifier = Modifier
                                .fillMaxWidth()
                                .onGloballyPositioned { coordinates ->
                                    fromTextfieldSize = coordinates.size.toSize()
                                },
                            trailingIcon = {
                                Icon(fromIcon, "contentDescription",
                                    Modifier.clickable { fromExpanded = !fromExpanded })
                            }
                        )
                        DropdownMenu(
                            expanded = fromExpanded,
                            onDismissRequest = { fromExpanded = false },
                            modifier = Modifier
                                .width(with(LocalDensity.current) { fromTextfieldSize.width.toDp() })
                        ) {
                            currencyList.forEach { label ->
                                DropdownMenuItem(onClick = {
                                    fromCurr = label
                                    fromExpanded = !fromExpanded
                                }) {
                                    Text(text = label)
                                }
                            }
                        }
                    }

                    IconButton(
                        onClick = {
                            if (textFieldState.matches("-?\\d+(\\.\\d+)?".toRegex())) {
                                viewModel.getConversion(fromCurr, toCurr, textFieldState)
                            }

                        }, modifier = Modifier
                            .weight(1f)
                            .width(IntrinsicSize.Min)
                    ) {
                        Icon(Icons.Filled.Check, "")
                    }


                    Column(modifier = Modifier.weight(2f)) {
                        OutlinedTextField(
                            label = { Text("To") },
                            singleLine = true,
                            readOnly = true,
                            value = toCurr,
                            onValueChange = { toCurr = it },
                            modifier = Modifier
                                .fillMaxWidth()
                                .onGloballyPositioned { coordinates ->
                                    toTextfieldSize = coordinates.size.toSize()
                                },
                            trailingIcon = {
                                Icon(toIcon, "contentDescription",
                                    Modifier.clickable { toExpanded = !toExpanded })
                            }
                        )
                        DropdownMenu(
                            expanded = toExpanded,
                            onDismissRequest = { toExpanded = false },
                            modifier = Modifier
                                .width(with(LocalDensity.current) { toTextfieldSize.width.toDp() })
                        ) {
                            currencyList.forEach { label ->
                                DropdownMenuItem(onClick = {
                                    toCurr = label
                                    toExpanded = !toExpanded
                                }) {
                                    Text(text = label)
                                }
                            }
                        }
                    }
                }
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp, 16.dp),
                    horizontalArrangement = Arrangement.Start,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text("Conversion:" + viewModel.conversion.value)
                }

                Row(
                    modifier = Modifier
                        .height(IntrinsicSize.Min)
                        .fillMaxWidth()
                ) {
                    Column(
                        modifier = Modifier
                            .height(IntrinsicSize.Min)
                            .weight(1f)
                    ) {
                        CalculatorButton(
                            modifier = Modifier.weight(1f),
                            number = "(",
                            onClick = {
                                if (hasBeenEval) {
                                    hasBeenEval = false
                                }
                                textFieldState += "("
                            })
                        CalculatorButton(
                            modifier = Modifier.weight(1f),
                            number = "7",
                            onClick = {
                                if (hasBeenEval) {
                                    textFieldState = ""
                                    hasBeenEval = false
                                }
                                textFieldState += "7"
                            })
                        CalculatorButton(
                            modifier = Modifier.weight(1f),
                            number = "4",
                            onClick = {
                                if (hasBeenEval) {
                                    textFieldState = ""
                                    hasBeenEval = false
                                }
                                textFieldState += "4"
                            })
                        CalculatorButton(
                            modifier = Modifier.weight(1f),
                            number = "1",
                            onClick = {
                                if (hasBeenEval) {
                                    textFieldState = ""
                                    hasBeenEval = false
                                }
                                textFieldState += "1"
                            })
                        CalculatorButton(
                            modifier = Modifier.weight(1f),
                            number = ".",
                            onClick = {
                                if (textFieldState.isNotEmpty() && textFieldState.last().toString()
                                    !in arrayOf("+", "-", "/", "*", ".")
                                ) {
                                    if (hasBeenEval) {
                                        textFieldState = ""
                                        hasBeenEval = false
                                    } else {
                                        textFieldState += "."
                                    }
                                }
                            })

                    }
                    Column(
                        modifier = Modifier
                            .height(IntrinsicSize.Min)
                            .weight(1f)
                    ) {
                        CalculatorButton(
                            modifier = Modifier.weight(1f),
                            number = ")",
                            onClick = {
                                if (hasBeenEval) {
                                    hasBeenEval = false
                                }
                                textFieldState += ")"
                            })
                        CalculatorButton(
                            modifier = Modifier.weight(1f),
                            number = "8",
                            onClick = {
                                if (hasBeenEval) {
                                    textFieldState = ""
                                    hasBeenEval = false
                                }
                                textFieldState += "8"
                            })
                        CalculatorButton(
                            modifier = Modifier.weight(1f),
                            number = "5",
                            onClick = {
                                if (hasBeenEval) {
                                    textFieldState = ""
                                    hasBeenEval = false
                                }
                                textFieldState += "5"
                            })
                        CalculatorButton(
                            modifier = Modifier.weight(1f),
                            number = "2",
                            onClick = {
                                if (hasBeenEval) {
                                    textFieldState = ""
                                    hasBeenEval = false
                                }
                                textFieldState += "2"
                            })
                        CalculatorButton(
                            modifier = Modifier.weight(1f),
                            number = "0",
                            onClick = {
                                if (hasBeenEval) {
                                    textFieldState = ""
                                    hasBeenEval = false
                                }
                                textFieldState += "0"
                            })

                    }
                    Column(
                        modifier = Modifier
                            .height(IntrinsicSize.Min)
                            .weight(1f)
                    ) {
                        CalculatorButton(
                            modifier = Modifier.weight(1f),
                            number = "AC",
                            onClick = {
                                if (hasBeenEval) {
                                    textFieldState = ""
                                    hasBeenEval = false
                                }
                                textFieldState = ""
                                textFieldHistoryState = ""
                            })
                        CalculatorButton(
                            modifier = Modifier.weight(1f),
                            number = "9",
                            onClick = {
                                if (hasBeenEval) {
                                    textFieldState = ""
                                    hasBeenEval = false
                                }
                                textFieldState += "9"
                            })
                        CalculatorButton(
                            modifier = Modifier.weight(1f),
                            number = "6",
                            onClick = {
                                if (hasBeenEval) {
                                    textFieldState = ""
                                    hasBeenEval = false
                                }
                                textFieldState += "6"
                            })
                        CalculatorButton(
                            modifier = Modifier.weight(1f),
                            number = "3",
                            onClick = {
                                if (hasBeenEval) {
                                    textFieldState = ""
                                    hasBeenEval = false
                                }
                                textFieldState += "3"
                            })
                        CalculatorButton(
                            modifier = Modifier.weight(1f),
                            number = "Del",
                            onClick = {
                                if (hasBeenEval) {
                                    textFieldState = ""
                                    hasBeenEval = false
                                }
                                textFieldState = textFieldState.dropLast(1)
                            })

                    }
                    Column(
                        modifier = Modifier
                            .height(IntrinsicSize.Min)
                            .weight(1f)
                    ) {
                        CalculatorButton(
                            modifier = Modifier.weight(1f),
                            number = "/",
                            onClick = {
                                if (textFieldState.isNotEmpty() && textFieldState.last().toString()
                                    !in arrayOf("+", "-", "/", "*", ".")
                                ) {
                                    if (hasBeenEval) {
                                        hasBeenEval = false
                                    }
                                    textFieldState += "/"

                                }
                            })
                        CalculatorButton(
                            modifier = Modifier.weight(1f),
                            number = "*",
                            onClick = {
                                if (textFieldState.isNotEmpty() && textFieldState.last().toString()
                                    !in arrayOf("+", "-", "/", "*", ".")
                                ) {
                                    if (hasBeenEval) {
                                        hasBeenEval = false
                                    }
                                    textFieldState += "*"
                                }
                            })
                        CalculatorButton(
                            modifier = Modifier.weight(1f),
                            number = "+",
                            onClick = {
                                if (textFieldState.isNotEmpty() && textFieldState.last().toString()
                                    !in arrayOf("+", "-", "/", "*", ".")
                                ) {
                                    if (hasBeenEval) {
                                        hasBeenEval = false
                                    }
                                    textFieldState += "+"

                                }
                            })
                        CalculatorButton(
                            modifier = Modifier.weight(1f),
                            number = "-",
                            onClick = {
                                if (hasBeenEval) {
                                    hasBeenEval = false
                                }
                                textFieldState += "-"
                            })
                        CalculatorButton(
                            modifier = Modifier.weight(1f),
                            number = "=",
                            onClick = {
                                try {
                                    textFieldHistoryState = textFieldState
                                    textFieldState = Expressions().eval(textFieldState).toString()
                                    hasBeenEval = true
                                } catch (e: Exception) {
                                    textFieldHistoryState = ""
                                }
                            })
                    }
                }
            }
        }
    }
}