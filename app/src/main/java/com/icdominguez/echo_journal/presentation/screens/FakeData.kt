package com.icdominguez.echo_journal.presentation.screens

import com.icdominguez.echo_journal.data.model.EntryEntity

class FakeData {
    companion object {
        val timelineEntries: Array<EntryEntity> = arrayOf(
            EntryEntity(
                entryId = 0,
                mood = "SAD",
                title = "Hoy estoy triste",
                description = "Ha acabado el año y me eencuentro un poco mal, porque tengo miedo de que empiece algo nuevo y no sea capaz de mejorar y siga estancado. Este 2024 me ha enseñado muchas cosas y no me voy a rendir. Todo se puede conseguir con esfuerzo y disciplina",
                filePath = ""
            ),
            EntryEntity(
                entryId = 0,
                mood = "STRESSED",
                title = "Hoy estoy estresado",
                description = "Tengo 4 días para terminar dos trabajos y no se si me va a dar tiempo la verdad",
                filePath = ""
            ),
            EntryEntity(
                entryId = 0,
                mood = "PEACEFUL",
                title = "Hoy me encuentro chill",
                description = "Día de paz y tranquilidad sin hacer nadaaa",
                filePath = ""
            )
        )
    }
}