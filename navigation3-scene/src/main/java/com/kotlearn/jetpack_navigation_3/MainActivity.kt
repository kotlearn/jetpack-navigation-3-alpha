package com.kotlearn.jetpack_navigation_3

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.adaptive.ExperimentalMaterial3AdaptiveApi
import androidx.compose.material3.adaptive.navigation3.ListDetailSceneStrategy
import androidx.compose.material3.adaptive.navigation3.rememberListDetailSceneStrategy
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation3.runtime.NavKey
import androidx.navigation3.runtime.entry
import androidx.navigation3.runtime.entryProvider
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
    @OptIn(ExperimentalMaterial3AdaptiveApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Jetpack_navigation_3Theme {
                val backStack = rememberNavBackStack(NoteList)
                val listDetailStrategy = rememberListDetailSceneStrategy<NavKey>()
                Scaffold(
                    modifier = Modifier.fillMaxSize()
                ) { innerPadding ->
                    val screenModifier = Modifier
                        .fillMaxSize()
                        .padding(innerPadding)
                    NavDisplay(
                        backStack = backStack,
                        sceneStrategy = listDetailStrategy,
                        entryProvider = entryProvider {
                            entry<NoteList>(
                                metadata = ListDetailSceneStrategy.listPane(
                                    detailPlaceholder = {
                                        Box(
                                            contentAlignment = Alignment.Center,
                                            modifier = screenModifier,
                                        ) {
                                            Text("Choose a note from the list")
                                        }
                                    }
                                )
                            ) {
                                NoteListScreen(
                                    onNoteClick = { id ->
                                        val last = backStack.lastOrNull()
                                        if (last is NoteDetail) {
                                            backStack[backStack.lastIndex] = NoteDetail(id)
                                        } else {
                                            backStack.add(NoteDetail(id))
                                        }
                                    },
                                    onCreateClick = { backStack.add(NoteCreate) },
                                    modifier = screenModifier
                                )
                            }
                            entry<NoteDetail>(
                                metadata = ListDetailSceneStrategy.detailPane()
                            ) { args ->
                                NoteDetailScreen(
                                    noteId = args.id,
                                    onBackClick = { backStack.removeLastOrNull() },
                                    onEditClick = { backStack.add(NoteEdit(args.id)) },
                                    showBackButton = listDetailStrategy.directive.maxHorizontalPartitions == 1,
                                    modifier = screenModifier
                                )
                            }
                            entry<NoteEdit>(
                                metadata = ListDetailSceneStrategy.extraPane()
                            ) { args ->
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
