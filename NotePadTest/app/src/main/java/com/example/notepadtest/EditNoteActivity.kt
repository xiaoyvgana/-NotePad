package com.example.notepadtest

import android.content.Context
import android.content.SharedPreferences
import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatTextView
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

/*
编辑页面
 */
class EditNoteActivity : AppCompatActivity() {

    private lateinit var editTextTitle: EditText
    private lateinit var editTextNote: EditText
    private lateinit var noteType: String
    private lateinit var bgColorSpinner: Spinner
    private lateinit var fontSizeSpinner: Spinner
    private lateinit var typeSpinner: Spinner
    private lateinit var btnSave: TextView
    private lateinit var btnDelete: TextView
    private lateinit var sharedPreferences: SharedPreferences
    private lateinit var dbHelper: NotesDatabaseHelper

    private var noteId: Long = -1 // 用于标识当前编辑的笔记（-1表示新笔记）

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_note)

        editTextTitle = findViewById(R.id.editTextTitle)
        editTextNote = findViewById(R.id.editTextNote)
        bgColorSpinner = findViewById(R.id.bgColorSpinner)
        fontSizeSpinner = findViewById(R.id.fontSizeSpinner)
        typeSpinner = findViewById(R.id.typeSpinner)
        btnSave = findViewById(R.id.btnSave)
        btnDelete = findViewById(R.id.btnDelete)

        dbHelper = NotesDatabaseHelper(this)
        sharedPreferences = getSharedPreferences("EditorPreferences", Context.MODE_PRIVATE)

        // 初始化编辑器设置
        initEditorSettings()

        // 检查是否是编辑已有笔记
        noteId = intent.getLongExtra("noteId", -1)
        if (noteId != -1L) {
            loadNote()
        }

        // 保存笔记
        btnSave.setOnClickListener {
            saveNote()
        }

        // 删除笔记
        btnDelete.setOnClickListener {
            deleteNote()
        }

        // 监听背景颜色选择
        bgColorSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                val color = when (position) {
                    0 -> Color.WHITE
                    1 -> Color.LTGRAY
                    2 -> Color.YELLOW
                    else -> Color.WHITE
                }
                editTextNote.setBackgroundColor(color)
                saveBackgroundColorPreference(position)
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {}
        }

        // 监听字体大小选择
        fontSizeSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                val textSize = when (position) {
                    0 -> 14f
                    1 -> 18f
                    2 -> 22f
                    else -> 18f
                }
                editTextNote.textSize = textSize
                saveFontSizePreference(position)
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {}
        }
        typeSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                noteType =when(position){
                    0->"学习"
                    1->"工作"
                    2->"生活"
                    else ->"其他"
                }
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {}
        }
    }

    private fun initEditorSettings() {
        val bgColorPosition = sharedPreferences.getInt("bg_color_position", 0)
        bgColorSpinner.setSelection(bgColorPosition)
        editTextNote.setBackgroundColor(
            when (bgColorPosition) {
                0 -> Color.WHITE
                1 -> Color.LTGRAY
                2 -> Color.YELLOW
                else -> Color.WHITE
            }
        )

        val fontSizePosition = sharedPreferences.getInt("font_size_position", 1)
        fontSizeSpinner.setSelection(fontSizePosition)
        editTextNote.textSize = when (fontSizePosition) {
            0 -> 14f
            1 -> 18f
            2 -> 22f
            else -> 18f
        }
    }

    private fun saveBackgroundColorPreference(position: Int) {
        sharedPreferences.edit().putInt("bg_color_position", position).apply()
    }

    private fun saveFontSizePreference(position: Int) {
        sharedPreferences.edit().putInt("font_size_position", position).apply()
    }


    private fun loadNote() {
        val notes = dbHelper.getAllNotes() // 查询所有笔记
        val note = notes.find { it.id == noteId } // 找到指定的笔记
        note?.let {
            editTextTitle.setText(it.title)
            editTextNote.setText(it.content)
            val pos = when(it.type){
                "学习"->0
                "工作"->1
                "生活"->2
                else->0
            }
            typeSpinner.setSelection(pos)
        }
    }

    private fun saveNote() {
        val title = editTextTitle.text.toString().trim()
        val content = editTextNote.text.toString().trim()
        if (title.isEmpty() || content.isEmpty()) {
            Toast.makeText(this, "Title and content cannot be empty!", Toast.LENGTH_SHORT).show()
            return
        }

        if (noteId == -1L) {
            dbHelper.insertNote(Note(0, title, content, NormalUtils.formatTimestampToYYMMDD(System.currentTimeMillis()), noteType,false))
            Toast.makeText(this, "Note saved!", Toast.LENGTH_SHORT).show()
        } else {
            dbHelper.updateNote(Note(noteId, title, content, NormalUtils.formatTimestampToYYMMDD(System.currentTimeMillis()), noteType,false))
            Toast.makeText(this, "Note updated!", Toast.LENGTH_SHORT).show()
        }
        finish()
    }

    private fun deleteNote() {
        if (noteId != -1L) {
            dbHelper.deleteNoteById(noteId)
            Toast.makeText(this, "Note deleted!", Toast.LENGTH_SHORT).show()
        }
        finish()
    }
}

