package dev.tuklu.dumpertripcalculator

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.*
import dev.tuklu.dumpertripcalculator.data.prefs.readIsOnboardingComplete
import dev.tuklu.dumpertripcalculator.ui.nav.NavGraph
import dev.tuklu.dumpertripcalculator.ui.screen.OnboardingScreen
import dev.tuklu.dumpertripcalculator.ui.theme.DumperTripCalculatorTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            DumperTripCalculatorTheme {
                val onboardingComplete by readIsOnboardingComplete(this)
                    .collectAsState(initial = null)

                when (onboardingComplete) {
                    null -> Unit
                    false -> OnboardingScreen(onComplete = { recreate() })
                    true -> NavGraph()
                }
            }
        }
    }
}
