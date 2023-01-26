package com.example.network

import androidx.fragment.app.Fragment

fun NetworkService.uiUpdater(): UiUpdater {
    return MainActivity.MainCompanion
}

interface UiUpdater {
    fun updatePictureRecycler()
}