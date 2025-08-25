package com.project.repository.imageupload

import com.project.models.imageupload.ImageRepository
import com.project.repository.RetrofitClient
import com.project.repository.SessionManager
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import java.io.File

class ImageRepositoryImpl : ImageRepository {
    override suspend fun uploadProfileImage(
        username: String,
        imageFile: File
    ): Boolean {
        TODO("Not yet implemented")
    }

}

