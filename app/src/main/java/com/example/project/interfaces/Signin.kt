package com.example.project.interfaces


import android.content.Intent
import android.util.Log
import android.widget.Toast
import androidx.activity.compose.ManagedActivityResultLauncher
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
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
import androidx.compose.foundation.layout.size
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
import androidx.compose.material3.ElevatedButton
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
import androidx.compose.runtime.rememberCoroutineScope
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
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.em
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.project.R
import com.example.project.databases.DataClasses.Credentials
import com.example.project.databases.DataClasses.RegisterRequest
import com.example.project.databases.DataClasses.TokenRequest
import com.example.project.databases.entities.User
import com.example.project.interfaces.DestinationPath
import com.example.project.models.UserModel
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.firebase.Firebase
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.auth.auth
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DisplaySignIn(navController: NavHostController, userModel: UserModel) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var errorMessage by remember { mutableStateOf("") }
    var user by remember { mutableStateOf(Firebase.auth.currentUser) }
    val launcher = rememberFirebaseAuthLauncher(
        onAuthComplete = { authResult, exists ->
            val gmail = authResult.user!!.email!!
            user = authResult.user
            val firstName = user?.displayName?.split(" ")?.getOrNull(0) ?: ""
            val lastName = user?.displayName?.split(" ")?.getOrNull(1) ?: ""

            if (!exists) {
                var pwd = generateRandomPassword()
                val newUser = RegisterRequest(gmail, pwd, firstName, lastName)
                userModel.registerUser(newUser)
                val userCredentials = Credentials(gmail, pwd)
                userModel.loginUser(userCredentials)
                userModel.addToken(TokenRequest(gmail, ""))
                Log.v("logged ","new")
                navController.navigate(DestinationPath.Reservations.route) {
                    popUpTo(DestinationPath.Home.route)
                }
            } else {
                userModel.getUserByEmail(gmail)

                Log.v("logged ","not new")
                navController.navigate(DestinationPath.Reservations.route) {
                    popUpTo(DestinationPath.Home.route)
                }
            }

            // Rediriger vers la page des réservations

        },
        onAuthError = {
            user = null
        },
        userModel = userModel
    )
    val token = stringResource(id = R.string.client_id)
    val context = LocalContext.current

    Column(
        modifier = Modifier
            .padding(10.dp)
            .verticalScroll(rememberScrollState())
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
                style = TextStyle(lineHeight = 70.sp)
            )
        }
        if (errorMessage.isNotEmpty()) {
            Text(
                text = errorMessage,
                color = Color.Red,
                modifier = Modifier.padding(vertical = 8.dp, horizontal = 16.dp)
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
                label = { Text("Email") },
                placeholder = {
                    Text(
                        text = "Email",
                        color = Color(0xFF703ED1),
                        style = TextStyle(
                            fontSize = 14.sp,
                        )
                    )
                },
                colors = TextFieldDefaults.textFieldColors(containerColor = Color(0xFFF6F6F6))
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
                label = { Text("Password") },
                visualTransformation = PasswordVisualTransformation(),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                placeholder = {
                    Text(
                        text = "Password",
                        color = Color(0xFF703ED1),
                        style = TextStyle(
                            fontSize = 14.sp,
                        )
                    )
                },
                colors = TextFieldDefaults.textFieldColors(containerColor = Color(0xFFF6F6F6))
            )
            Spacer(modifier = Modifier.height(20.dp))
            Column(
                modifier = Modifier.fillMaxWidth(1f),
                verticalArrangement = Arrangement.Center
            ) {
                Button(
                    onClick = {
                        if (email.isNotEmpty() && password.isNotEmpty()) {
                            val userCredentials = Credentials(email, password)
                            userModel.loginUser(userCredentials)
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
                    )
                }
            }
            Row(
                modifier = Modifier.align(Alignment.CenterHorizontally)
            ) {
                Text(text = "Don't have an account? ")
                Text(
                    text = "Sign Up",
                    color = Color(0xFF773FFF),
                    modifier = Modifier.clickable {
                        navController.navigate(DestinationPath.SignUp.route)
                    }
                )
            }
            Column(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                if (user == null) {
                    Spacer(modifier = Modifier.height(35.dp))
                    ElevatedButton(
                        onClick = {
                            val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                                .requestIdToken(token)
                                .requestEmail()
                                .build()
                            val googleSignInClient = GoogleSignIn.getClient(context, gso)
                            launcher.launch(googleSignInClient.signInIntent)
                        },
                        shape = RoundedCornerShape(15.dp),
                        modifier = Modifier
                            .height(50.dp)
                            .fillMaxSize()
                            .padding(5.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = Color.White, contentColor = Color.Black)
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.google_logo),
                            contentDescription = "",
                            modifier = Modifier.size(45.dp),
                            contentScale = ContentScale.Fit
                        )
                        Spacer(modifier = Modifier.width(10.dp))
                        Text(
                            text = "Sign in via Google",
                            fontFamily = FontFamily.SansSerif,
                            fontWeight = FontWeight.ExtraBold,
                            fontSize = 15.sp,
                            letterSpacing = 0.1.em
                        )
                    }
                } else {
                    Text(
                        text = "Hi, ${user!!.displayName}!",
                        fontFamily = FontFamily.SansSerif,
                        fontWeight = FontWeight.ExtraBold,
                        fontSize = 14.sp,
                        color = Color.Black ,
                        modifier = Modifier.clickable {
                            navController.navigate(DestinationPath.SignUp.route)
                        }
                    )
                    /*navController.navigate(DestinationPath.Reservations.route) {
                        popUpTo(DestinationPath.Home.route)
                    }*/
                    Spacer(modifier = Modifier.height(30.dp))
                    Button(
                        onClick = {
                            Firebase.auth.signOut()
                            user = null
                        },
                        shape = RoundedCornerShape(15.dp),
                        modifier = Modifier
                            .height(50.dp)
                            .fillMaxSize()
                            .padding(5.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = Color.White, contentColor = Color.Black)
                    ) {
                        Text(
                            text = "Log out",
                            fontFamily = FontFamily.SansSerif,
                            fontWeight = FontWeight.ExtraBold,
                            fontSize = 15.sp,
                            letterSpacing = 0.1.em
                        )
                    }
                }
            }
        }
        DisplayToast(userModel)
    }
}

