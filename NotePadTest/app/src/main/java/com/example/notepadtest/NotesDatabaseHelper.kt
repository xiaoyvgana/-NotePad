package com.example.notepadtest

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import androidx.core.database.getBlobOrNull

class NotesDatabaseHelper(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    companion object {
        const val DATABASE_NAME = "Notes.db"
        const val DATABASE_VERSION = 1

        const val TABLE_NAME = "notes"
        const val COLUMN_ID = "_id"
        const val COLUMN_TITLE = "title"
        const val COLUMN_TYPE = "type"
        const val COLUMN_CONTENT = "content"
        const val COLUMN_MODIFICATION_DATE = "modification_date"
        const val COLUMN_NAME_IS_DONE = "isdone"
    }

    override fun onCreate(db: SQLiteDatabase) {
        val createTable = """
            CREATE TABLE $TABLE_NAME (
                $COLUMN_ID INTEGER PRIMARY KEY AUTOINCREMENT,
                $COLUMN_TITLE TEXT,
                $COLUMN_TYPE TEXT,
                $COLUMN_CONTENT TEXT,
                $COLUMN_MODIFICATION_DATE TEXT,
                $COLUMN_NAME_IS_DONE INTEGER DEFAULT 0)
        """
        db.execSQL(createTable)
    }




    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS $TABLE_NAME")
        onCreate(db)
    }

    // 插入笔记
    fun insertNote(note: Note): Long {
        val db = writableDatabase
        val values = ContentValues().apply {
            put(COLUMN_TITLE, note.title)
            put(COLUMN_TYPE, note.type)
            put(COLUMN_CONTENT, note.content)
            put(COLUMN_MODIFICATION_DATE, note.modificationDate)
            put(COLUMN_NAME_IS_DONE, if(note.isDone)1 else 0)
        }
        return db.insert(TABLE_NAME, null, values)
    }

    // 获取所有笔记
    fun getAllNotes(): List<Note> {
        val db = readableDatabase
        val cursor = db.query(TABLE_NAME, null, null, null, null, null, "$COLUMN_MODIFICATION_DATE DESC")
        val notes = mutableListOf<Note>()

        while (cursor.moveToNext()) {
            notes.add(
                Note(
                    id = cursor.getLong(cursor.getColumnIndexOrThrow(COLUMN_ID)),
                    title = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_TITLE)),
                    content = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_CONTENT)),
                    modificationDate = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_MODIFICATION_DATE)),
                    type = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_TYPE)),
                    isDone =  cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_NAME_IS_DONE))==1
            )
            )
        }
        cursor.close()
        return notes
    }

    // 更新笔记
    fun updateNote(note: Note) {
        val db = writableDatabase
        val values = ContentValues().apply {
            put(COLUMN_TITLE, note.title)
            put(COLUMN_TYPE, note.type)
            put(COLUMN_CONTENT, note.content)
            put(COLUMN_MODIFICATION_DATE, note.modificationDate)
            put(COLUMN_NAME_IS_DONE, if(note.isDone)1 else 0)
        }
        db.update(TABLE_NAME, values, "$COLUMN_ID = ?", arrayOf(note.id.toString()))
    }

    // 删除笔记
    fun deleteNoteById(id: Long) {
        val db = writableDatabase
        db.delete(TABLE_NAME, "$COLUMN_ID = ?", arrayOf(id.toString()))
    }
    fun getNoteById(noteId: Long): Note? {
        val db = readableDatabase
        // 确保查询的列是正确的，并且查询条件正确
        val cursor = db.query(
            TABLE_NAME, // 表名
            arrayOf(
                COLUMN_ID,
                COLUMN_TITLE,
                COLUMN_CONTENT,
                COLUMN_MODIFICATION_DATE,
                COLUMN_NAME_IS_DONE
            ), // 查询的列
            "$COLUMN_ID = ?", // 查询条件
            arrayOf(noteId.toString()), // 查询条件的参数
            null, null, null // 分组、排序等
        )

        var note: Note? = null
        if (cursor != null && cursor.moveToFirst()) {
            val id = cursor.getLong(cursor.getColumnIndexOrThrow(COLUMN_ID))
            val title = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_TITLE))
            val type = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_TYPE))
            val content = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_CONTENT))
            val modificationDate = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_MODIFICATION_DATE))
            val isDone = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_NAME_IS_DONE)) == 1

            // 构建 Note 对象
            note = Note(id, title, content, modificationDate,type, isDone)
        }
        cursor?.close()
        db.close()
        return note
    }



    // 搜索笔记
    fun searchNotes(query: String): List<Note> {
        val db = readableDatabase
        val cursor = db.query(
            TABLE_NAME,
            null,
            "$COLUMN_TITLE LIKE ?",
            arrayOf("%$query%"),
            null,
            null,
            "$COLUMN_MODIFICATION_DATE DESC"
        )
        val notes = mutableListOf<Note>()
        while (cursor.moveToNext()) {
            notes.add(
                Note(
                    id = cursor.getLong(cursor.getColumnIndexOrThrow(COLUMN_ID)),
                    title = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_TITLE)),
                    content = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_CONTENT)),
                    modificationDate = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_MODIFICATION_DATE)),
                    type = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_TYPE)),
                    isDone = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_NAME_IS_DONE)) == 1
                )
            )
        }
        cursor.close()
        return notes
    }
}
