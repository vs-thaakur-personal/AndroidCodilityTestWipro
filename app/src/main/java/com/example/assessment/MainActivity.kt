package com.example.assessment

import android.content.SharedPreferences
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.lifecycle.ReportFragment.Companion.reportFragment
import androidx.lifecycle.lifecycleScope
import com.example.assessment.navigation.AppNavHost
import com.example.auth.ui.KEY_TOKEN
import com.example.theme.AssessmentTheme
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @Inject
    lateinit var sharedPreferences: SharedPreferences
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val splashScreen = installSplashScreen()

//        splashScreen.setKeepOnScreenCondition{true}
//        lifecycleScope.launch(Dispatchers.Main) {
//            delay(2000)
//            splashScreen.setKeepOnScreenCondition{false}
//        }

        val isLoggedIn = sharedPreferences.getString(KEY_TOKEN, null).isNullOrEmpty().not()
        setContent {
            AssessmentTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    AppNavHost(isLoggedIn = isLoggedIn)
                }
            }
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    AssessmentTheme(false) {
        Greeting("Android")
    }
}