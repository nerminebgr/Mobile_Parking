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
import androidx.compose.foundation.layout.width
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
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
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
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.project.R
import com.example.project.interfaces.DestinationPath


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DisplaySignUP(navController: NavHostController){
    var name by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var pwd by remember { mutableStateOf("") }
    val context = LocalContext.current
    Column (
        modifier = Modifier
            .padding(10.dp)
            .verticalScroll(rememberScrollState()),

        ){

        Column (
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier
                .fillMaxWidth(1f)
                .padding(top = 80.dp)

        ){
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center,
                modifier = Modifier
                    .fillMaxWidth(1f)
            ) {
                Text(
                    textAlign = TextAlign.Center,
                    text = "Sign Up ",
                    letterSpacing = 5.sp,
                    fontWeight = FontWeight.Bold,
                    fontSize = 43.sp,
                    color = Color(0xFF703ED1),

                    )

            }
            Spacer(modifier = Modifier.height(7.dp))
            Column(
                modifier = Modifier
                    .fillMaxWidth(.7f)

            ) {
                Text(
                    textAlign = TextAlign.Center,
                    text = " Fill your information below or register with your google account.",
                    fontWeight = FontWeight.Normal,
                    fontSize = 18.sp,
                    color = Color(0xFFAFB0AF)
                )
            }


        }
        Column(
            modifier = Modifier
                .padding(20.dp)
                .fillMaxWidth(1f)
        ) {
            OutlinedTextField(
                modifier = Modifier.fillMaxWidth(1f),
                value = name,
                onValueChange ={name= it},
                leadingIcon = { Icon(imageVector = Icons.Default.AccountCircle, contentDescription = "accountIcon" ,
                    tint = Color(0xFF703ED1)
                ) },
                label = { Text("Name") },
                placeholder = {
                    Text(text = "Name",
                        color = Color(0xFF703ED1),
                        style = TextStyle(
                            fontSize = 14.sp,
                        )
                    )
                },
            )
            Spacer(modifier = Modifier.height(10.dp))
            OutlinedTextField(
                modifier = Modifier.fillMaxWidth(1f),
                value = email,
                onValueChange ={email= it},
                leadingIcon = { Icon(imageVector = Icons.Default.Email, contentDescription = "emailIcon" ,
                    tint = Color(0xFF703ED1)
                ) },
                label = { Text("Email") },
                placeholder = {
                    Text(text = "Email",
                        color = Color(0xFF703ED1),
                        style = TextStyle(
                            fontSize = 14.sp,
                        )
                    )
                },
                // colors = TextFieldDefaults.textFieldColors(containerColor = Color(0xFFF6F6F6))
            )
            Spacer(modifier = Modifier.height(10.dp))
            OutlinedTextField(
                modifier = Modifier.fillMaxWidth(1f),
                value = pwd,
                onValueChange ={pwd= it},
                leadingIcon = { Icon(imageVector = Icons.Default.Lock, contentDescription = "pwdIcon",tint = Color(0xFF703ED1)) },
                label = { Text("Password") },
                visualTransformation = PasswordVisualTransformation(),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                placeholder = {
                    Text(text = "Password",
                        color = Color(0xFF703ED1),
                        style = TextStyle(
                            fontSize = 14.sp,

                            )
                    )
                },

            )
            Spacer(modifier = Modifier.height(20.dp))
            Column (
                modifier = Modifier
                    .fillMaxWidth(1f),
                verticalArrangement = Arrangement.Center
            ){
                Button( onClick = {
                    if (name.isNotEmpty() && email.isNotEmpty() && pwd.isNotEmpty()) {
                        authmanager.createUser(context, name, email, pwd)
                        navController.navigate(DestinationPath.Reservations.route)
                    } else {

                    }
                },
                    shape = RoundedCornerShape(30.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF773FFF)),
                    modifier = Modifier
                        .fillMaxWidth(1f)
                        .height(50.dp)
                ) {
                    Text(text = "Sign Up",
                        fontSize = 15.sp,
                    )
                }



            }
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(top = 70.dp),
                verticalArrangement = Arrangement.Center
            ){
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center,
                    modifier = Modifier.fillMaxWidth())
                {
                    Divider(
                        color = Color(0xFFAFB0AF),
                        thickness = 1.dp,
                        modifier = Modifier
                            .width(100.dp)
                    )
                    Text(text =" Or Sign UP with ",
                        color= Color(0xFFAFB0AF)
                    )

                    Divider(
                        color = Color(0xFFAFB0AF),
                        thickness = 1.dp,
                        modifier = Modifier
                            .width(100.dp)
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
                            .height(45.dp)
                            .width(45.dp),

                        contentScale = ContentScale.FillWidth
                    )
                }
                Spacer(modifier = Modifier.height(50.dp))
                Row (


                    modifier= Modifier
                        .align(Alignment.CenterHorizontally)

                ){
                    Text(text = "Already have an account? ")
                    Text(text = " Sing In",
                        color = Color(0xFF773FFF),
                        modifier = Modifier
                            .clickable {
                                navController.navigate(DestinationPath.SignIn.route)
                            })
                }


            }



        }


    }


}