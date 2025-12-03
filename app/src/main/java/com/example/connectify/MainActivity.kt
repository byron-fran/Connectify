package com.example.connectify

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.navigation.compose.rememberNavController
import com.example.connectify.presentation.navigation.ContactNavigationSuiteScaffold
import com.example.connectify.presentation.navigation.NavHostManager
import com.example.connectify.ui.theme.ConnectifyTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        installSplashScreen()
        setContent {
            val navHostController = rememberNavController()

            ConnectifyTheme {
                ContactNavigationSuiteScaffold(
                    navHostController
                ) {
                    NavHostManager(navHostController)
                }
            }
        }
    }
}