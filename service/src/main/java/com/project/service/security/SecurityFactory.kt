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
        return if (name == "UTBL") {
            IdentityServiceUTBL()
        } else {
            IdentityServiceImpl(context)
        }
    }

//    fun getIdentityService(): IdentityService {
//        return getIdentityService("KEY")
//    }

    fun getIdentityRepository(context: Context): IdentityRepository {
        if (RuntimeProfile.getCurrentRuntime() == LIVE_RUNTIME) {
            return IdentityRepositoryImpl()
        } else {
            return IdentityLocalRepositoryImpl(context)
        }
    }
}
