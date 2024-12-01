package com.example.notepadtest

import android.annotation.SuppressLint
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView

class NotesAdapter(
    private var notes: MutableList<Note>,
    private val onNoteClicked: (Note) -> Unit,
    private val onCheckChanged: (Note,Boolean) -> Unit
) : RecyclerView.Adapter<NotesAdapter.NoteViewHolder>() {

    inner class NoteViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val titleTextView: TextView = itemView.findViewById(R.id.titleTextView)
        val dateTextView: TextView = itemView.findViewById(R.id.dateTextView)
        val type: TextView = itemView.findViewById(R.id.noteType)
        val todoCheckBox: CheckBox = itemView.findViewById(R.id.todoCheckBox)
        val rootView: LinearLayout = itemView.findViewById(R.id.llRoot)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_note, parent, false)
        return NoteViewHolder(view)
    }

    override fun onBindViewHolder(holder: NoteViewHolder, position: Int) {
        val note = notes[position]
        holder.titleTextView.text = note.title
        holder.dateTextView.text = note.modificationDate
        holder.todoCheckBox.isChecked = note.isDone
        holder.type.text = note.type
        if (note.isDone) holder.rootView.setBackgroundColor(Color.parseColor("#D4E2E6"))
        else holder.rootView.setBackgroundColor(Color.parseColor("#FFFFFF"))

        holder.itemView.setOnClickListener {
            onNoteClicked(note)
        }

        holder.todoCheckBox.setOnCheckedChangeListener { _, isChecked ->

            onCheckChanged(note,isChecked)
            if (isChecked) holder.rootView.setBackgroundColor(Color.parseColor("#D4E2E6"))
            else holder.rootView.setBackgroundColor(Color.parseColor("#FFFFFF"))
        }
    }

    override fun getItemCount(): Int = notes.size

    @SuppressLint("NotifyDataSetChanged")
    fun updateNotes(newNotes: List<Note>) {
        notes.clear()
        notes.addAll(newNotes)
        notifyDataSetChanged()
    }
}

