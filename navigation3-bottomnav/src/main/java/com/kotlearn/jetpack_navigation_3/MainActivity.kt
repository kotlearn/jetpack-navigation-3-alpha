package com.kotlearn.jetpack_navigation_3

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Notes
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.navigation3.runtime.NavKey
import androidx.navigation3.runtime.entry
import androidx.navigation3.runtime.entryProvider
import androidx.navigation3.ui.NavDisplay
import com.kotlearn.jetpack_navigation_3.home.HomeDetailScreen
import com.kotlearn.jetpack_navigation_3.home.HomeScreen
import com.kotlearn.jetpack_navigation_3.notes.NoteCreateScreen
import com.kotlearn.jetpack_navigation_3.notes.NoteDetailScreen
import com.kotlearn.jetpack_navigation_3.notes.NoteEditScreen
import com.kotlearn.jetpack_navigation_3.notes.NoteListScreen
import com.kotlearn.jetpack_navigation_3.ui.theme.Jetpack_navigation_3Theme
import kotlinx.serialization.Serializable

interface BottomNavItem {
    val icon: ImageVector
    val title: String
}

@Serializable
data object Home : NavKey, BottomNavItem {
    override val icon: ImageVector = Icons.Filled.Home
    override val title: String = "Home"
}

@Serializable
data object HomeDetail : NavKey

@Serializable
data object NoteList : NavKey, BottomNavItem {
    override val icon: ImageVector = Icons.AutoMirrored.Filled.Notes
    override val title: String = "Notes"
}

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
                val bottomNavItems = listOf(Home, NoteList)
                val topLevelBackStack = remember { TopLevelBackStack<NavKey>(Home) }
                Scaffold(
                    bottomBar = {
                        NavigationBar {
                            bottomNavItems.forEach { item ->
                                val selected = topLevelBackStack.topLevelKey == item
                                NavigationBarItem(
                                    selected = selected,
                                    onClick = {
                                        topLevelBackStack.switchTopLevel(item)
                                    },
                                    icon = {
                                        Icon(
                                            imageVector = item.icon,
                                            contentDescription = item.title
                                        )
                                    },
                                    label = {
                                        Text(item.title)
                                    },
                                )
                            }
                        }
                    },
                    modifier = Modifier.fillMaxSize()
                ) { innerPadding ->
                    val screenModifier = Modifier
                        .fillMaxSize()
                        .padding(innerPadding)
                    NavDisplay(
                        backStack = topLevelBackStack.backStack,
                        onBack = { topLevelBackStack.removeLast() },
                        entryProvider = entryProvider {
                            entry<Home> {
                                HomeScreen(
                                    onDetailClick = { topLevelBackStack.add(HomeDetail) },
                                    modifier = screenModifier
                                )
                            }
                            entry<HomeDetail> {
                                HomeDetailScreen(
                                    onBackClick = { topLevelBackStack.removeLast() },
                                    modifier = screenModifier
                                )
                            }
                            entry<NoteList> {
                                NoteListScreen(
                                    onNoteClick = { id -> topLevelBackStack.add(NoteDetail(id)) },
                                    onCreateClick = { topLevelBackStack.add(NoteCreate) },
                                    modifier = screenModifier
                                )
                            }
                            entry<NoteDetail> { args ->
                                NoteDetailScreen(
                                    noteId = args.id,
                                    onBackClick = { topLevelBackStack.removeLast() },
                                    onEditClick = { topLevelBackStack.add(NoteEdit(args.id)) },
                                    modifier = screenModifier
                                )
                            }
                            entry<NoteEdit> { args ->
                                NoteEditScreen(
                                    noteId = args.id,
                                    onBackClick = { topLevelBackStack.removeLast() },
                                    onSaveClick = { topLevelBackStack.removeLast() },
                                    modifier = screenModifier
                                )
                            }
                            entry<NoteCreate> {
                                NoteCreateScreen(
                                    onBackClick = { topLevelBackStack.removeLast() },
                                    onNoteCreated = { id ->
                                        topLevelBackStack.replaceStack(NoteList, NoteDetail(id))
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

class TopLevelBackStack<T : NavKey>(private val startKey: T) {

    private var topLevelBackStacks: HashMap<T, SnapshotStateList<T>> = hashMapOf(
        startKey to mutableStateListOf(startKey)
    )

    var topLevelKey by mutableStateOf(startKey)
        private set

    val backStack = mutableStateListOf<T>(startKey)

    private fun updateBackStack() {
        backStack.clear()
        val currentStack = topLevelBackStacks[topLevelKey] ?: emptyList()

        if (topLevelKey == startKey) {
            backStack.addAll(currentStack)
        } else {
            val startStack = topLevelBackStacks[startKey] ?: emptyList()
            backStack.addAll(startStack + currentStack)
        }
    }

    fun switchTopLevel(key: T) {
        if (topLevelBackStacks[key] == null) {
            topLevelBackStacks[key] = mutableStateListOf(key)
        }
        topLevelKey = key
        updateBackStack()
    }

    fun add(key: T) {
        topLevelBackStacks[topLevelKey]?.add(key)
        updateBackStack()
    }

    fun removeLast() {
        val currentStack = topLevelBackStacks[topLevelKey] ?: return

        if (currentStack.size > 1) {
            currentStack.removeLastOrNull()
        } else if (topLevelKey != startKey) {
            topLevelKey = startKey
        }
        updateBackStack()
    }

    fun replaceStack(vararg keys: T) {
        topLevelBackStacks[topLevelKey] = mutableStateListOf(*keys)
        updateBackStack()
    }

}





