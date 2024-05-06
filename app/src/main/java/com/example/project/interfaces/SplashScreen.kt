import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.project.R
import com.example.project.interfaces.DestinationPath

import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun SplashScreen(navController: NavController) {
    val coroutineScope = rememberCoroutineScope()

    // Utilise LaunchedEffect pour naviguer après un délai
    LaunchedEffect(key1 = true) {
        coroutineScope.launch {
            delay(3000) // Attendre 5 secondes
            navController.navigate(DestinationPath.Home.route)
        }
    }

    // Interface SplashScreen avec le logo de votre application
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = painterResource(id = R.drawable.logo),// Remplacez R.drawable.logo par votre propre logo
            contentDescription = "Logo",
            modifier = Modifier.size(200.dp),
            contentScale = ContentScale.Fit
        )
        Spacer(modifier = Modifier.height(16.dp))
        Text(text = "Your no.1 parking assistant", color = Color(0xFF703ED1))
    }
}
