package com.example.connectify.presentation.navigation

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.test.assertIsSelected
import androidx.compose.ui.test.hasContentDescription
import androidx.compose.ui.test.hasTestTag
import androidx.compose.ui.test.hasText
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performClick
import androidx.test.ext.junit.runners.AndroidJUnit4
import junit.framework.TestCase.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class ContactNavigationSuiteScaffoldTest {
    private var navigatedRoute: String? = null
    private var currentRouteState: String? by mutableStateOf(null)
    @get:Rule
    val composeTestRule = createComposeRule()

    @Before
    fun setup() {

        navigatedRoute = null
        currentRouteState = null

        composeTestRule.setContent {
            ContactNavigationSuiteScaffold(
                navigationItems = navigationItems,
                currentRoute = currentRouteState,
                onNavigateTo = { navigatedRoute = it },
                content = {}
            )
        }
    }

    @Test
    fun navigation_items_are_displayed() {

        composeTestRule
            .onNode(hasText("Contacts") and hasTestTag("navigation_item_Contacts"))
            .assertExists()
    }

    @Test
    fun all_navigation_items_are_displayed_by_content_description() {

        val expectedContentDescriptions = listOf("Contacts", "Favorites", "Search")
        expectedContentDescriptions.forEach { contentDescription ->
            composeTestRule
                .onNodeWithContentDescription(contentDescription)
                .assertExists()
        }
    }

    @Test
    fun when_currentRoute_is_null_contacts_is_selected_by_default() {

        currentRouteState = null
        composeTestRule
            .onNodeWithTag("navigation_item_Contacts")
            .assertIsSelected()
    }

    @Test
    fun when_currentRoute_is_favorites_favorites_is_selected() {

        currentRouteState = Screens.Favorites::class.qualifiedName
        composeTestRule
            .onNodeWithTag("navigation_item_Favorites")
            .assertIsSelected()
    }

    @Test
    fun click_on_item_invokes_navigation_callback() {

        composeTestRule
            .onNodeWithTag("navigation_item_Favorites")
            .performClick()

        assertEquals(Screens.Favorites::class.qualifiedName, navigatedRoute)
    }
}