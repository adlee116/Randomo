package com.example.loginandregistration.presentation.policeItemListScreen

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.draw.clip
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.loginandregistration.presentation.domain.PoliceListItem

@Composable
fun PoliceListScreen(
    onItemClicked: (PoliceListItem) -> Unit,
    viewModel: PoliceListScreenViewModel = hiltViewModel()
) {
    val policeListState by viewModel.policeState.collectAsStateWithLifecycle()
    var loading = remember { true }
    var policeItemList = remember { emptyList<PoliceListItem>() }

    policeListState?.let { state ->
        loading = state.state is PoliceListState.State.Loading
        when (state.state) {
            is PoliceListState.State.Reading -> {
                policeItemList = state.state.listItems
            }

            is PoliceListState.State.Selected -> {
                LaunchedEffect(Unit) {
                    onItemClicked(
                        PoliceListItem(
                            state.state.id,
                            state.state.name
                        )
                    )
                }
            }

            else -> {}
        }
    }

    if (loading) loadingIndicator()
    PoliceItemList(policeItemList) {
        viewModel.process(PoliceListEvent.ClickItem(it.id, it.name))
    }
}

@Composable
fun loadingIndicator() {
    Column(
        Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        CircularProgressIndicator(
            modifier = Modifier
                .width(64.dp)
                .fillMaxWidth(),
            color = MaterialTheme.colorScheme.secondary,
            trackColor = MaterialTheme.colorScheme.surfaceVariant,
        )
    }
}

@Composable
fun PoliceItemList(items: List<PoliceListItem>, onItemClicked: (PoliceListItem) -> Unit) {
    LazyColumn(
        modifier = Modifier.padding(vertical = 22.dp)
    ) {
        items(items = items) { policeItem ->
            PoliceItem(policeItem, onItemClicked)
        }
    }
}

@Preview @Composable
fun PoliceItemPreview() {
    PoliceItem(PoliceListItem("Long Name", "With another long name"), {})
}

@Composable
fun PoliceItem(policeListItem: PoliceListItem, onItemClicked: (PoliceListItem) -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp)
            .clickable(onClick = { onItemClicked(policeListItem) }),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Box(
            modifier = Modifier
                .clip(RoundedCornerShape(12.dp))
                .padding(16.dp)
        ) {
            Text(
                text = policeListItem.id + " : " + policeListItem.name,
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.onSurface
            )
        }
    }
}