package com.example.connectify.presentation.navigation

import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBarItemColors
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.NavigationDrawerItemColors
import androidx.compose.material3.NavigationDrawerItemDefaults
import androidx.compose.material3.NavigationRailItemColors
import androidx.compose.material3.NavigationRailItemDefaults
import androidx.compose.material3.adaptive.currentWindowAdaptiveInfo
import androidx.compose.material3.adaptive.currentWindowSize
import androidx.compose.material3.adaptive.navigationsuite.NavigationSuiteDefaults
import androidx.compose.material3.adaptive.navigationsuite.NavigationSuiteItemColors
import androidx.compose.material3.adaptive.navigationsuite.NavigationSuiteScaffold
import androidx.compose.material3.adaptive.navigationsuite.NavigationSuiteScaffoldDefaults
import androidx.compose.material3.adaptive.navigationsuite.NavigationSuiteType
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.toSize
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.connectify.R
import com.example.connectify.presentation.components.global.CustomIcon
import com.example.connectify.presentation.components.global.LabelSmall

data class NavItem(
    val screen: Screens,
    val label: Int,
    val icon: Int
)

@Composable
fun ContactNavigationSuiteScaffold(
    navHostController: NavHostController,
    content: @Composable () -> Unit
) {

    val navBackStackEntry by navHostController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination

    val selectDestination: Screens = when (currentDestination?.route) {
        Screens.Contacts::class.qualifiedName -> Screens.Contacts
        Screens.Favorites::class.qualifiedName -> Screens.Favorites
        Screens.Search::class.qualifiedName -> Screens.Search
        else -> Screens.Contacts
    }

    val navigationItems = listOf(
        NavItem(
            screen = Screens.Contacts,
            label = R.string.contacts,
            icon = R.drawable.icon_contact
        ),
        NavItem(
            screen = Screens.Favorites,
            label = R.string.favorites,
            icon = R.drawable.icon_star_round_filled
        ),
        NavItem(
            screen = Screens.Search,
            label = R.string.search,
            icon = R.drawable.icon_search
        )

    )

    val navItemColors = NavigationBarColors()
    val navigationRailItemColors = NavigationRailColors()
    val navigationDrawerItemColors = NavigationDrawerColors()

    val windowSize = with(LocalDensity.current) {
        currentWindowSize().toSize().toDpSize()
    }

    val layoutType = if (windowSize.width >= 1200.dp) {
        NavigationSuiteType.NavigationDrawer
    } else {
        NavigationSuiteScaffoldDefaults.calculateFromAdaptiveInfo(
            adaptiveInfo = currentWindowAdaptiveInfo()
        )
    }

    NavigationSuiteScaffold(
        layoutType = layoutType,
        navigationSuiteItems = {
            navigationItems.forEach {
                item(
                    selected = it.screen == selectDestination,
                    onClick = {
                        navHostController.navigateSingleBottomTo(it.screen::class.qualifiedName!!)
                    },
                    icon = {
                        CustomIcon(icon = it.icon, color = LocalContentColor.current)
                    },
                    label = {
                        LabelSmall(stringResource(it.label), color = Color.Unspecified)
                    },
                    colors = NavigationSuiteItemColors(
                        navigationBarItemColors = navItemColors,
                        navigationRailItemColors = navigationRailItemColors,
                        navigationDrawerItemColors = navigationDrawerItemColors
                    )
                )
            }
        },
        containerColor = MaterialTheme.colorScheme.background,
        navigationSuiteColors = NavigationSuiteDefaults.colors(
            navigationBarContainerColor = MaterialTheme.colorScheme.surface,
            navigationRailContainerColor = MaterialTheme.colorScheme.surface,
            navigationDrawerContainerColor = MaterialTheme.colorScheme.surface,
        )
    ) {
        content()
    }
}

@Composable
fun NavigationBarColors() : NavigationBarItemColors {

    return NavigationBarItemDefaults.colors(
        selectedIconColor = MaterialTheme.colorScheme.onSecondary,
        selectedTextColor = MaterialTheme.colorScheme.onSecondary,
        indicatorColor = MaterialTheme.colorScheme.secondary,
        unselectedIconColor = MaterialTheme.colorScheme.onSurface,
        unselectedTextColor = MaterialTheme.colorScheme.onSurface

    )
}

@Composable
fun NavigationRailColors() : NavigationRailItemColors {
    return NavigationRailItemDefaults.colors(
        selectedIconColor = MaterialTheme.colorScheme.onSecondary,
        selectedTextColor = MaterialTheme.colorScheme.onSecondary,
        indicatorColor = MaterialTheme.colorScheme.secondary,
        unselectedIconColor = MaterialTheme.colorScheme.onSurface,
        unselectedTextColor = MaterialTheme.colorScheme.onSurface
    )
}

@Composable
fun NavigationDrawerColors() : NavigationDrawerItemColors {

    return NavigationDrawerItemDefaults.colors(
        selectedIconColor = MaterialTheme.colorScheme.onSecondary,
        selectedTextColor = MaterialTheme.colorScheme.onSecondary,
        unselectedIconColor = MaterialTheme.colorScheme.onSurface,
        unselectedTextColor = MaterialTheme.colorScheme.onSurface,
        selectedBadgeColor = MaterialTheme.colorScheme.secondary,
        unselectedBadgeColor = MaterialTheme.colorScheme.onSurface,
        selectedContainerColor = MaterialTheme.colorScheme.secondary
    )
}