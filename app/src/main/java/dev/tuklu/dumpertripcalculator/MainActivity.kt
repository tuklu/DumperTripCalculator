package dev.tuklu.dumpertripcalculator

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.*
import dev.tuklu.dumpertripcalculator.data.prefs.readIsOnboardingComplete
import dev.tuklu.dumpertripcalculator.ui.screen.OnboardingScreen
import dev.tuklu.dumpertripcalculator.ui.theme.DumperTripCalculatorTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            DumperTripCalculatorTheme {
                val context = this
                val onboardingComplete by readIsOnboardingComplete(context)
                    .collectAsState(initial = null)

                when (onboardingComplete) {
                    null -> Unit // blank surface while loading — avoids flash of wrong screen
                    false -> OnboardingScreen(onComplete = { recreate() })
                    true -> DumperCalculator()
                }
            }
        }
    }
}
