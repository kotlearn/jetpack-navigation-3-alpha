package com.kotlearn.jetpack_navigation_3.notes

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NoteListScreen(
    onNoteClick: (Long) -> Unit,
    onCreateClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val notes = remember { NoteRepository.getAllNotes() }

    Column(modifier) {
        TopAppBar(
            title = {
                Text("Notes")
            },
            actions = {
                IconButton(onClick = onCreateClick) {
                    Icon(
                        imageVector = Icons.Default.Add,
                        contentDescription = "Create note"
                    )
                }
            },
            windowInsets = WindowInsets(0),
        )
        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(8.dp),
            contentPadding = PaddingValues(16.dp),
            modifier = Modifier.fillMaxSize(),
        ) {
            items(notes) { note ->
                Card(
                    onClick = { onNoteClick(note.id) },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 4.dp),
                ) {
                    Column(
                        verticalArrangement = Arrangement.spacedBy(4.dp),
                        modifier = Modifier.padding(16.dp)
                    ) {
                        Text(
                            text = note.title,
                            style = MaterialTheme.typography.titleMedium
                        )
                        Text(
                            text = note.content.take(100),
                            style = MaterialTheme.typography.bodyMedium,
                            maxLines = 2,
                            overflow = TextOverflow.Ellipsis
                        )
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun NoteListScreenPreview() {
    NoteListScreen(
        onCreateClick = {},
        onNoteClick = {},
        modifier = Modifier.fillMaxSize()
    )
}
