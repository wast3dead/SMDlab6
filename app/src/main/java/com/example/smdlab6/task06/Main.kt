package com.example.smdlab6.task06

sealed class UiState {
    data object Loading : UiState()
    data class Success(val city: String, val temperature: Double) : UiState()
    data class Error(val message: String) : UiState()
}

val weatherDatabase = mapOf(
    "Tashkent" to 25.0,
    "Samarkand" to 22.5,
    "Bukhara" to 28.0,
    "London" to 15.0,
    "New York" to 18.5
)

fun loadWeather(city: String): UiState {
    val temp = weatherDatabase[city]
    return if (temp != null) {
        UiState.Success(city, temp)
    } else {
        UiState.Error("City '$city' not found in weather database.")
    }
}

fun render(state: UiState) {
    when (state) {
        is UiState.Loading -> {
            println("[UI] Loading weather data...")
        }
        is UiState.Success -> {
            println("[UI] Weather in ${state.city}: ${state.temperature}°C")
        }
        is UiState.Error -> {
            println("[UI Error] ${state.message}")
        }
    }
}

fun main() {
    println("Available cities: ${weatherDatabase.keys.joinToString()}")
    while (true) {
        print("Enter city name (or 'exit' to quit): ")
        val input = readlnOrNull()?.trim() ?: break
        if (input.equals("exit", ignoreCase = true)) break
        if (input.isEmpty()) continue

        render(UiState.Loading)
        // Simulate slight delay or direct load
        val state = loadWeather(input)
        render(state)
        println()
    }
    println("Exited Weather app.")
}
