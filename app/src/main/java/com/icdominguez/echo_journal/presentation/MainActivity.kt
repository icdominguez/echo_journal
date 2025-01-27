package com.icdominguez.echo_journal.presentation

import android.os.Bundle
import android.view.WindowInsetsController
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.icdominguez.echo_journal.common.Constants.IS_LAUNCHED_FROM_WIDGET
import com.icdominguez.echo_journal.presentation.designsystem.theme.EchoJournalTheme
import com.icdominguez.echo_journal.presentation.navigation.Navigation
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        installSplashScreen()
        enableEdgeToEdge()

        val window = this.window
        window.insetsController?.setSystemBarsAppearance(
            WindowInsetsController.APPEARANCE_LIGHT_STATUS_BARS,
            WindowInsetsController.APPEARANCE_LIGHT_STATUS_BARS
        )

        setContent {
            val isLaunchedFromWidget = intent.getBooleanExtra(IS_LAUNCHED_FROM_WIDGET, false)

            EchoJournalTheme {
                Navigation(isLaunchedFromWidget = isLaunchedFromWidget)
            }
        }
    }
}
