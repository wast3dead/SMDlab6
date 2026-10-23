package com.example.smdlab6.task03

enum class Delivery {
    PICKUP, COURIER, EXPRESS
}

fun shippingCost(type: Delivery, weightKg: Double): Double {
    return when (type) {
        Delivery.PICKUP -> 5000.0 + weightKg * 1000.0
        Delivery.COURIER -> 15000.0 + weightKg * 2000.0
        Delivery.EXPRESS -> 30000.0 + weightKg * 4000.0
    }
}

fun main() {
    println("Delivery types: ${Delivery.entries.joinToString()}")
    print("Enter delivery type (PICKUP, COURIER, EXPRESS): ")
    val typeInput = readlnOrNull()?.trim()?.uppercase() ?: return
    
    val deliveryType = try {
        Delivery.valueOf(typeInput)
    } catch (e: IllegalArgumentException) {
        println("Invalid delivery type.")
        return
    }

    print("Enter weight in kg: ")
    val weightInput = readlnOrNull()?.trim()?.toDoubleOrNull()
    if (weightInput == null || weightInput < 0) {
        println("Invalid weight.")
        return
    }

    val cost = shippingCost(deliveryType, weightInput)
    println("Calculated shipping cost for $deliveryType with weight ${weightInput}kg: $cost UZS")
}
