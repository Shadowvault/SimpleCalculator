package com.example.simplecalculator.presentation.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.unit.toSize
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.simplecalculator.presentation.screens.components.CalculatorButton


@Preview
@Composable
fun CalcScreen(
    viewModel: CalcScreenViewModel = hiltViewModel(),

    ) {

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


    Surface {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.SpaceBetween,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    //.height(IntrinsicSize.Min)
                    .padding(16.dp),
                contentAlignment = Alignment.BottomEnd
            ) {
                Column(
                    horizontalAlignment = Alignment.End
                ) {
                    ClickableText(
                        AnnotatedString(text = viewModel.historyTextFlow.value),
                        style = TextStyle(fontSize = 24.sp),
                        maxLines =1,
                        onClick = {
                            viewModel.appendHistory()
                        })
                    Spacer(modifier = Modifier.height(42.dp))
                    Text(
                        viewModel.mainTextState.value, textAlign = TextAlign.End,
                        fontSize = 40.sp,
                        maxLines = 1,
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
                            if (viewModel.mainTextState.value.matches("-?\\d+(\\.\\d+)?".toRegex())) {
                                viewModel.getConversion(
                                    fromCurr,
                                    toCurr,
                                    viewModel.mainTextState.value
                                )
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
                    modifier = Modifier.fillMaxWidth().padding(8.dp, 16.dp),
                    horizontalArrangement = Arrangement.Start,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        "Conversion: " + viewModel.conversion.value,
                        style = TextStyle(fontSize = 24.sp, fontWeight = FontWeight.Normal)
                    )
                }

                Row(modifier = Modifier
                    .height(IntrinsicSize.Min)
                    .fillMaxWidth()
                    .padding(2.dp),
                    ) {
                    Column(
                        modifier = Modifier
                            .height(IntrinsicSize.Min)
                            .weight(1f),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        CalculatorButton(
                            modifier = Modifier.weight(1f),
                            number = "(",
                            onClick = {
                                viewModel.appendButtonChar("(")
                            }, buttonColor = Color(242, 242, 244), textColor = Color.DarkGray)
                        CalculatorButton(
                            modifier = Modifier.weight(1f),
                            number = "7",
                            onClick = {
                                viewModel.appendButtonChar("7")
                            }, buttonColor = Color(242, 242, 244), textColor = Color.DarkGray)
                        CalculatorButton(
                            modifier = Modifier.weight(1f),
                            number = "4",
                            onClick = {
                                viewModel.appendButtonChar("4")
                            }, buttonColor = Color(242, 242, 244), textColor = Color.DarkGray)
                        CalculatorButton(
                            modifier = Modifier.weight(1f),
                            number = "1",
                            onClick = {
                                viewModel.appendButtonChar("1")
                            }, buttonColor = Color(242, 242, 244), textColor = Color.DarkGray)
                        CalculatorButton(
                            modifier = Modifier.weight(1f),
                            number = ".",
                            onClick = {
                                viewModel.appendButtonChar(".")
                            }, buttonColor = Color(242, 242, 244), textColor = Color.DarkGray)
                    }
                    Column(
                        modifier = Modifier
                            .height(IntrinsicSize.Min)
                            .weight(1f),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        CalculatorButton(
                            modifier = Modifier.weight(1f),
                            number = ")",
                            onClick = {
                                viewModel.appendButtonChar(")")
                            }, buttonColor = Color(242, 242, 244), textColor = Color.DarkGray)
                        CalculatorButton(
                            modifier = Modifier.weight(1f),
                            number = "8",
                            onClick = {
                                viewModel.appendButtonChar("8")
                            }, buttonColor = Color(242, 242, 244), textColor = Color.DarkGray)
                        CalculatorButton(
                            modifier = Modifier.weight(1f),
                            number = "5",
                            onClick = {
                                viewModel.appendButtonChar("5")
                            }, buttonColor = Color(242, 242, 244), textColor = Color.DarkGray)
                        CalculatorButton(
                            modifier = Modifier.weight(1f),
                            number = "2",
                            onClick = {
                                viewModel.appendButtonChar("2")
                            }, buttonColor = Color(242, 242, 244), textColor = Color.DarkGray)
                        CalculatorButton(
                            modifier = Modifier.weight(1f),
                            number = "0",
                            onClick = {
                                viewModel.appendButtonChar("0")
                            }, buttonColor = Color(242, 242, 244), textColor = Color.DarkGray)
                    }
                    Column(
                        modifier = Modifier
                            .height(IntrinsicSize.Min)
                            .weight(1f),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        CalculatorButton(
                            modifier = Modifier.weight(1f),
                            number = "AC",
                            onClick = {
                                viewModel.clearTexts()
                            }, buttonColor = Color(242, 242, 244), textColor = Color.DarkGray)
                        CalculatorButton(
                            modifier = Modifier.weight(1f),
                            number = "9",
                            onClick = {
                                viewModel.appendButtonChar("9")
                            }, buttonColor = Color(242, 242, 244), textColor = Color.DarkGray)
                        CalculatorButton(
                            modifier = Modifier.weight(1f),
                            number = "6",
                            onClick = {
                                viewModel.appendButtonChar("6")
                            }, buttonColor = Color(242, 242, 244), textColor = Color.DarkGray)
                        CalculatorButton(
                            modifier = Modifier.weight(1f),
                            number = "3",
                            onClick = {
                                viewModel.appendButtonChar("3")
                            }, buttonColor = Color(242, 242, 244), textColor = Color.DarkGray)
                        CalculatorButton(
                            modifier = Modifier.weight(1f),
                            number = "Del",
                            onClick = {
                                viewModel.appendButtonChar("Del")
                            }, buttonColor = Color(242, 242, 244), textColor = Color.DarkGray)
                    }
                    Column(
                        modifier = Modifier
                            .height(IntrinsicSize.Min)
                            .weight(1f),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        CalculatorButton(
                            modifier = Modifier.weight(1f),
                            number = "/",
                            onClick = {
                                viewModel.appendButtonChar("/")
                            }, buttonColor = Color(239, 239, 239), textColor = Color.DarkGray)
                        CalculatorButton(
                            modifier = Modifier.weight(1f),
                            number = "*",
                            onClick = {
                                viewModel.appendButtonChar("*")
                            }, buttonColor = Color(239, 239, 239), textColor = Color.DarkGray)
                        CalculatorButton(
                            modifier = Modifier.weight(1f),
                            number = "+",
                            onClick = {
                                viewModel.appendButtonChar("+")
                            }, buttonColor = Color(239, 239, 239), textColor = Color.DarkGray)
                        CalculatorButton(
                            modifier = Modifier.weight(1f),
                            number = "-",
                            onClick = {
                                viewModel.appendButtonChar("-")
                            }, buttonColor = Color(239, 239, 239), textColor = Color.DarkGray)
                        CalculatorButton(
                            modifier = Modifier.weight(1f),
                            number = "=",
                            onClick = {
                                viewModel.calculateResult()
                            }, buttonColor = Color(239, 239, 239), textColor = Color.DarkGray)
                    }
                }
            }
        }
    }
}