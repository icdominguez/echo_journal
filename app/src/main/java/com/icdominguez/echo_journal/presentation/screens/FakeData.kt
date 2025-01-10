package com.icdominguez.echo_journal.presentation.screens

import com.icdominguez.echo_journal.data.model.EntryEntity

class FakeData {
    companion object {
        val timelineEntries: List<EntryEntity> = listOf(
            EntryEntity(
                entryId = 0,
                mood = "Sad",
                title = "Hoy estoy triste",
                description = "Ha acabado el año y me eencuentro un poco mal, porque tengo miedo de que empiece algo nuevo y no sea capaz de mejorar y siga estancado. Este 2024 me ha enseñado muchas cosas y no me voy a rendir. Todo se puede conseguir con esfuerzo y disciplina",
                filePath = "",
            ),
            EntryEntity(
                entryId = 0,
                mood = "Peaceful",
                title = "Hoy me encuentro chill",
                description = "Día de paz y tranquilidad sin hacer nadaaa",
                filePath = ""
            ),
            EntryEntity(
                entryId = 0,
                mood = "Stressed",
                title = "Luna agobiada",
                description = "No le da tiempo a entregar su tarea de Lengua por su nuevo Ipad Air de 156 GB con M2 y de color Morado con un texto detrás que pone Luna Unicornio Dedos de Rock",
                filePath = ""
            ),
            EntryEntity(
                entryId = 0,
                mood = "Peaceful",
                title = "Andrés está totalmente chill de cojones",
                description = "Está programando la nueva funcionalidad de filtrar por el filter chip de forma excepcional",
                filePath = ""
            ),
            EntryEntity(
                entryId = 0,
                mood = "Stressed",
                title = "Hoy estoy estresado",
                description = "Tengo 4 días para terminar dos trabajos y no se si me va a dar tiempo la verdad",
                filePath = ""
            ),
        )
    }
}