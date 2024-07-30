package com.rmtz.calendar.model

import android.annotation.SuppressLint
import android.os.Parcel
import android.os.Parcelable
import kotlinx.serialization.Serializable


@Serializable
sealed interface ImageState{
    @Serializable
    data object Loading : ImageState

    @Serializable
    data class Success(val url : String) : ImageState
}