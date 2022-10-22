package com.example.englishpatterns.data

import kotlinx.serialization.SerializationException
import kotlinx.serialization.json.Json
import java.io.InputStream
import java.io.OutputStream


@Suppress("BlockingMethodInNonBlockingContext")
class PatternHoldersSerializer : androidx.datastore.core.Serializer<PatternHolders> {

    override val defaultValue: PatternHolders = PatternHolders()

    override suspend fun readFrom(input: InputStream): PatternHolders {
        return try {
            Json.decodeFromString(
                deserializer = PatternHolders.serializer(),
                string = input.readBytes().decodeToString()
            )
        } catch (e: SerializationException) {
            e.printStackTrace()
            PatternHolders()
        }
    }

    override suspend fun writeTo(t: PatternHolders, output: OutputStream) {
        output.write(
            Json.encodeToString(
                serializer = PatternHolders.serializer(),
                value = t
            ).encodeToByteArray()
        )
    }
}