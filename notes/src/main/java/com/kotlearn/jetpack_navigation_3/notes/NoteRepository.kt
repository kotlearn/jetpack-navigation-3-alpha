package com.kotlearn.jetpack_navigation_3.notes

object NoteRepository {
    private val notes = mutableListOf(
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

    fun getAllNotes(): List<Note> = notes

    fun getNoteById(id: Long): Note? = notes.find { it.id == id }

    fun updateNote(note: Note) {
        val index = notes.indexOfFirst { it.id == note.id }
        if (index != -1) {
            notes[index] = note
        }
    }

    fun createNote(title: String, content: String): Note {
        val id = (notes.maxOfOrNull { it.id } ?: 0L) + 1
        val note = Note(id, title, content)
        notes.add(note)
        return note
    }
}