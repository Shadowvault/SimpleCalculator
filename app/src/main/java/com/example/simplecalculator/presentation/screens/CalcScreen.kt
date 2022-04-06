package com.example.simplecalculator.presentation.screens

import android.util.Log
import android.widget.Space
import android.widget.TextView
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.simplecalculator.common.Resource
import com.example.simplecalculator.domain.model.ConversionWithAmount
import com.example.simplecalculator.presentation.screens.components.NumberButton
import com.github.keelar.exprk.Expressions
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

//@Preview
@Composable
fun CalcScreen(
    viewModel: CalcScreenViewModel = hiltViewModel(),

) {

    var textFieldState by remember {mutableStateOf("")}
    var textFieldHistoryState by remember {mutableStateOf("")}
    var hasBeenEval = false

    Scaffold() {
        Column(modifier = Modifier.fillMaxSize(),
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
                horizontalAlignment = Alignment.End) {
                    ClickableText(AnnotatedString(textFieldHistoryState),
                    style = TextStyle(fontSize = 30.sp),
                    onClick = {
                         textFieldState = Expressions().eval(textFieldHistoryState).toString()
                    })
                    Spacer(modifier = Modifier.height(20.dp))
                    Text(textFieldState, textAlign = TextAlign.End,
                        fontSize = 40.sp)
                }
            }

            Column(){
            Row(modifier = Modifier
                //.height(IntrinsicSize.Min)
                .fillMaxWidth()) {
                Text("asdasd")
                Text("asdasd")
                TextButton(onClick = {
                    //Here i need to Implement the click
                }) {
                    Text("OK")
                }
            }
            Row(
                modifier = Modifier
                    .height(IntrinsicSize.Min)
                    .background(color = Color.LightGray)
                    .fillMaxWidth()
            ) {
                Column(modifier = Modifier
                    .height(IntrinsicSize.Min)
                    .weight(1f)) {
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
                Column(modifier = Modifier
                    .height(IntrinsicSize.Min)
                    .weight(1f)) {
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
                Column(modifier = Modifier
                    .height(IntrinsicSize.Min)
                    .weight(1f)) {
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
                Column(modifier = Modifier
                    .height(IntrinsicSize.Min)
                    .weight(1f)) {
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
                            try {textFieldHistoryState = textFieldState
                                textFieldState = Expressions().eval(textFieldState).toString()
                                hasBeenEval = true
                            } catch (e: Exception) {
                                textFieldHistoryState =""
                            }
                        })

                }
            }
        }
        }
    }

}