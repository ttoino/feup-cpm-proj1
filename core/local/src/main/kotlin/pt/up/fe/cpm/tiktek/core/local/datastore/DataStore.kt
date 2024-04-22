package pt.up.fe.cpm.tiktek.core.local.datastore

import androidx.datastore.core.Serializer
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.decodeFromStream
import kotlinx.serialization.json.encodeToStream
import java.io.InputStream
import java.io.OutputStream

@OptIn(ExperimentalSerializationApi::class)
internal inline fun <reified T> serializer(defaultValue: T) =
    object : Serializer<T> {
        override val defaultValue = defaultValue

        override suspend fun readFrom(input: InputStream): T = Json.decodeFromStream<T>(input)

        override suspend fun writeTo(
            t: T,
            output: OutputStream,
        ) = Json.encodeToStream(t, output)
    }
