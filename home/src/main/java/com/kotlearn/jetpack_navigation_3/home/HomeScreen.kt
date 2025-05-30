package com.kotlearn.jetpack_navigation_3.home

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    onDetailClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier,
    ) {
        TopAppBar(
            title = {
                Text("Home")
            },
            windowInsets = WindowInsets(0),
        )
        Spacer(Modifier.weight(8f))
        Text("Home")
        Spacer(Modifier.weight(1f))
        Button(onClick = onDetailClick) {
            Text("Go to detail")
        }
        Spacer(Modifier.weight(8f))
    }
}

@Preview(showBackground = true)
@Composable
private fun NoteListScreenPreview() {
    HomeScreen(
        onDetailClick = {},
        modifier = Modifier.fillMaxSize()
    )
}
