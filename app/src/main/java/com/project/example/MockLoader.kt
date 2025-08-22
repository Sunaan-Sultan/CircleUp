package com.project.example

import android.annotation.SuppressLint
import android.content.Context

class MockLoader(private val context: Context) {

    var isLoaded = false
        private set

    fun init() {
        if (isLoaded) return


        isLoaded = true
    }

    @SuppressLint("DiscouragedApi")
    fun loadJson(fileName: String): String {
        val packageName = context.packageName
        val resourceId = context.resources.getIdentifier(fileName, "raw", packageName)
        val inputStream = context.resources.openRawResource(resourceId)
        return inputStream.bufferedReader().use { it.readText() }
    }
}

