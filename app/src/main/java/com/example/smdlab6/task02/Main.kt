package com.example.smdlab6.task02

enum class Operation(val symbol: String) {
    ADD("+") {
        override fun apply(a: Double, b: Double): Double = a + b
    },
    SUBTRACT("-") {
        override fun apply(a: Double, b: Double): Double = a - b
    },
    MULTIPLY("*") {
        override fun apply(a: Double, b: Double): Double = a * b
    },
    DIVIDE("/") {
        override fun apply(a: Double, b: Double): Double {
            if (b == 0.0) throw ArithmeticException("Division by zero")
            return a / b
        }
    };

    abstract fun apply(a: Double, b: Double): Double

    companion object {
        fun fromSymbol(symbol: String): Operation? {
            return entries.firstOrNull { it.symbol == symbol }
        }
    }
}

fun main() {
    println("Calculator Operations: ${Operation.entries.joinToString { it.symbol }}")
    print("Enter expression (e.g., 12 * 4): ")
    val input = readlnOrNull()?.trim() ?: return
    
    // Simple parser expecting format: number operator number (e.g. "12 * 4" or "12*4")
    val parts = input.split(Regex("\\s+"))
    if (parts.size != 3) {
        println("Invalid format. Please use format: number operator number (e.g., 12 * 4)")
        return
    }

    val a = parts[0].toDoubleOrNull()
    val sym = parts[1]
    val b = parts[2].toDoubleOrNull()

    if (a == null || b == null) {
        println("Invalid numbers provided.")
        return
    }

    val operation = Operation.fromSymbol(sym)
    if (operation == null) {
        println("Unknown operation symbol: '$sym'")
        return
    }

    try {
        val result = operation.apply(a, b)
        println("Result: $a $sym $b = $result")
    } catch (e: Exception) {
        println("Error: ${e.message}")
    }
}
