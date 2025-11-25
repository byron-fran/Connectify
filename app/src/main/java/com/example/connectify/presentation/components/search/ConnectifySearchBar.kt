package com.example.connectify.presentation.components.search

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SearchBar
import androidx.compose.material3.SearchBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.connectify.R
import com.example.connectify.presentation.components.global.CustomIcon
import com.example.connectify.presentation.components.global.CustomIconButton
import com.example.connectify.presentation.components.global.LabelMedium
import com.example.connectify.ui.theme.RoundedCorner
import com.example.connectify.ui.theme.Spacing

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ConnectifySearchBar(
    query: String,
    expanded: Boolean,
    onQueryChange: (String) -> Unit,
    onClearQuery: () -> Unit,
    onSearch: (String) -> Unit,
    onExpandedChange: (Boolean) -> Unit,
    content: @Composable () -> Unit
) {

    SearchBar(
        inputField = {
            SearchBarDefaults.InputField(
                query = query,
                onQueryChange = onQueryChange,
                onSearch = onSearch,
                onExpandedChange = onExpandedChange,
                expanded = expanded,
                placeholder = {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(Spacing.spacing_sm)
                    ) {
                        CustomIcon(
                            icon = R.drawable.icon_search,
                            color = MaterialTheme.colorScheme.onSurface,
                            modifier = Modifier.size(24.dp)
                        )
                        LabelMedium(
                            text = stringResource(R.string.search_contacts),
                            color = MaterialTheme.colorScheme.onSurface
                        )
                    }
                },
                trailingIcon = {
                    CustomIconButton(
                        icon = R.drawable.icon_cancel,
                        color = MaterialTheme.colorScheme.onSurface
                    ) {
                        onClearQuery()
                    }
                },
            )
        },
        expanded = expanded,
        onExpandedChange = {},
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(RoundedCorner.rounded_corner_md),
        colors = SearchBarDefaults.colors(
            containerColor = MaterialTheme.colorScheme.surface,
            dividerColor = MaterialTheme.colorScheme.primary
        ),
    ) {
        content()
    }
}