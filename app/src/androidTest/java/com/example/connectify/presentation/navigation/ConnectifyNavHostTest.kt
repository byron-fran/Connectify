package com.example.connectify.presentation.navigation

import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onAllNodesWithTag
import androidx.compose.ui.test.onFirst
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performClick
import androidx.navigation.compose.ComposeNavigator
import androidx.navigation.testing.TestNavHostController
import com.example.connectify.utils.Tag.ADD_CONTACT_BUTTON
import com.example.connectify.utils.Tag.ADD_CONTACT_SCREEN
import com.example.connectify.utils.Tag.CONTACT_CARD
import com.example.connectify.utils.Tag.CONTACT_DETAIL_SCREEN
import com.example.connectify.utils.Tag.CONTACT_SCREEN
import com.example.connectify.utils.Tag.SETTINGS_BUTTON
import com.example.connectify.utils.Tag.SETTINGS_SCREEN
import org.junit.Before
import org.junit.Rule
import org.junit.Test


class ConnectifyNavHostTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    private lateinit var navController: TestNavHostController

    @OptIn(ExperimentalSharedTransitionApi::class)
    @Before
    fun connectifyNavHostTest() {
        composeTestRule.setContent {
            navController = TestNavHostController(LocalContext.current)
            navController.navigatorProvider.addNavigator(
                ComposeNavigator()
            )
            FakeNavHost(navController)
        }
    }

    @Test
    fun connectifyNavHost_verifyContactStatDestination() {
        composeTestRule
            .onNodeWithTag(CONTACT_SCREEN)
            .assertIsDisplayed()
    }

    @Test
    fun connectifyNavHost_navigateToAddContactScreen() {
        composeTestRule
            .onNodeWithTag(ADD_CONTACT_BUTTON)
            .performClick()
        composeTestRule.waitForIdle()
        composeTestRule
            .onNodeWithTag(ADD_CONTACT_SCREEN)
            .assertIsDisplayed()
    }

    @Test
    fun connectifyNavHost_navigateToContactDetailsScreen() {
        composeTestRule
            .onAllNodesWithTag("${CONTACT_CARD}_1")
            .onFirst()
            .performClick()
        composeTestRule.waitForIdle()
        composeTestRule
            .onNodeWithTag("${CONTACT_DETAIL_SCREEN}_1")
            .assertIsDisplayed()


    }

    @Test
    fun connectifyNavHost_navigateToSettingsScreen() {
        composeTestRule
            .onAllNodesWithTag(SETTINGS_BUTTON)
            .onFirst()
            .performClick()
        composeTestRule.waitForIdle()
        composeTestRule
            .onNodeWithTag(SETTINGS_SCREEN)
            .assertIsDisplayed()
    }
}