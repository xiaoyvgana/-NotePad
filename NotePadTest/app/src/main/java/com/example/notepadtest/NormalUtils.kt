package com.example.notepadtest

import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

object NormalUtils {



    fun formatTimestampToYYMMDD(timestamp: Long): String {
        val dateFormat = SimpleDateFormat("yy-MM-dd hh-mm", Locale.getDefault())
        return dateFormat.format(Date(timestamp))
    }

}