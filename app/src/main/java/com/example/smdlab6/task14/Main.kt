package com.example.smdlab6.task14

sealed interface Outcome<out T> {
    data class Ok<T>(val value: T) : Outcome<T>
    data class Fail(val error: String) : Outcome<Nothing>
}

fun parseQuantity(raw: String): Outcome<Int> {
    val trimmed = raw.trim()
    val num = trimmed.toIntOrNull()
    return if (num != null && num > 0) {
        Outcome.Ok(num)
    } else {
        Outcome.Fail("Invalid quantity: '$raw'")
    }
}

fun parsePrice(raw: String): Outcome<Double> {
    val trimmed = raw.trim()
    val price = trimmed.toDoubleOrNull()
    return if (price != null && price >= 0.0) {
        Outcome.Ok(price)
    } else {
        Outcome.Fail("Invalid price: '$raw'")
    }
}

fun main() {
    println("Generic Sealed Result Outcome Parser")
    val inputs = listOf(
        Pair("10", "19.99"),
        Pair("abc", "15.0"),
        Pair("5", "-5.0"),
        Pair("0", "49.5")
    )

    for ((qStr, pStr) in inputs) {
        println("Parsing raw inputs -> qty: '$qStr', price: '$pStr'")
        val qtyOutcome = parseQuantity(qStr)
        val priceOutcome = parsePrice(pStr)

        when (qtyOutcome) {
            is Outcome.Fail -> println(" -> Qty Error: ${qtyOutcome.error}")
            is Outcome.Ok -> {
                when (priceOutcome) {
                    is Outcome.Fail -> println(" -> Price Error: ${priceOutcome.error}")
                    is Outcome.Ok -> {
                        val total = qtyOutcome.value * priceOutcome.value
                        println(" -> Success! Total Cost: $total (Qty: ${qtyOutcome.value}, Unit Price: ${priceOutcome.value})")
                    }
                }
            }
        }
        println()
    }
}
