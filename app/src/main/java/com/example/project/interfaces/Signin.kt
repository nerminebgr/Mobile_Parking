package com.example.project.interfaces


import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.absoluteOffset
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.rounded.ShoppingCart
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext

import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.project.R
import com.example.project.databases.DataClasses.Credentials
import com.example.project.databases.DataClasses.RegisterRequest
import com.example.project.databases.DataClasses.TokenRequest
import com.example.project.interfaces.DestinationPath
import com.example.project.models.UserModel


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DisplaySignIn(navController: NavHostController,userModel: UserModel){
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var errorMessage by remember { mutableStateOf("") }
    val context = LocalContext.current




    Column (
        modifier = Modifier
            .padding(10.dp)
            .verticalScroll(rememberScrollState()),

        ) {

        Column(
            modifier = Modifier
                .fillMaxWidth(.8f)
                .padding(top = 100.dp, start = 20.dp)



        ) {
            Text(
                text = "Sign in",
                letterSpacing = 5.sp,
                fontWeight = FontWeight.ExtraBold,
                fontSize = 45.sp,
                color = Color(0xFF703ED1),
                style = TextStyle(lineHeight = 70.sp),
                onTextLayout = {}
            )

        }
        if (errorMessage.isNotEmpty()) {
            Text(
                text = errorMessage,
                color = Color.Red,
                modifier = Modifier.padding(vertical = 8.dp, horizontal = 16.dp),
                onTextLayout = {}
            )
        }
        Column(
            modifier = Modifier
                .padding(20.dp)
                .fillMaxWidth(1f)
        ) {
            OutlinedTextField(
                modifier = Modifier.fillMaxWidth(1f),
                value = email,
                onValueChange = { email = it },
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Default.Email, contentDescription = "emailIcon",
                        tint = Color(0xFF703ED1)
                    )
                },
                label = { Text(text="Email",
                    onTextLayout = {}) },
                placeholder = {
                    Text(
                        text = "Email",
                        color = Color(0xFF703ED1),
                        style = TextStyle(
                            fontSize = 14.sp,
                        ),
                        onTextLayout = {}
                    )
                },
                colors = TextFieldDefaults
                    .textFieldColors(containerColor = Color(0xFFF6F6F6))
            )
            Spacer(modifier = Modifier.height(10.dp))
            OutlinedTextField(
                modifier = Modifier.fillMaxWidth(1f),
                value = password,
                onValueChange = { password = it },
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Default.Lock,
                        contentDescription = "pwdIcon",
                        tint = Color(0xFF703ED1)
                    )
                },
                label = { Text(text="Password",
                    onTextLayout = {}) },
                visualTransformation = PasswordVisualTransformation(),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                placeholder = {
                    Text(
                        text = "Password",
                        color = Color(0xFF703ED1),
                        style = TextStyle(
                            fontSize = 14.sp,

                            ),
                        onTextLayout = {}
                    )
                },
                colors = TextFieldDefaults
                    .textFieldColors(containerColor = Color(0xFFF6F6F6))

            )
            Spacer(modifier = Modifier.height(20.dp))
            Column(
                modifier = Modifier
                    .fillMaxWidth(1f),
                verticalArrangement = Arrangement.Center
            ) {
                Button(
                    onClick = {
                        if (email.isNotEmpty() && password.isNotEmpty()) {
                            var user = Credentials(email,password)
                            userModel.loginUser(user)
                            userModel.addToken(TokenRequest(email,""))
                            navController.navigate(DestinationPath.Reservations.route) {
                                popUpTo(DestinationPath.Home.route)
                            }
                        } else {
                            errorMessage = "Please enter email and password"
                        }
                    },
                    shape = RoundedCornerShape(30.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF773FFF)),
                    modifier = Modifier
                        .fillMaxWidth(1f)
                        .height(50.dp)
                ) {
                    Text(
                        text = "Sign In",
                        fontSize = 15.sp,
                        onTextLayout = {}
                    )
                }


            }

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(top = 70.dp),
                verticalArrangement = Arrangement.Center
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center,
                    modifier = Modifier.fillMaxWidth()
                )
                {
                    Divider(
                        color = Color(0xFFAFB0AF),
                        thickness = 1.dp,
                        modifier = Modifier
                            .width(100.dp)
                    )
                    Text(
                        text = " Or Sign in with ",
                        color = Color(0xFFAFB0AF),
                        onTextLayout = {}
                    )

                    Divider(
                        color = Color(0xFFAFB0AF),
                        thickness = 1.dp,
                        modifier = Modifier
                            .width(100.dp)
                        //.padding(vertical = 8.dp)
                    )

                }

                Row(
                    horizontalArrangement = Arrangement.SpaceEvenly,

                    modifier = Modifier
                        .padding(top = 30.dp)
                        .fillMaxWidth(1f)
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.img_2),
                        contentDescription = null,
                        modifier = Modifier
                            //.fillMaxWidth()
                            .height(45.dp)
                            .width(45.dp),

                        contentScale = ContentScale.FillWidth
                    )
                }

                Spacer(modifier = Modifier.height(50.dp))
                Row(


                    modifier = Modifier
                        .align(Alignment.CenterHorizontally)

                ) {
                    Text(text = "Don't have an account? ",
                        onTextLayout = {})
                    Text(text = " Sing Up",
                        color = Color(0xFF773FFF),
                        onTextLayout = {},
                        modifier = Modifier
                            .clickable {
                                navController.navigate(DestinationPath.SignUp.route)
                            })
                }


            }


        }
        DisplayToast(userModel)


    }
}


@Composable
fun Loading(userModel: UserModel ){
    if (userModel.loading.value){
        CircularProgressIndicator()
    }
}


@Composable
fun DisplayToast(userModel: UserModel ){
    val context = LocalContext.current
    if (userModel.error.value){
        Toast.makeText(context,"Une erreur s'est produite!", Toast.LENGTH_SHORT).show()
    }

}