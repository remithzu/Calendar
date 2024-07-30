package com.rmtz.calendar.stateDevinitions

import androidx.datastore.core.CorruptionException
import androidx.datastore.core.Serializer
import com.rmtz.calendar.model.ImageState
import com.rmtz.calendar.model.ImageState.Loading
import kotlinx.serialization.SerializationException
import kotlinx.serialization.json.Json
import java.io.InputStream
import java.io.OutputStream

object ImageStateSerializer : Serializer<ImageState> {
    override val defaultValue = Loading

    override suspend fun readFrom(input: InputStream): ImageState = try {
        Json.decodeFromString(
            ImageState.serializer(),
            input.readBytes().decodeToString()
        )
    } catch (exception: SerializationException) {
        throw CorruptionException("Could not read data: ${exception.message}")
    }

    override suspend fun writeTo(t: ImageState, output: OutputStream) {
        output.use {
            it.write(
                Json.encodeToString(ImageState.serializer(), t).encodeToByteArray()
            )
        }
    }
}