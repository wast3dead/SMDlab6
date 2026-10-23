package com.example.smdlab6.task11

sealed interface Chargeable
sealed interface Wireless

class Phone(val model: String) : Chargeable, Wireless {
    override fun toString() = "Phone($model)"
}

class Headphones(val brand: String) : Chargeable, Wireless {
    override fun toString() = "Headphones($brand)"
}

class SmartLamp(val room: String) : Chargeable {
    override fun toString() = "SmartLamp($room)"
}

class Toaster(val slots: Int) {
    override fun toString() = "Toaster($slots slots)"
}

fun main() {
    println("Smart Home Devices Inventory")
    val devices: List<Any> = listOf(
        Phone("Pixel 8"),
        Headphones("Sony WH-1000XM5"),
        SmartLamp("Living Room"),
        Toaster(2),
        Phone("iPhone 15")
    )

    println("Inspecting ${devices.size} devices:")
    for (device in devices) {
        val traits = mutableListOf<String>()
        if (device is Chargeable) traits.add("Chargeable")
        if (device is Wireless) traits.add("Wireless")
        if (traits.isEmpty()) traits.add("Standard Appliance")

        println(" - $device -> Abilities: ${traits.joinToString(", ")}")
    }

    val chargeableCount = devices.count { it is Chargeable }
    println("\nTotal Chargeable Devices: $chargeableCount out of ${devices.size}")
}
