package com.kotlearn.jetpack_navigation_3.notes

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NoteDetailScreen(
    noteId: Long,
    onBackClick: () -> Unit,
    onEditClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val note = remember(noteId) { NoteRepository.getNoteById(noteId) }

    Column(
        verticalArrangement = Arrangement.spacedBy(16.dp),
        modifier = modifier
            .verticalScroll(rememberScrollState())
    ) {
        TopAppBar(
            title = {
                Text(note?.title.orEmpty())
            },
            navigationIcon = {
                IconButton(onClick = onBackClick) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Default.ArrowBack,
                        contentDescription = "Back"
                    )
                }
            },
            windowInsets = WindowInsets(0),
        )
        if (note == null) {

            Text(
                text = "Note not found.",
                modifier = modifier
                    .wrapContentSize(Alignment.Center)
            )
        } else {
            Text(
                text = note.content,
                style = MaterialTheme.typography.bodyLarge,
                modifier = Modifier.padding(horizontal = 16.dp)
            )
            Button(
                onClick = onEditClick,
                modifier = Modifier.align(Alignment.CenterHorizontally)
            ) {
                Text("Edit Note")
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun NoteScreenPreview() {
    NoteDetailScreen(
        noteId = 1L,
        onBackClick = {},
        onEditClick = {},
        modifier = Modifier
            .fillMaxSize()
    )
}