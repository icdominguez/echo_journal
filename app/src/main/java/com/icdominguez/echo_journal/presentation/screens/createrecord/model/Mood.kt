package com.icdominguez.echo_journal.presentation.screens.createrecord.model

import androidx.annotation.DrawableRes
import androidx.compose.ui.graphics.Color
import com.icdominguez.echo_journal.R
import com.icdominguez.echo_journal.presentation.designsystem.theme.ExcitedMood
import com.icdominguez.echo_journal.presentation.designsystem.theme.NeutralMood
import com.icdominguez.echo_journal.presentation.designsystem.theme.PeacefulMood
import com.icdominguez.echo_journal.presentation.designsystem.theme.SadMood
import com.icdominguez.echo_journal.presentation.designsystem.theme.StressedMood

data class Mood(
    val name: String,
    @DrawableRes val selectedDrawable: Int,
    @DrawableRes val unselectedDrawable: Int,
    val color: Color,
)

object Moods {
    val NEUTRAL = Mood(
        name = "Neutral",
        selectedDrawable = R.drawable.neutral_mood_on,
        unselectedDrawable = R.drawable.neutral_mood_off,
        color = NeutralMood,
    )
    val SAD = Mood(
        name = "Sad",
        selectedDrawable = R.drawable.sad_mood_on,
        unselectedDrawable = R.drawable.sad_mood_off,
        color = SadMood,
    )
    val EXCITED = Mood(
        name = "Excited",
        selectedDrawable = R.drawable.excited_mood_on,
        unselectedDrawable = R.drawable.excited_mood_off,
        color = ExcitedMood,
    )
    val STRESSED = Mood(
        name = "Stressed",
        selectedDrawable = R.drawable.stressed_mood_on,
        unselectedDrawable = R.drawable.stressed_mood_off,
        color = StressedMood,
    )
    val PEACEFUL = Mood(
        name = "Peaceful",
        selectedDrawable = R.drawable.peaceful_mood_on,
        unselectedDrawable = R.drawable.peaceful_mood_off,
        color = PeacefulMood,
    )

    val allMods = listOf(STRESSED, SAD, NEUTRAL, PEACEFUL, EXCITED)
}
