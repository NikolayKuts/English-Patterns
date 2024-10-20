package com.example.englishpatterns.data

import kotlinx.serialization.SerializationException
import kotlinx.serialization.json.Json
import java.io.InputStream
import java.io.OutputStream

@Suppress("BlockingMethodInNonBlockingContext")
class PatternHoldersSerializer : androidx.datastore.core.Serializer<RowPatternGroupHolders> {

    override val defaultValue: RowPatternGroupHolders = RowPatternGroupHolders()

    override suspend fun readFrom(input: InputStream): RowPatternGroupHolders {
        return try {
            Json.decodeFromString(
                deserializer = RowPatternGroupHolders.serializer(),
                string = input.readBytes().decodeToString()
            )
        } catch (e: SerializationException) {
            e.printStackTrace()
            RowPatternGroupHolders()
        }
    }

    override suspend fun writeTo(t: RowPatternGroupHolders, output: OutputStream) {
        output.write(
            Json.encodeToString(
                serializer = RowPatternGroupHolders.serializer(),
                value = t
            ).encodeToByteArray()
        )
    }
}