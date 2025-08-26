package com.project.repository.imageupload

import com.project.models.imageupload.ImageRepository
import java.io.File

class ImageRepositoryImpl : ImageRepository {
    override suspend fun uploadProfileImage(
        username: String,
        imageFile: File
    ): Boolean {
        TODO("Not yet implemented")
    }

}

