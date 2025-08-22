package com.project.repository.imageupload

import android.content.Context

class ImageLocalRepositoryImpl private constructor(private val context: Context) {
    companion object {
        private var INSTANCE: ImageLocalRepositoryImpl? = null

        fun getInstance(context: Context): ImageLocalRepositoryImpl {
            return INSTANCE ?: synchronized(this) {
                INSTANCE ?: ImageLocalRepositoryImpl(context).also { INSTANCE = it }
            }
        }
    }
}