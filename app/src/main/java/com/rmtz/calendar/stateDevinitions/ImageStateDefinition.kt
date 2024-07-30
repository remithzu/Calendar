package com.rmtz.calendar.stateDevinitions

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.dataStore
import androidx.datastore.dataStoreFile
import androidx.glance.state.GlanceStateDefinition
import com.rmtz.calendar.model.ImageState
import java.io.File

object ImageStateDefinition: GlanceStateDefinition<ImageState> {
    private val DATA_STORE_FILENAME = "imageState"
    private val Context.datastore by dataStore(DATA_STORE_FILENAME, ImageStateSerializer)
    override suspend fun getDataStore(context: Context, fileKey: String): DataStore<ImageState> {
        return context.datastore
    }

    override fun getLocation(context: Context, fileKey: String): File {
        return context.dataStoreFile(DATA_STORE_FILENAME)
    }
}