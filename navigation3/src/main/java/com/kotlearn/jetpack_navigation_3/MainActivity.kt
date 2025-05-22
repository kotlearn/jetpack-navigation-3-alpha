package com.kotlearn.jetpack_navigation_3

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation3.runtime.NavEntry
import androidx.navigation3.runtime.NavKey
import androidx.navigation3.runtime.rememberNavBackStack
import androidx.navigation3.ui.NavDisplay
import com.kotlearn.jetpack_navigation_3.notes.NoteCreateScreen
import com.kotlearn.jetpack_navigation_3.notes.NoteDetailScreen
import com.kotlearn.jetpack_navigation_3.notes.NoteEditScreen
import com.kotlearn.jetpack_navigation_3.notes.NoteListScreen
import com.kotlearn.jetpack_navigation_3.ui.theme.Jetpack_navigation_3Theme
import kotlinx.serialization.Serializable

@Serializable
data object NoteList : NavKey

@Serializable
data object NoteCreate : NavKey

@Serializable
data class NoteDetail(val id: Long) : NavKey

@Serializable
data class NoteEdit(val id: Long) : NavKey

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Jetpack_navigation_3Theme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    val screenModifier = Modifier
                        .fillMaxSize()
                        .padding(innerPadding)
                    val backStack = rememberNavBackStack(NoteList)

                    NavDisplay(
                        backStack = backStack,
                    ) { route ->
                        when (route) {
                            is NoteList -> NavEntry(route) {
                                NoteListScreen(
                                    onNoteClick = { id ->
                                        backStack.add(NoteDetail(id))
                                    },
                                    onCreateClick = {
                                        backStack.add(NoteCreate)
                                    },
                                    modifier = screenModifier,
                                )
                            }

                            is NoteDetail -> NavEntry(route) {
                                NoteDetailScreen(
                                    noteId = route.id,
                                    onBackClick = {
                                        backStack.removeLastOrNull()
                                    },
                                    onEditClick = {
                                        backStack.add(NoteEdit(route.id))
                                    },
                                    modifier = screenModifier,
                                )
                            }

                            is NoteEdit -> NavEntry(route) {
                                NoteEditScreen(
                                    noteId = route.id,
                                    onBackClick = {
                                        backStack.removeLastOrNull()
                                    },
                                    onSaveClick = {
                                        backStack.removeLastOrNull()
                                    },
                                    modifier = screenModifier,
                                )
                            }

                            is NoteCreate -> NavEntry(route) {
                                NoteCreateScreen(
                                    onBackClick = {
                                        backStack.removeLastOrNull()
                                    },
                                    onNoteCreated = { id ->
                                        backStack.clear()
                                        backStack.addAll(listOf(NoteList, NoteDetail(id)))
                                    },
                                    modifier = screenModifier,
                                )
                            }

                            else -> NavEntry(route) { Text("Unknown route") }
                        }

                    }
                }
            }
        }
    }
}
