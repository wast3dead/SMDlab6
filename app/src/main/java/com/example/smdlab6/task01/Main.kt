package com.example.smdlab6.task01

enum class OrderStatus {
    NEW, PAID, SHIPPED, DELIVERED
}

fun main() {
    println("Available statuses: ${OrderStatus.entries.joinToString()}")
    print("Enter order status: ")
    val input = readlnOrNull()?.trim()?.uppercase() ?: ""
    
    try {
        val status = OrderStatus.valueOf(input)
        println("Position (Ordinal + 1): ${status.ordinal + 1}")
        println("Total count: ${OrderStatus.entries.size}")
        
        val nextStep = OrderStatus.entries.getOrNull(status.ordinal + 1)
        if (nextStep != null) {
            println("Next step: $nextStep")
        } else {
            println("This is the final status.")
        }
    } catch (e: IllegalArgumentException) {
        println("Invalid status: '$input'")
    }
}
