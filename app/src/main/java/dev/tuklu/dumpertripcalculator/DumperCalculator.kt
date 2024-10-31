package dev.tuklu.dumpertripcalculator

import androidx.compose.animation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import java.util.Locale
import kotlin.math.ceil

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DumperCalculator() {
    val keyboardController = LocalSoftwareKeyboardController.current
    val focusManager = LocalFocusManager.current

    var isMetric by remember { mutableStateOf(false) }
    var showSettings by remember { mutableStateOf(false) }

    var lengthString by remember { mutableStateOf("") }
    var lengthError by remember { mutableStateOf("") }
    var widthString by remember { mutableStateOf("") }
    var widthError by remember { mutableStateOf("") }
    var heightString by remember { mutableStateOf("") }
    var heightError by remember { mutableStateOf("") }

    var totalVolume by remember { mutableFloatStateOf(0f) }
    var requiredTrips by remember { mutableIntStateOf(0) }
    var dumperCapacity by remember { mutableFloatStateOf(16f) }
    var costPerTrip by remember { mutableStateOf("3500") }
    var hasCalculated by remember { mutableStateOf(false) }

    fun validateField(value: String, fieldName: String): String? {
        return when {
            value.isEmpty() -> "$fieldName is required"
            value.toFloatOrNull() == null -> "$fieldName must be a valid number"
            value.toFloat() <= 0 -> "$fieldName must be greater than 0"
            else -> null
        }
    }

    fun validateInputs(): Boolean {
        lengthError = validateField(lengthString, "Length") ?: ""
        widthError = validateField(widthString, "Width") ?: ""
        heightError = validateField(heightString, "Height") ?: ""

        return lengthError.isEmpty() && widthError.isEmpty() && heightError.isEmpty()
    }

    fun calculateVolume() {
        keyboardController?.hide()
        focusManager.clearFocus()

        if (!validateInputs()) return

        val l = lengthString.toFloat()
        val w = widthString.toFloat()
        val h = heightString.toFloat()

        // Always calculate in cubic meters first
        totalVolume = if (isMetric) {
            l * w * h
        } else {
            l * w * h * 0.0283168f
        }

        requiredTrips = ceil(totalVolume / dumperCapacity).toInt()
        hasCalculated = true
    }

    fun resetCalculation() {
        hasCalculated = false
        lengthString = ""
        widthString = ""
        heightString = ""
        lengthError = ""
        widthError = ""
        heightError = ""
    }

    // Convert volume based on current unit selection
    val displayVolume = if (isMetric) {
        totalVolume
    } else {
        totalVolume / 0.0283168f // Convert back to cubic feet
    }

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // Header
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = Icons.Default.LocalShipping,
                    contentDescription = "Dumper Truck"
                )

                IconButton(onClick = { showSettings = true }) {
                    Icon(
                        imageVector = Icons.Default.Settings,
                        contentDescription = "Settings"
                    )
                }
            }

            Text(
                text = "Dumper Trip Calculator",
                style = MaterialTheme.typography.headlineMedium,
                color = MaterialTheme.colorScheme.primary
            )

            AnimatedVisibility(
                visible = !hasCalculated,
                enter = fadeIn() + expandVertically(),
                exit = fadeOut() + shrinkVertically()
            ) {
                Column(
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    // Unit toggle
                    Card(
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(16.dp),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = "Unit: ${if (isMetric) "Meters" else "Feet"}",
                                style = MaterialTheme.typography.titleMedium
                            )
                            Switch(
                                checked = isMetric,
                                onCheckedChange = { isMetric = it }
                            )
                        }
                    }

                    // Input fields
                    OutlinedTextField(
                        value = lengthString,
                        onValueChange = {
                            lengthString = it
                            lengthError = validateField(it, "Length") ?: ""
                        },
                        label = { Text("Length") },
                        keyboardOptions = KeyboardOptions(
                            keyboardType = KeyboardType.Decimal,
                            imeAction = ImeAction.Next
                        ),
                        modifier = Modifier.fillMaxWidth(),
                        isError = lengthError.isNotEmpty(),
                        supportingText = {
                            if (lengthError.isNotEmpty()) {
                                Text(lengthError, color = MaterialTheme.colorScheme.error)
                            }
                        }
                    )

                    OutlinedTextField(
                        value = widthString,
                        onValueChange = {
                            widthString = it
                            widthError = validateField(it, "Width") ?: ""
                        },
                        label = { Text("Width") },
                        keyboardOptions = KeyboardOptions(
                            keyboardType = KeyboardType.Decimal,
                            imeAction = ImeAction.Next
                        ),
                        modifier = Modifier.fillMaxWidth(),
                        isError = widthError.isNotEmpty(),
                        supportingText = {
                            if (widthError.isNotEmpty()) {
                                Text(widthError, color = MaterialTheme.colorScheme.error)
                            }
                        }
                    )

                    OutlinedTextField(
                        value = heightString,
                        onValueChange = {
                            heightString = it
                            heightError = validateField(it, "Height") ?: ""
                        },
                        label = { Text("Height") },
                        keyboardOptions = KeyboardOptions(
                            keyboardType = KeyboardType.Decimal,
                            imeAction = ImeAction.Done
                        ),
                        keyboardActions = KeyboardActions(
                            onDone = { calculateVolume() }
                        ),
                        modifier = Modifier.fillMaxWidth(),
                        isError = heightError.isNotEmpty(),
                        supportingText = {
                            if (heightError.isNotEmpty()) {
                                Text(heightError, color = MaterialTheme.colorScheme.error)
                            }
                        }
                    )

                    Button(
                        onClick = { calculateVolume() },
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Icon(
                            imageVector = Icons.Default.Check,
                            contentDescription = "Calculate"
                        )
                        Spacer(Modifier.width(8.dp))
                        Text("Calculate")
                    }
                }
            }

            // Results
            AnimatedVisibility(
                visible = hasCalculated,
                enter = fadeIn() + expandVertically(),
                exit = fadeOut() + shrinkVertically()
            ) {
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    verticalArrangement = Arrangement.spacedBy(16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    // Hero section for trips
                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        colors = CardDefaults.cardColors(
                            containerColor = MaterialTheme.colorScheme.primaryContainer
                        )
                    ) {
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(24.dp),
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            Text(
                                text = "Required Trips",
                                style = MaterialTheme.typography.titleMedium
                            )
                            Text(
                                text = "$requiredTrips",
                                style = MaterialTheme.typography.displayLarge,
                                color = MaterialTheme.colorScheme.primary,
                                fontWeight = FontWeight.Bold
                            )

                            val cost = costPerTrip.toFloatOrNull() ?: 0f
                            Text(
                                text = String.format(
                                    Locale.getDefault(),
                                    "Total Cost: ₹%.2f",
                                    requiredTrips * cost
                                ),
                                style = MaterialTheme.typography.titleMedium,
                                color = MaterialTheme.colorScheme.secondary
                            )
                        }
                    }

                    // Volume information with proper spacing
                    Card(
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 12.dp, horizontal = 16.dp),
                            verticalArrangement = Arrangement.Center,
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Text(
                                text = String.format(
                                    Locale.getDefault(),
                                    "Total Volume: %.2f %s³",
                                    displayVolume,
                                    if (isMetric) "m" else "ft"
                                ),
                                style = MaterialTheme.typography.titleMedium,
                                textAlign = TextAlign.Center,
                                modifier = Modifier.padding(bottom = 8.dp)
                            )

                            // Compact unit toggle
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.Center,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text(
                                    text = "Feet",
                                    style = MaterialTheme.typography.bodyMedium,
                                    modifier = Modifier.padding(end = 12.dp)
                                )
                                Switch(
                                    checked = isMetric,
                                    onCheckedChange = { isMetric = it },
                                    modifier = Modifier.padding(horizontal = 4.dp)
                                )
                                Text(
                                    text = "Meters",
                                    style = MaterialTheme.typography.bodyMedium,
                                    modifier = Modifier.padding(start = 12.dp)
                                )
                            }
                        }
                    }

                    Button(
                        onClick = { resetCalculation() },
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Icon(Icons.Default.Refresh, contentDescription = "Calculate Again")
                        Spacer(Modifier.width(8.dp))
                        Text("Calculate Again")
                    }
                }
            }

            // Settings Dialog
            if (showSettings) {
                AlertDialog(
                    onDismissRequest = { showSettings = false },
                    title = { Text("Settings") },
                    text = {
                        Column(
                            verticalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            OutlinedTextField(
                                value = dumperCapacity.toString(),
                                onValueChange = {
                                    dumperCapacity = it.toFloatOrNull() ?: dumperCapacity
                                },
                                label = { Text("Dumper Capacity (m³)") },
                                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal)
                            )

                            OutlinedTextField(
                                value = costPerTrip,
                                onValueChange = { costPerTrip = it },
                                label = { Text("Cost per Trip (₹)") },
                                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal)
                            )
                        }
                    },
                    confirmButton = {
                        TextButton(onClick = { showSettings = false }) {
                            Text("OK")
                        }
                    }
                )
            }
        }
    }
}