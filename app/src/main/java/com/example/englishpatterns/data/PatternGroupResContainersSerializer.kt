package com.example.englishpatterns.data

import androidx.datastore.core.Serializer
import com.example.englishpatterns.presentation.patternPractisingScreen.PracticingPatternGroup
import kotlinx.serialization.SerializationException
import kotlinx.serialization.json.Json
import java.io.InputStream
import java.io.OutputStream

@Suppress("BlockingMethodInNonBlockingContext")
class PatternGroupResContainersSerializer : Serializer<PatternGroupResContainers> {

    override val defaultValue: PatternGroupResContainers = PatternGroupResContainers.Default

    override suspend fun readFrom(input: InputStream): PatternGroupResContainers {
        return try {
            Json.decodeFromString(
                deserializer = PatternGroupResContainers.serializer(),
                string = input.readBytes().decodeToString()
            )
        } catch (e: SerializationException) {
            e.printStackTrace()
            PatternGroupResContainers.Default
        }
    }

    override suspend fun writeTo(t: PatternGroupResContainers, output: OutputStream) {
        output.write(
            Json.encodeToString(
                serializer = PatternGroupResContainers.serializer(),
                value = t
            ).encodeToByteArray()
        )
    }
}

@Suppress("BlockingMethodInNonBlockingContext")
class WeekPatternGroupResContainersSerializer : Serializer<PracticingPatternGroup> {

    override val defaultValue: PracticingPatternGroup = PracticingPatternGroup()

    override suspend fun readFrom(input: InputStream): PracticingPatternGroup {
        return try {
            Json.decodeFromString(
                deserializer = PracticingPatternGroup.serializer(),
                string = input.readBytes().decodeToString()
            )
        } catch (e: SerializationException) {
            e.printStackTrace()
            PracticingPatternGroup()
        }
    }

    override suspend fun writeTo(t: PracticingPatternGroup, output: OutputStream) {
        output.write(
            Json.encodeToString(
                serializer = PracticingPatternGroup.serializer(),
                value = t
            ).encodeToByteArray()
        )
    }
}