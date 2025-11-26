package com.example.connectify.presentation.navigation

import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.NavigationDrawerItemDefaults
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
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.toSize
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
    onNavigateTo: (Screens) -> Unit,
    content: @Composable () -> Unit
) {
    var selectDestination: Screens by remember { mutableStateOf(Screens.Contacts) }

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

    val windowSize = with(LocalDensity.current) {
        currentWindowSize().toSize().toDpSize()
    }

    val layoutType = if (windowSize.width >= 1200.dp) {
        NavigationSuiteType.NavigationDrawer
    } else {
        NavigationSuiteScaffoldDefaults.calculateFromAdaptiveInfo(
            currentWindowAdaptiveInfo()
        )
    }

    val navItemColors = NavigationBarItemDefaults.colors(
        selectedIconColor = MaterialTheme.colorScheme.onSecondary,
        selectedTextColor = MaterialTheme.colorScheme.onSecondary,
        indicatorColor = MaterialTheme.colorScheme.secondary,
        unselectedIconColor = MaterialTheme.colorScheme.onSurface,
        unselectedTextColor = MaterialTheme.colorScheme.onSurface,
    )
    val navigationRailItemColors = NavigationRailItemDefaults.colors(
        selectedIconColor = MaterialTheme.colorScheme.onSecondary,
        selectedTextColor = MaterialTheme.colorScheme.onSecondary,
        indicatorColor = MaterialTheme.colorScheme.secondary,
        unselectedIconColor = MaterialTheme.colorScheme.onSurface,
        unselectedTextColor = MaterialTheme.colorScheme.onSurface,

        )
    val navigationDrawerItemColors = NavigationDrawerItemDefaults.colors(
        selectedIconColor = MaterialTheme.colorScheme.onSecondary,
        selectedTextColor = MaterialTheme.colorScheme.onSecondary,
        unselectedIconColor = MaterialTheme.colorScheme.onSurface,
        unselectedTextColor = MaterialTheme.colorScheme.onSurface,
        selectedBadgeColor = MaterialTheme.colorScheme.secondary,
        unselectedBadgeColor = MaterialTheme.colorScheme.onSurface,
        selectedContainerColor = MaterialTheme.colorScheme.secondary
    )

    NavigationSuiteScaffold(
        layoutType = layoutType,
        navigationSuiteItems = {
            navigationItems.forEach {
                item(
                    selected = it.screen == selectDestination,
                    onClick = {
                        selectDestination = it.screen
                        onNavigateTo(it.screen)
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