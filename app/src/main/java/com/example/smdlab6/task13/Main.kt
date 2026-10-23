package com.example.smdlab6.task13

sealed class Passenger(val age: Int) {
    class Adult(age: Int) : Passenger(age)
    class Child(age: Int) : Passenger(age)
    class Senior(age: Int) : Passenger(age)
}

fun fare(passenger: Passenger): Double {
    return when (passenger) {
        is Passenger.Child if passenger.age < 4 -> 0.0
        is Passenger.Child -> 50.0
        is Passenger.Senior if passenger.age >= 65 -> 60.0
        is Passenger.Senior -> 100.0
        is Passenger.Adult -> 100.0
    }
}

fun main() {
    println("Bus Fare Calculator (with Guard Conditions)")
    val passengers = listOf(
        Passenger.Child(2),   // < 4 years old -> free (0.0)
        Passenger.Child(10),  // child regular -> 50.0
        Passenger.Senior(70), // senior >= 65 -> 60.0
        Passenger.Senior(60), // senior < 65 -> 100.0
        Passenger.Adult(30)   // adult -> 100.0
    )

    for (p in passengers) {
        val f = fare(p)
        val type = p::class.simpleName
        println("Passenger type: $type, Age: ${p.age} -> Fare: $f UZS")
    }
}
