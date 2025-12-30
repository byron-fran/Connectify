package com.example.connectify

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.connectify.presentation.navigation.ConnectifyNavHost
import com.example.connectify.presentation.navigation.ContactNavigationSuiteScaffold
import com.example.connectify.presentation.navigation.navigateSingleBottomTo
import com.example.connectify.presentation.navigation.navigationItems
import com.example.connectify.presentation.screens.theme.ThemeViewModel
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
            val navBackStackEntry by navHostController.currentBackStackEntryAsState()
            val currentRoute = navBackStackEntry?.destination?.route
            val themeViewModel : ThemeViewModel = hiltViewModel<ThemeViewModel>()
            val themeMode by themeViewModel.themeMode.collectAsState()

            ConnectifyTheme(themeMode) {
                ContactNavigationSuiteScaffold(
                    navigationItems = navigationItems,
                    currentRoute = currentRoute,
                    onNavigateTo = { route ->
                        navHostController.navigateSingleBottomTo(route)
                    }
                ) {
                    ConnectifyNavHost(navHostController)
                }
            }
        }
    }
}