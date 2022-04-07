package com.example.simplecalculator.presentation.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowForward
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
import com.example.simplecalculator.presentation.screens.components.NumberButton
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
    var fromExpanded by remember { mutableStateOf(false)}
    var fromTextfieldSize by remember { mutableStateOf(Size.Zero)}
    val fromIcon = if (fromExpanded)
        Icons.Filled.KeyboardArrowUp
    else
        Icons.Filled.KeyboardArrowDown


    var toCurr: String by remember { mutableStateOf("USD") }
    var toExpanded by remember { mutableStateOf(false)}
    var toTextfieldSize by remember { mutableStateOf(Size.Zero)}
    val toIcon = if (fromExpanded)
        Icons.Filled.KeyboardArrowUp
    else
        Icons.Filled.KeyboardArrowDown



    val currencyList = listOf<String>("EUR","USD","CHF","SEK","GBP","JPY","AUD","CAD")

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
                Row(horizontalArrangement = Arrangement.SpaceEvenly) {


                    Column(modifier = Modifier.weight(1f)) {
                        TextField(
                            singleLine = true,
                            readOnly = true,
                            value = fromCurr,
                            onValueChange = { fromCurr = it },
                            modifier = Modifier
                                .fillMaxWidth()
                                .onGloballyPositioned { coordinates ->
                                    //This value is used to assign to the DropDown the same width
                                    fromTextfieldSize = coordinates.size.toSize()
                                },
                            trailingIcon = {
                                Icon(fromIcon,"contentDescription",
                                    Modifier.clickable { fromExpanded = !fromExpanded })
                            }
                        )
                        DropdownMenu(
                            expanded = fromExpanded,
                            onDismissRequest = { fromExpanded = false },
                            modifier = Modifier
                                .width(with(LocalDensity.current){fromTextfieldSize.width.toDp()})
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





                    Box(modifier = Modifier
                        .weight(1f)
                        .width(IntrinsicSize.Min), contentAlignment = Alignment.Center) {
                        Icon(Icons.Filled.ArrowForward, "")
                    }


                    Column(modifier = Modifier.weight(1f)) {
                        TextField(
                            singleLine = true,
                            readOnly = true,
                            value = toCurr,
                            onValueChange = { toCurr = it },
                            modifier = Modifier
                                .fillMaxWidth()
                                .onGloballyPositioned { coordinates ->
                                    //This value is used to assign to the DropDown the same width
                                    toTextfieldSize = coordinates.size.toSize()
                                },
                            trailingIcon = {
                                Icon(toIcon,"contentDescription",
                                    Modifier.clickable { toExpanded = !toExpanded })
                            }
                        )
                        DropdownMenu(
                            expanded = toExpanded,
                            onDismissRequest = { toExpanded = false },
                            modifier = Modifier
                                .width(with(LocalDensity.current){toTextfieldSize.width.toDp()})
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




                    TextButton(onClick = {
                        if (textFieldState.matches("-?\\d+(\\.\\d+)?".toRegex())) {
                            viewModel.getConversion(fromCurr, toCurr, textFieldState)
                        }

                    }, modifier = Modifier
                        .weight(1f)
                        .width(IntrinsicSize.Min)) {
                        Text("OK")

                    }
                }
                Text(viewModel.conversion.value)
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
                        NumberButton(
                            modifier = Modifier
                                .weight(1f)
                                .aspectRatio(1f),
                            number = "(",
                            onClick = {
                                if (hasBeenEval) {
                                    hasBeenEval = false
                                }
                                textFieldState += "("
                            })
                        NumberButton(
                            modifier = Modifier
                                .weight(1f)
                                .aspectRatio(1f),
                            number = "7",
                            onClick = {
                                if (hasBeenEval) {
                                    textFieldState = ""
                                    hasBeenEval = false
                                }
                                textFieldState += "7"
                            })
                        NumberButton(
                            modifier = Modifier
                                .weight(1f)
                                .aspectRatio(1f),
                            number = "4",
                            onClick = {
                                if (hasBeenEval) {
                                    textFieldState = ""
                                    hasBeenEval = false
                                }
                                textFieldState += "4"
                            })
                        NumberButton(
                            modifier = Modifier
                                .weight(1f)
                                .aspectRatio(1f),
                            number = "1",
                            onClick = {
                                if (hasBeenEval) {
                                    textFieldState = ""
                                    hasBeenEval = false
                                }
                                textFieldState += "1"
                            })
                        NumberButton(
                            modifier = Modifier
                                .weight(1f)
                                .aspectRatio(1f),
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
                        NumberButton(
                            modifier = Modifier
                                .weight(1f)
                                .aspectRatio(1f),
                            number = ")",
                            onClick = {
                                if (hasBeenEval) {
                                    hasBeenEval = false
                                }
                                textFieldState += ")"
                            })
                        NumberButton(
                            modifier = Modifier
                                .weight(1f)
                                .aspectRatio(1f),
                            number = "8",
                            onClick = {
                                if (hasBeenEval) {
                                    textFieldState = ""
                                    hasBeenEval = false
                                }
                                textFieldState += "8"
                            })
                        NumberButton(
                            modifier = Modifier
                                .weight(1f)
                                .aspectRatio(1f),
                            number = "5",
                            onClick = {
                                if (hasBeenEval) {
                                    textFieldState = ""
                                    hasBeenEval = false
                                }
                                textFieldState += "5"
                            })
                        NumberButton(
                            modifier = Modifier
                                .weight(1f)
                                .aspectRatio(1f),
                            number = "2",
                            onClick = {
                                if (hasBeenEval) {
                                    textFieldState = ""
                                    hasBeenEval = false
                                }
                                textFieldState += "2"
                            })
                        NumberButton(
                            modifier = Modifier
                                .weight(1f)
                                .aspectRatio(1f),
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
                        NumberButton(
                            modifier = Modifier
                                .weight(1f)
                                .aspectRatio(1f),
                            number = "AC",
                            onClick = {
                                if (hasBeenEval) {
                                    textFieldState = ""
                                    hasBeenEval = false
                                }
                                textFieldState = ""
                                textFieldHistoryState = ""
                            })
                        NumberButton(
                            modifier = Modifier
                                .weight(1f)
                                .aspectRatio(1f),
                            number = "9",
                            onClick = {
                                if (hasBeenEval) {
                                    textFieldState = ""
                                    hasBeenEval = false
                                }
                                textFieldState += "9"
                            })
                        NumberButton(
                            modifier = Modifier
                                .weight(1f)
                                .aspectRatio(1f),
                            number = "6",
                            onClick = {
                                if (hasBeenEval) {
                                    textFieldState = ""
                                    hasBeenEval = false
                                }
                                textFieldState += "6"
                            })
                        NumberButton(
                            modifier = Modifier
                                .weight(1f)
                                .aspectRatio(1f),
                            number = "3",
                            onClick = {
                                if (hasBeenEval) {
                                    textFieldState = ""
                                    hasBeenEval = false
                                }
                                textFieldState += "3"
                            })
                        NumberButton(
                            modifier = Modifier
                                .weight(1f)
                                .aspectRatio(1f),
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
                        NumberButton(
                            modifier = Modifier
                                .weight(1f)
                                .aspectRatio(1f),
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
                        NumberButton(
                            modifier = Modifier
                                .weight(1f)
                                .aspectRatio(1f),
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
                        NumberButton(
                            modifier = Modifier
                                .weight(1f)
                                .aspectRatio(1f),
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
                        NumberButton(
                            modifier = Modifier
                                .weight(1f)
                                .aspectRatio(1f),
                            number = "-",
                            onClick = {
                                if (hasBeenEval) {
                                    hasBeenEval = false
                                }
                                textFieldState += "-"
                            })
                        NumberButton(
                            modifier = Modifier
                                .weight(1f)
                                .aspectRatio(1f),
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