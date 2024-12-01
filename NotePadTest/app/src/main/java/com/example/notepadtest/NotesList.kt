package com.example.notepadtest

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

/*
首页（笔记列表）
 */
class NotesList : AppCompatActivity() {

    private lateinit var dbHelper: NotesDatabaseHelper
    private lateinit var adapter: NotesAdapter
    private var notes = listOf<Note>()
    private lateinit var recyclerView: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        dbHelper = NotesDatabaseHelper(this)

        adapter = NotesAdapter(
            notes = mutableListOf(),
            onNoteClicked = { note ->
                val intent = Intent(this, EditNoteActivity::class.java)
                intent.putExtra("noteId", note.id)
                startActivity(intent)
            },
            onCheckChanged = { note,ischeck ->
                val updatedNote = note.copy(isDone = ischeck)
                dbHelper.updateNote(updatedNote) // 更新数据库
                if (ischeck)Toast.makeText(this,"已完成代办",Toast.LENGTH_LONG).show()
            }
        )

        recyclerView = findViewById(R.id.recyclerView)

        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = adapter

        findViewById<EditText>(R.id.searchEditText).addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                searchNotes(s.toString())
            }

            override fun afterTextChanged(s: Editable?) {}
        })

        findViewById<TextView>(R.id.addButton).setOnClickListener {
            val intent = Intent(this, EditNoteActivity::class.java)
            startActivity(intent)
        }
    }

    override fun onResume() {
        super.onResume()
        loadNotes()
    }

    private fun loadNotes() {
        notes = dbHelper.getAllNotes()
        recyclerView.post {
            adapter.updateNotes(notes)
        }
    }

    private fun searchNotes(query: String) {
        notes = dbHelper.searchNotes(query)
        recyclerView.post {
            adapter.updateNotes(notes)
        }
    }
}
