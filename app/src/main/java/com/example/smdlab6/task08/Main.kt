package com.example.smdlab6.task08

sealed class ValidationError(val field: String, val message: String) {
    class EmptyName : ValidationError("name", "Name cannot be empty or blank")
    class InvalidAge(age: Int) : ValidationError("age", "Age $age must be between 18 and 120")
    class BadEmail(email: String) : ValidationError("email", "Email '$email' is malformed")
}

fun validate(name: String, age: Int, email: String): List<ValidationError> {
    val errors = mutableListOf<ValidationError>()
    if (name.isBlank()) {
        errors.add(ValidationError.EmptyName())
    }
    if (age !in 18..120) {
        errors.add(ValidationError.InvalidAge(age))
    }
    if (!email.contains("@") || !email.contains(".")) {
        errors.add(ValidationError.BadEmail(email))
    }
    return errors
}

fun main() {
    println("Form Validation Test")
    val testCases = listOf(
        Triple("Alice", 25, "alice@example.com"),
        Triple("", 16, "bademail"),
        Triple("Bob", 150, "bob@domain"),
        Triple("  ", 40, "valid@mail.org")
    )

    for ((name, age, email) in testCases) {
        println("Testing input -> name: '$name', age: $age, email: '$email'")
        val results = validate(name, age, email)
        if (results.isEmpty()) {
            println(" -> Validation PASSED!")
        } else {
            println(" -> Validation FAILED with ${results.size} error(s):")
            for (err in results) {
                println("    [Field: ${err.field}] ${err.message}")
            }
        }
        println()
    }
}
