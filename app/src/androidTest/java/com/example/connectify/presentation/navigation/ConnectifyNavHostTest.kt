package com.example.connectify.presentation.navigation

import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.isDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.navigation.compose.ComposeNavigator
import androidx.navigation.testing.TestNavHostController
import org.junit.Assert.fail
import org.junit.Before
import org.junit.Rule
import org.junit.Test


class ConnectifyNavHostTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    private lateinit var navController: TestNavHostController

    @Before
    fun connectifyNavHostTest() {
        composeTestRule.setContent {
            navController = TestNavHostController(LocalContext.current)
            navController.navigatorProvider.addNavigator(
                ComposeNavigator()
            )
            ConnectifyNavHost(navController)

        }
    }

    @Test
    fun connectifyNavHost_verifyContactStatDestination() {
        composeTestRule
            .onNodeWithContentDescription("Contact Screen")
            .assertIsDisplayed()
    }

}