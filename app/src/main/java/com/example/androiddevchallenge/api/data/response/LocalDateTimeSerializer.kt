package com.example.androiddevchallenge.api.data.response

import kotlinx.serialization.KSerializer
import kotlinx.serialization.descriptors.PrimitiveKind
import kotlinx.serialization.descriptors.PrimitiveSerialDescriptor
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneId

object LocalDateTimeSerializer : KSerializer<LocalDateTime> {
    override val descriptor: SerialDescriptor = PrimitiveSerialDescriptor(
        "Date",
        PrimitiveKind.LONG
    )

    override fun serialize(encoder: Encoder, value: LocalDateTime) = throw NotImplementedError()
    override fun deserialize(decoder: Decoder): LocalDateTime = LocalDateTime.ofInstant(
        Instant.ofEpochMilli(decoder.decodeLong()),
        ZoneId.systemDefault()
    )
}