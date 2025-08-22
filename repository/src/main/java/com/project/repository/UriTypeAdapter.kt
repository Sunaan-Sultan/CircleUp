package com.project.repository

import android.net.Uri
import com.google.gson.TypeAdapter
import com.google.gson.stream.JsonReader
import com.google.gson.stream.JsonWriter

class UriTypeAdapter : TypeAdapter<Uri?>() {
    override fun write(out: JsonWriter, value: Uri?) {
        // Write the URI as its string form (or null)
        out.value(value?.toString())
    }

    override fun read(reader: JsonReader): Uri? {
        // Read the next JSON string and parse it back to a Uri
        val s = reader.nextString()
        return if (s.isNullOrEmpty()) null else Uri.parse(s)
    }
}