/*
@Composable
fun rememberFirebaseAuthLauncher(
    onAuthComplete: (AuthResult, Boolean) -> Unit,
    onAuthError: (ApiException) -> Unit,
    userModel: UserModel,
    navController: NavHostController
): ManagedActivityResultLauncher<Intent, ActivityResult> {
    val scope = rememberCoroutineScope()
    return rememberLauncherForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        val task = GoogleSignIn.getSignedInAccountFromIntent(result.data)
        Log.v("GoogleAuth", "task $task")
        try {
            val account = task.getResult(ApiException::class.java)!!
            Log.v("GoogleAuth", "account $account")
            val credential = GoogleAuthProvider.getCredential(account.idToken!!, null)
            scope.launch {
                val authResult = Firebase.auth.signInWithCredential(credential).await()
                val gmail = authResult.user!!.email!!
                val isNewUser = userModel.checkEmail(gmail)
                onAuthComplete(authResult, isNewUser)

                val user = authResult.user
                val firstName = user?.displayName?.split(" ")?.getOrNull(0) ?: ""
                val lastName = user?.displayName?.split(" ")?.getOrNull(1) ?: ""

                if (isNewUser) {
                    var pwd = generateRandomPassword()
                    val newUser = RegisterRequest(gmail, pwd, firstName, lastName)
                    userModel.registerUser(newUser)
                    val userCredentials = Credentials(gmail, pwd)
                    userModel.loginUser(userCredentials)

                    userModel.addToken(TokenRequest(gmail,""))
                    navController.navigate(DestinationPath.Reservations.route) {
                        popUpTo(DestinationPath.Home.route)
                    }

                } else {
                    userModel.getUserByEmail(gmail)

                    navController.navigate(DestinationPath.Reservations.route) {
                        popUpTo(DestinationPath.Home.route)
                    }
                }

                // Rediriger vers la page des réservations

            }
        } catch (e: ApiException) {
            Log.v("GoogleAuth", e.toString())
            onAuthError(e)
        }
    }
}*/
@Composable
fun rememberFirebaseAuthLauncher(
    onAuthComplete: (AuthResult, Boolean) -> Unit,
    onAuthError: (ApiException) -> Unit,
    userModel: UserModel
): ManagedActivityResultLauncher<Intent, ActivityResult> {
    val scope = rememberCoroutineScope()
    return rememberLauncherForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        val task = GoogleSignIn.getSignedInAccountFromIntent(result.data)
        Log.v("GoogleAuth", "task $task")
        try {
            val account = task.getResult(ApiException::class.java)!!
            Log.v("GoogleAuth", "account $account")
            val credential = GoogleAuthProvider.getCredential(account.idToken!!, null)
            scope.launch {
                val authResult = Firebase.auth.signInWithCredential(credential).await()
                val gmail = authResult.user!!.email!!
                val exists = userModel.checkEmail(gmail)
                Log.v("exists",exists.toString())
                onAuthComplete(authResult, exists)
            }
        } catch (e: ApiException) {
            Log.v("GoogleAuth", e.toString())
            onAuthError(e)
        }
    }
}

@Composable
fun Loading(userModel: UserModel) {
    if (userModel.loading.value) {
        CircularProgressIndicator()
    }
}

fun generateRandomPassword(length: Int = 10): String {
    val allowedChars = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789"
    return (1..length)
        .map { allowedChars.random() }
        .joinToString("")
}

@Composable
fun DisplayToast(userModel: UserModel) {
    val context = LocalContext.current
    if (userModel.error.value) {
        Toast.makeText(context, "Une erreur s'est produite!", Toast.LENGTH_SHORT).show()
    }
}
