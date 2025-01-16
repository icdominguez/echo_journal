package com.icdominguez.echo_journal.presentation.screens

import com.icdominguez.echo_journal.data.model.TopicEntity
import com.icdominguez.echo_journal.presentation.model.Entry
import com.icdominguez.echo_journal.presentation.screens.createrecord.model.Moods
import java.time.LocalDateTime

class FakeData {
    companion object {
        val topicsEntries: List<TopicEntity> = listOf(
            TopicEntity(name = "Work"),
            TopicEntity(name = "Friends"),
            TopicEntity(name = "Family"),
            TopicEntity(name = "Love"),
            TopicEntity(name = "Surprise"),
            TopicEntity(name = "Meditation"),
            TopicEntity(name = "Nature"),
            TopicEntity(name = "Stress Management"),
            TopicEntity(name = "Routine"),
            TopicEntity(name = "Travel"),
            TopicEntity(name = "Adventure"),
            TopicEntity(name = "Goodbyes"),
            TopicEntity(name = "Friendship")
        )

        val timelineEntries: List<Entry> = listOf(
            Entry(
                entryId = 0,
                mood = Moods.SAD,
                title = "Hoy estoy triste",
                description = "Ha acabado el año y me eencuentro un poco mal, porque tengo miedo de que empiece algo nuevo y no sea capaz de mejorar y siga estancado. Este 2024 me ha enseñado muchas cosas y no me voy a rendir. Todo se puede conseguir con esfuerzo y disciplina",
                filePath = "",
                topics = listOf()
            ),
            Entry(
                entryId = 0,
                mood = Moods.PEACEFUL,
                title = "Hoy me encuentro chill",
                description = "Día de paz y tranquilidad sin hacer nadaaa",
                filePath = "",
                topics = listOf(topicsEntries[0].name, topicsEntries[1].name),
            ),
            Entry(
                entryId = 0,
                mood = Moods.STRESSED,
                title = "Luna agobiada",
                description = "No le da tiempo a entregar su tarea de Lengua por su nuevo Ipad Air de 156 GB con M2 y de color Morado con un texto detrás que pone Luna Unicornio Dedos de Rock",
                filePath = "",
                topics = listOf(topicsEntries[2].name, topicsEntries[1].name, topicsEntries[3].name, topicsEntries[8].name),
                date = LocalDateTime.now().minusDays(1)
            ),
            Entry(
                entryId = 0,
                mood = Moods.PEACEFUL,
                title = "Andrés está totalmente chill de cojones",
                description = "Está programando la nueva funcionalidad de filtrar por el filter chip de forma excepcional",
                filePath = "",
                topics = listOf(topicsEntries[0].name, topicsEntries[3].name),
                date = LocalDateTime.now().minusDays(23)
            ),
            Entry(
                entryId = 0,
                mood = Moods.EXCITED,
                title = "Hoy estoy estresado",
                description = "",
                filePath = "",
                topics = listOf(topicsEntries[1].name, topicsEntries[3].name, topicsEntries[4].name),
            ),
            Entry(
                entryId = 1,
                mood = Moods.NEUTRAL,
                title = "A Calm Morning",
                description = "I woke up early and meditated by the lake.",
                filePath = "",
                topics = listOf(topicsEntries[5].name, topicsEntries[6].name)
            ),
            Entry(
                entryId = 2,
                mood = Moods.STRESSED,
                title = "Deadline Pressure",
                description = "The project deadline is tomorrow, and I'm overwhelmed.",
                filePath = "",
                topics = listOf(topicsEntries[0].name, topicsEntries[7].name),
                date = LocalDateTime.now().minusDays(1)
            ),
            Entry(
                entryId = 3,
                mood = Moods.STRESSED,
                title = "A Regular Day",
                description = "Nothing much happened today. Just a usual day at work.",
                filePath = "",
                topics = listOf(topicsEntries[8].name),
                date = LocalDateTime.now().minusDays(3)
            ),
            Entry(
                entryId = 4,
                mood = Moods.PEACEFUL,
                title = "New Adventure",
                description = "I started planning for my trip to the mountains!",
                filePath = "",
                topics = listOf(topicsEntries[9].name, topicsEntries[10].name),
                date = LocalDateTime.now().minusDays(1)
            ),
            Entry(
                entryId = 5,
                mood = Moods.PEACEFUL,
                title = "A Difficult Goodbye",
                description = "I had to say goodbye to an old friend today.",
                filePath = "",
                topics = listOf(topicsEntries[11].name, topicsEntries[12].name),
                date = LocalDateTime.now().minusDays(2)
            )
        )

    }
}