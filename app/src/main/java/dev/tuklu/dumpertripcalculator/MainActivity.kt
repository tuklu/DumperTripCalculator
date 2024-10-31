package dev.tuklu.dumpertripcalculator

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import dev.tuklu.dumpertripcalculator.ui.theme.DumperTripCalculatorTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            DumperTripCalculatorTheme {
                DumperCalculator()
            }
        }
    }
}