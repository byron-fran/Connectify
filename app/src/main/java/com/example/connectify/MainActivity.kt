package com.example.connectify

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
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
        setContent {
        val navController = rememberNavController()
            ConnectifyTheme {
                ContactNavigationSuiteScaffold(
                    onNavigateTo = {
                        navController.navigate(it)
                    }
                ) {
                   NavHostManager(navController)
                }
            }
        }
    }
}