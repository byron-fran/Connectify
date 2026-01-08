package com.example.connectify.presentation.components.contact

import androidx.compose.ui.test.assertTextContains
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import com.example.connectify.domain.models.Contact
import org.junit.Rule
import org.junit.Test
import java.util.UUID

class ContactCardTest {
    private val contactId: String = UUID.randomUUID().toString()
    private val contactOne = Contact(
        id = contactId,
        name = "Jose",
        phoneNumber = "123456789",
        email = "juan@gmail.com",
        imageUrl = null,
        isFavorite = false
    )

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun contactCard_displays_userData() {

        composeTestRule.setContent {
            ContactCard(
                togglingFavoriteId = contactId,
                contact = contactOne,
                onFavoriteToggle = {},
                onNavigate = {},
                onChangeModalBottonSheet = {}
            )
        }
        composeTestRule
            .onNodeWithTag("contact_name_${contactOne.id}", useUnmergedTree = true)
            .assertTextContains("Jose")
        composeTestRule
            .onNodeWithTag("contact_phone_${contactOne.id}", useUnmergedTree = true)
            .assertTextContains("123456789")
        composeTestRule
            .onNodeWithTag("contact_email_${contactOne.id}", useUnmergedTree = true)
            .assertTextContains("juan@gmail.com")
    }
}       