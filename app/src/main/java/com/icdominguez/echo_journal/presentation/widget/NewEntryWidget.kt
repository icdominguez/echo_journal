package com.icdominguez.echo_journal.presentation.widget

import androidx.compose.runtime.Composable
import androidx.glance.GlanceModifier
import androidx.glance.Image
import androidx.glance.ImageProvider
import androidx.glance.action.ActionParameters
import androidx.glance.action.actionParametersOf
import androidx.glance.action.actionStartActivity
import androidx.glance.action.clickable
import androidx.glance.appwidget.GlanceAppWidget
import androidx.glance.appwidget.GlanceAppWidgetReceiver
import androidx.glance.layout.Alignment
import androidx.glance.layout.Column
import androidx.glance.layout.wrapContentSize
import com.icdominguez.echo_journal.R
import com.icdominguez.echo_journal.common.Constants.IS_LAUNCHED_FROM_WIDGET
import com.icdominguez.echo_journal.presentation.MainActivity

object NewEntryWidget: GlanceAppWidget() {

    @Composable
    override fun Content() {
        Column(
            modifier = GlanceModifier
                .wrapContentSize(),
            verticalAlignment = Alignment.Vertical.CenterVertically,
            horizontalAlignment = Alignment.Horizontal.CenterHorizontally
        ) {
            Image(
                modifier = GlanceModifier.wrapContentSize()
                        .clickable(onClick = actionStartActivity<MainActivity>(
                            parameters = actionParametersOf(
                                pairs = arrayOf(ActionParameters.Key<Boolean>(IS_LAUNCHED_FROM_WIDGET) to true)
                            )
                        )
                    ),
                provider = ImageProvider(R.drawable.widget),
                contentDescription = null
            )
        }
    }
}

class NewEntryWidgetReceiver : GlanceAppWidgetReceiver() {
    override val glanceAppWidget: GlanceAppWidget
        get() = NewEntryWidget
}