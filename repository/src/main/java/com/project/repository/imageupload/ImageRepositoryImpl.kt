package com.project.repository.imageupload

import com.project.models.imageupload.ImageUploadResponse
import com.project.models.imageupload.ImageRepository
import com.project.repository.RetrofitClient
import com.project.repository.SessionManager
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import java.io.File

class ImageRepositoryImpl : ImageRepository {
    override suspend fun uploadProfileImage(username: String, imageFile: File): Boolean {
        // prepare parts
        val userRb  = RequestBody.create("text/plain".toMediaType(), username)
        val imageRb = RequestBody.create("image/*".toMediaType(), imageFile)
        val part    = MultipartBody.Part.createFormData("image", imageFile.name, imageRb)

        // call API
        val token = SessionManager.accessToken ?: return false
        val resp  = RetrofitClient.imageUpload.uploadProfileImage("Bearer $token", userRb, part)

        // treat as success if HTTP 200
        return resp.code() == 200
    }
}

