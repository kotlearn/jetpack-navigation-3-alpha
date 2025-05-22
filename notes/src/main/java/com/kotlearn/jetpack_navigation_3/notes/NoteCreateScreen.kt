package com.kotlearn.jetpack_navigation_3.notes

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NoteCreateScreen(
    onBackClick: () -> Unit,
    onNoteCreated: (id: Long) -> Unit,
    modifier: Modifier = Modifier
) {
    var title by rememberSaveable { mutableStateOf("") }
    var content by rememberSaveable { mutableStateOf("") }

    Column(
        verticalArrangement = Arrangement.spacedBy(16.dp),
        modifier = modifier
            .verticalScroll(rememberScrollState())
    ) {
        TopAppBar(
            title = {
                Text("Create Note")
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

        OutlinedTextField(
            value = title,
            onValueChange = { title = it },
            label = { Text("Title") },
            modifier = Modifier
                .padding(horizontal = 16.dp)
                .fillMaxWidth()
        )

        OutlinedTextField(
            value = content,
            onValueChange = { content = it },
            label = { Text("Content") },
            modifier = Modifier
                .padding(horizontal = 16.dp)
                .fillMaxWidth()
                .height(200.dp),
            maxLines = Int.MAX_VALUE
        )

        Button(
            onClick = {
                val note = NoteRepository.createNote(title, content)
                onNoteCreated(note.id)
            },
            enabled = title.isNotBlank() && content.isNotBlank(),
            modifier = Modifier.align(Alignment.CenterHorizontally)
        ) {
            Text("Save Note")
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun NoteCreateScreenPreview() {
    NoteCreateScreen(
        onBackClick = {},
        onNoteCreated = {},
        modifier = Modifier.fillMaxSize(),
    )
}