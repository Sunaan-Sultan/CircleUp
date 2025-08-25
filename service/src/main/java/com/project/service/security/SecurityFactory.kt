package com.project.service.security

import android.content.Context
import com.project.models.security.IdentityRepository
import com.project.models.security.IdentityService
import com.project.repository.security.IdentityLocalRepositoryImpl
import com.project.repository.security.IdentityRepositoryImpl
import com.project.service.RuntimeProfile
import com.project.service.RuntimeProfile.LIVE_RUNTIME


object SecurityFactory {

    fun getIdentityService(context: Context, name: String): IdentityService {
           return IdentityServiceImpl(context)
    }

    fun getIdentityRepository(context: Context): IdentityRepository {
        return if (RuntimeProfile.getCurrentRuntime() == LIVE_RUNTIME) {
            IdentityRepositoryImpl()
        } else {
            IdentityLocalRepositoryImpl(context)
        }
    }
}
