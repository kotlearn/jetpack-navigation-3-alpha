package com.kotlearn.jetpack_navigation_3

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.kotlearn.jetpack_navigation_3.notes.NoteCreateScreen
import com.kotlearn.jetpack_navigation_3.notes.NoteDetailScreen
import com.kotlearn.jetpack_navigation_3.notes.NoteEditScreen
import com.kotlearn.jetpack_navigation_3.notes.NoteListScreen
import com.kotlearn.jetpack_navigation_3.ui.theme.Jetpack_navigation_3Theme
import kotlinx.serialization.Serializable

@Serializable
data object NoteList

@Serializable
data object NoteCreate

@Serializable
data class NoteDetail(val id: Long)

@Serializable
data class NoteEdit(val id: Long)

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
                    val navController = rememberNavController()

                    val id = 4L

                    navController.navigate(NoteList)
                    navController.navigate(NoteDetail(id))
                    navController.navigate(NoteEdit(id))

                    NavHost(
                        navController = navController,
                        startDestination = NoteList,
                    ) {
                        composable<NoteList> {
                            NoteListScreen(
                                onNoteClick = { id ->
                                    navController.navigate(NoteDetail(id))
                                },
                                onCreateClick = {
                                    navController.navigate(NoteCreate)
                                },
                                modifier = screenModifier,
                            )
                        }
                        composable<NoteCreate> {
                            NoteCreateScreen(
                                onBackClick = {
                                    navController.popBackStack()
                                },
                                onNoteCreated = { id ->
                                    navController.popBackStack(NoteList, inclusive = false)
                                    navController.navigate(NoteDetail(id))
                                },
                                modifier = screenModifier,
                            )
                        }
                        composable<NoteDetail> {
                            val args = it.toRoute<NoteDetail>()
                            NoteDetailScreen(
                                noteId = args.id,
                                onBackClick = {
                                    navController.popBackStack()
                                },
                                onEditClick = {
                                    navController.navigate(NoteEdit(args.id))
                                },
                                modifier = screenModifier,
                            )
                        }
                        composable<NoteEdit> {
                            val args = it.toRoute<NoteEdit>()
                            NoteEditScreen(
                                noteId = args.id,
                                onBackClick = {
                                    navController.popBackStack()
                                },
                                onSaveClick = {
                                    navController.popBackStack()
                                },
                                modifier = screenModifier,
                            )
                        }
                    }
                }
            }
        }
    }
}
