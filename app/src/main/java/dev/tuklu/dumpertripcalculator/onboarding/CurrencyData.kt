package dev.tuklu.dumpertripcalculator.onboarding

import java.util.Currency
import java.util.Locale

data class CurrencyOption(
    val code: String,
    val symbol: String,
    val country: String
)

val supportedCurrencies = listOf(
    CurrencyOption("INR", "₹", "India"),
    CurrencyOption("USD", "$", "United States"),
    CurrencyOption("GBP", "£", "United Kingdom"),
    CurrencyOption("EUR", "€", "Europe"),
    CurrencyOption("AUD", "A$", "Australia"),
    CurrencyOption("CAD", "C$", "Canada"),
    CurrencyOption("AED", "د.إ", "UAE"),
    CurrencyOption("SGD", "S$", "Singapore"),
    CurrencyOption("ZAR", "R", "South Africa"),
    CurrencyOption("NGN", "₦", "Nigeria"),
    CurrencyOption("KES", "KSh", "Kenya")
)

fun detectCurrencyFromLocale(): CurrencyOption {
    return try {
        val locale = Locale.getDefault()
        val currency = Currency.getInstance(locale)
        supportedCurrencies.find { it.code == currency.currencyCode }
            ?: supportedCurrencies.find { it.code == "USD" }!!
    } catch (_: Exception) {
        supportedCurrencies.find { it.code == "USD" }!!
    }
}
