package com.project.service.imageupload

import android.content.Context
import com.project.models.imageupload.ImageRepository
import com.project.models.imageupload.ImageService
import com.project.repository.imageupload.ImageRepositoryImpl
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.File

class ImageServiceImpl(context: Context) : ImageService {
    private val repo: ImageRepository = ImageRepositoryImpl()

    override suspend fun uploadProfileImage(username: String, imageFile: File): Boolean =
        withContext(Dispatchers.IO) {
            repo.uploadProfileImage(username, imageFile)
        }
}