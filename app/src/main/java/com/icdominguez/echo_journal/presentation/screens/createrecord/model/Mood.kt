package com.icdominguez.echo_journal.presentation.screens.createrecord.model

import androidx.annotation.DrawableRes
import com.icdominguez.echo_journal.R

data class Mood(
    val name: String,
    @DrawableRes val selectedDrawable: Int,
    @DrawableRes val unselectedDrawable: Int,
    val selected: Boolean = false,
)

object Moods {
    private val NEUTRAL = Mood(
        name = "Neutral",
        selectedDrawable = R.drawable.neutral_mood_on,
        unselectedDrawable = R.drawable.neutral_mood_off,
    )
    private val SAD = Mood(
        name = "Sad",
        selectedDrawable = R.drawable.sad_mood_on,
        unselectedDrawable = R.drawable.sad_mood_off,
    )
    private val EXCITED = Mood(
        name = "Excited",
        selectedDrawable = R.drawable.excited_mood_on,
        unselectedDrawable = R.drawable.excited_mood_off,
    )
    private val STRESSED = Mood(
        name = "Stressed",
        selectedDrawable = R.drawable.stressed_mood_on,
        unselectedDrawable = R.drawable.stressed_mood_off,
    )
    private val PEACEFUL = Mood(
        name = "Peaceful",
        selectedDrawable = R.drawable.peaceful_mood_on,
        unselectedDrawable = R.drawable.peaceful_mood_off,
    )

    val allMods = listOf(STRESSED, SAD, NEUTRAL, PEACEFUL, EXCITED)
}
