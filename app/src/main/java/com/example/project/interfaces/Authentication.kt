package com.example.project.interfaces

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController

@Composable
fun auth(navController: NavHostController){
    var username = remember { mutableStateOf("") }
    var password = remember { mutableStateOf("") }

    Column(modifier = Modifier
        //.background(Color(0xFFE9E9E9))
        .padding(5.dp)
        .fillMaxSize() // Ensure the column takes up the full available space
        ) {
        Row(horizontalArrangement = Arrangement.Center){
            Text(
                text = "Sign In To Continue",
                fontWeight = FontWeight.Bold,
                fontSize = 30.sp,
                color = Color(0xFF767676)
            )
        }
        Column(modifier = Modifier.padding(20.dp)) {
            Column() {
                Row {
                    Text(text = "Email ID*")
                }
                TextField(
                    value = username.value,
                    onValueChange = { username.value = it },
                    modifier = Modifier.fillMaxWidth(1f).padding(10.dp),
                    textStyle = TextStyle(color = Color.Black),
                    colors = TextFieldDefaults.colors(unfocusedTextColor = Color(0xFFFFFFFF))
                )
            }
            Column {
                Row(horizontalArrangement = Arrangement.SpaceBetween) {
                    Text(text = "Password*", fontSize = 12.sp)
                    Text(text = "Forgot password?")
                }
                TextField(
                    value = password.value,
                    onValueChange = { password.value = it },
                    modifier = Modifier.fillMaxWidth(1f).padding(10.dp),
                    textStyle = TextStyle(color = Color.Black),
                    colors = TextFieldDefaults.colors(unfocusedTextColor = Color(0xFFFFFFFF))
                )
                Button(onClick = { /*TODO*/ },
                    modifier = Modifier.padding(8.dp, 30.dp).fillMaxWidth(0.5f),
                    shape = RoundedCornerShape(8),
                    contentPadding = PaddingValues(15.dp, 18.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFD6D8D7))
                    ) {

                    Text(
                        text = ("Sign in"),
                        color = Color.Black,
                        fontWeight = FontWeight.Bold,
                        fontSize = 13.sp
                    )

                }

                }
            }

        }

    }

