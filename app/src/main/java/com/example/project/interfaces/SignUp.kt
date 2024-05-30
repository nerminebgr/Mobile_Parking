package com.example.project.interfaces
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.project.R
import com.example.project.databases.DataClasses.RegisterRequest
import com.example.project.models.UserModel


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DisplaySignUP(navController: NavHostController,userModel: UserModel){
    var firstname by remember { mutableStateOf("") }
    var lastname by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var pwd by remember { mutableStateOf("") }
    var confirm_pwd by remember { mutableStateOf("") }
    val context = LocalContext.current


    Column (
        modifier = Modifier
            .padding(10.dp)
            .verticalScroll(rememberScrollState())
            .padding(bottom = 80.dp)

        ){


        Column (
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier
                .fillMaxWidth(1f)
                .padding(top = 30.dp, bottom = 10.dp)

        ){
            Image(
                painter = painterResource(id = R.drawable.logo),// Remplacez R.drawable.logo par votre propre logo
                contentDescription = "Logo",
                modifier = Modifier.size(150.dp),
                contentScale = ContentScale.Fit
            )


        }
        Column(
            modifier = Modifier
                .padding(20.dp)
                .fillMaxWidth(1f)
        ) {
            OutlinedTextField(
                modifier = Modifier.fillMaxWidth(1f),
                value = firstname,
                onValueChange ={firstname= it},
                leadingIcon = { Icon(imageVector = Icons.Default.AccountCircle, contentDescription = "accountIcon" ,
                    tint = Color(0xFF9695A8)
                ) },
                label = { Text(text="Firstname",
                    onTextLayout = {}) },
                placeholder = {
                    Text(text = "Firstname",
                        color = Color(0xFF9695A8),
                        style = TextStyle(
                            fontSize = 14.sp,
                        ),
                        onTextLayout = {}
                    )
                },
            )
            Spacer(modifier = Modifier.height(20.dp))
            OutlinedTextField(
                modifier = Modifier.fillMaxWidth(1f),
                value = lastname,
                onValueChange ={lastname= it},
                leadingIcon = { Icon(imageVector = Icons.Default.AccountCircle, contentDescription = "accountIcon" ,
                    tint = Color(0xFF9695A8)
                ) },
                label = { Text(text="Lastname",
                    onTextLayout = {}) },
                placeholder = {
                    Text(text = "Lastname",
                        color = Color(0xFF9695A8),
                        style = TextStyle(
                            fontSize = 14.sp,
                        ),
                        onTextLayout = {}
                    )
                },
            )
            Spacer(modifier = Modifier.height(20.dp))
            OutlinedTextField(
                modifier = Modifier.fillMaxWidth(1f),
                value = email,
                onValueChange ={email= it},
                leadingIcon = { Icon(imageVector = Icons.Default.Email, contentDescription = "emailIcon" ,
                    tint = Color(0xFF9695A8)
                ) },
                label = { Text(text="Email",
                    onTextLayout = {}) },
                placeholder = {
                    Text(text = "Email",
                        color = Color(0xFF9695A8),
                        style = TextStyle(
                            fontSize = 14.sp,
                        ),
                        onTextLayout = {}
                    )
                },
                // colors = TextFieldDefaults.textFieldColors(containerColor = Color(0xFFF6F6F6))
            )
            Spacer(modifier = Modifier.height(20.dp))
            OutlinedTextField(
                modifier = Modifier.fillMaxWidth(1f),
                value = pwd,
                onValueChange ={pwd= it},
                leadingIcon = { Icon(imageVector = Icons.Default.Lock, contentDescription = "pwdIcon",tint = Color(0xFF9695A8)) },
                label = { Text(text="Password",
                    onTextLayout = {}) },
                visualTransformation = PasswordVisualTransformation(),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                placeholder = {
                    Text(text = "Password",
                        color = Color(0xFF9695A8),
                        style = TextStyle(
                            fontSize = 14.sp,),
                        onTextLayout = {}
                    )
                },

            )
            Spacer(modifier = Modifier.height(20.dp))
            OutlinedTextField(
                modifier = Modifier.fillMaxWidth(1f),
                value = confirm_pwd,
                onValueChange ={confirm_pwd= it},
                leadingIcon = { Icon(imageVector = Icons.Default.Lock, contentDescription = "pwdIcon",tint = Color(0xFF9695A8)) },
                label = { Text(text="Confirm Password",
                    onTextLayout = {}) },
                visualTransformation = PasswordVisualTransformation(),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                placeholder = {
                    Text(text = "Confirm Password",
                        color = Color(0xFF9695A8),
                        style = TextStyle(
                            fontSize = 14.sp,
                            ),
                        onTextLayout = {}
                    )
                },

                )
            Spacer(modifier = Modifier.height(20.dp))
            Column (
                modifier = Modifier
                    .fillMaxWidth(1f)
                    .padding(top = 40.dp),
                verticalArrangement = Arrangement.Center
            ){
                Button( onClick = {
                    if (firstname.isNotEmpty() && lastname.isNotEmpty() && email.isNotEmpty() && pwd.isNotEmpty() && pwd == confirm_pwd) {
                        val trimemail = email.trimEnd() // Trim trailing spaces from password

                        val user = RegisterRequest(trimemail, pwd, firstname, lastname)
                        userModel.registerUser(user)
                        navController.navigate(DestinationPath.SignIn.route)
                    } else {

                    }
                },
                    shape = RoundedCornerShape(30.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF703ED1)),
                    modifier = Modifier
                        .fillMaxWidth(1f)
                        .height(50.dp)
                ) {
                    Text(text = "Sign Up",
                        fontSize = 15.sp,
                        onTextLayout = {}
                    )
                }



            }
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(top = 20.dp),
                verticalArrangement = Arrangement.Center
            ){
                Row (


                    modifier= Modifier
                        .align(Alignment.CenterHorizontally)

                ){

                    Text(
                        text = buildAnnotatedString {
                            withStyle(
                                style = SpanStyle(
                                    color = Color(0xFF8498B4),
                                    textDecoration = TextDecoration.Underline
                                )
                            ) {
                                append("Already have an account? Sign In")
                            }
                        },
                        modifier = Modifier.clickable {
                            navController.navigate(DestinationPath.SignIn.route)
                        }
                    )
                }
            }



        }


    }


}