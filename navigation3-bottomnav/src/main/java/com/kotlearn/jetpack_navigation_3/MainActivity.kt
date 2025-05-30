package com.kotlearn.jetpack_navigation_3

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import androidx.navigation3.runtime.NavKey
import androidx.navigation3.runtime.entry
import androidx.navigation3.runtime.entryProvider
import androidx.navigation3.runtime.rememberNavBackStack
import androidx.navigation3.ui.NavDisplay
import com.kotlearn.jetpack_navigation_3.home.HomeDetailScreen
import com.kotlearn.jetpack_navigation_3.home.HomeScreen
import com.kotlearn.jetpack_navigation_3.notes.NoteCreateScreen
import com.kotlearn.jetpack_navigation_3.notes.NoteDetailScreen
import com.kotlearn.jetpack_navigation_3.notes.NoteEditScreen
import com.kotlearn.jetpack_navigation_3.notes.NoteListScreen
import com.kotlearn.jetpack_navigation_3.ui.theme.Jetpack_navigation_3Theme
import kotlinx.serialization.Serializable

@Serializable
data object Home : NavKey

@Serializable
data object HomeDetail : NavKey

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
                val backStack = rememberNavBackStack(Home)
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    val screenModifier = Modifier
                        .fillMaxSize()
                        .padding(innerPadding)
                    NavDisplay(
                        backStack = backStack,
                        onBack = { backStack.removeLastOrNull() },
                        entryProvider = entryProvider {
                            entry<Home> {
                                HomeScreen(
                                    onDetailClick = { backStack.add(HomeDetail) },
                                    modifier = screenModifier
                                )
                            }
                            entry<HomeDetail> {
                                HomeDetailScreen(
                                    onBackClick = { backStack.removeLastOrNull() },
                                    modifier = screenModifier
                                )
                            }
                            entry<NoteList> {
                                NoteListScreen(
                                    onNoteClick = { id -> backStack.add(NoteDetail(id)) },
                                    onCreateClick = { backStack.add(NoteCreate) },
                                    modifier = screenModifier
                                )
                            }
                            entry<NoteDetail> { args ->
                                NoteDetailScreen(
                                    noteId = args.id,
                                    onBackClick = { backStack.removeLastOrNull() },
                                    onEditClick = { backStack.add(NoteEdit(args.id)) },
                                    modifier = screenModifier
                                )
                            }
                            entry<NoteEdit> { args ->
                                NoteEditScreen(
                                    noteId = args.id,
                                    onBackClick = { backStack.removeLastOrNull() },
                                    onSaveClick = { backStack.removeLastOrNull() },
                                    modifier = screenModifier
                                )
                            }
                            entry<NoteCreate> {
                                NoteCreateScreen(
                                    onBackClick = { backStack.removeLastOrNull() },
                                    onNoteCreated = { id ->
                                        backStack.clear()
                                        backStack.addAll(listOf(NoteList, NoteDetail(id)))
                                    },
                                    modifier = screenModifier
                                )
                            }
                        }
                    )
                }
            }
        }
    }
}
