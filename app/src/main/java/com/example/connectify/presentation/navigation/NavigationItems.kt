package com.example.connectify.presentation.navigation

import com.example.connectify.R
import java.util.UUID

data class NavItem(
    val screen: Screens,
    val label: Int,
    val icon: Int,
    val id : String = UUID.randomUUID().toString(),
    val contentDescription : String = ""
)

val navigationItems = listOf(
    NavItem(
        screen = Screens.Contacts,
        label = R.string.contacts,
        icon = R.drawable.icon_contact,
        contentDescription = "Contacts"
    ),
    NavItem(
        screen = Screens.Favorites,
        label = R.string.favorites,
        icon = R.drawable.icon_star_round_filled,
        contentDescription = "Favorites"
    ),
    NavItem(
        screen = Screens.Search,
        label = R.string.search,
        icon = R.drawable.icon_search,
        contentDescription = "Search"
    )
)