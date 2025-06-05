package com.kotlearn.jetpack_navigation_3.notes

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.update

object NoteRepository {

    private val _notes = MutableStateFlow(
        listOf(
            Note(
                id = 1,
                title = "Meeting Notes",
                content = "Discuss project roadmap and milestones."
            ),
            Note(
                id = 2,
                title = "Shopping List",
                content = "Milk, Eggs, Bread, Butter"
            ),
            Note(
                id = 3,
                title = "Kotlearn Video Ideas",
                content = "Finish the MinesweeperK series!"
            )
        )
    )
    val notes = _notes.asStateFlow()

    fun getNoteById(id: Long): Flow<Note?> = notes.map { it.find { it.id == id } }

    fun getNoteByIdImmediate(id: Long): Note? = notes.value.find { it.id == id }

    fun updateNote(note: Note) {
        _notes.update { notes ->
            notes.toMutableList().apply {
                val index = indexOfFirst { it.id == note.id }
                if (index != -1) {
                    this[index] = note
                }
            }.toList()
        }
    }

    fun createNote(title: String, content: String): Note {
        val id = (notes.value.maxOfOrNull { it.id } ?: 0L) + 1
        val note = Note(id, title, content)
        _notes.update { notes ->
            notes.toMutableList().apply {
                add(note)
            }.toList()
        }
        return note
    }
}