package com.example.loginandregistration.presentation.policeItemListScreen

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.loginandregistration.presentation.domain.PoliceListItem

@Composable
fun PoliceListScreen(
    onItemClicked: (PoliceListItem) -> Unit,
    viewModel: PoliceListScreenViewModel = hiltViewModel()
) {
    val policeListState by viewModel.policeState.collectAsStateWithLifecycle()
    var loading = remember {true}
    var policeItemList = remember {  emptyList<PoliceListItem>() }

    policeListState?.let { state ->
        loading = state.state is PoliceListState.State.Loading
        when (state.state) {
            is PoliceListState.State.Reading -> {
                policeItemList = state.state.listItems
            }
            is PoliceListState.State.Selected -> {
                LaunchedEffect(Unit) { onItemClicked(PoliceListItem(state.state.id, state.state.name)) }
            }
            else -> {}
        }
    }

    if(loading) loadingIndicator()
    PoliceItemList(policeItemList) {
        viewModel.process(PoliceListEvent.ClickItem(it.id, it.name))
    }
}

@Composable
fun loadingIndicator() {
    CircularProgressIndicator(
        modifier = Modifier.width(64.dp),
        color = MaterialTheme.colorScheme.secondary,
        trackColor = MaterialTheme.colorScheme.surfaceVariant,
    )
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

@Composable
fun PoliceItem(policeListItem: PoliceListItem, onItemClicked: (PoliceListItem) -> Unit) {
    ClickableText(
        text = AnnotatedString(policeListItem.name),
        onClick = { onItemClicked(policeListItem) },
        style = TextStyle(
            color = Color.Black,
            fontSize = 18.sp,
            fontFamily = FontFamily.SansSerif,
            textAlign = TextAlign.Center
        ),
        modifier = Modifier
            .padding(horizontal = 8.dp, vertical = 8.dp)
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp)
    )
}