package com.project.service

object RuntimeProfile {
    const val MOCK_RUNTIME: Int = 1
    const val LIVE_RUNTIME: Int = 2

    var runtime: Int = MOCK_RUNTIME

    fun getCurrentRuntime(): Int {
        return runtime
    }

    fun setCurrentRuntime(rt: Int) {
        this.runtime = rt
    }
}