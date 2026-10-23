package com.example.smdlab6.task05

sealed class Payment {
    data class CreditCard(val number: String, val expiry: String) : Payment()
    data class PayPal(val email: String) : Payment()
    data object Cash : Payment()
    data class Crypto(val wallet: String) : Payment()
}

fun readPayment(): Payment? {
    println("Select payment method: 1. Credit Card, 2. PayPal, 3. Cash, 4. Crypto")
    print("Enter choice (1-4): ")
    when (readlnOrNull()?.trim()) {
        "1" -> {
            print("Enter card number: ")
            val num = readlnOrNull()?.trim() ?: ""
            print("Enter expiry (MM/YY): ")
            val exp = readlnOrNull()?.trim() ?: ""
            return Payment.CreditCard(num, exp)
        }
        "2" -> {
            print("Enter PayPal email: ")
            val email = readlnOrNull()?.trim() ?: ""
            return Payment.PayPal(email)
        }
        "3" -> {
            return Payment.Cash
        }
        "4" -> {
            print("Enter wallet address: ")
            val wallet = readlnOrNull()?.trim() ?: ""
            return Payment.Crypto(wallet)
        }
        else -> {
            println("Invalid selection.")
            return null
        }
    }
}

fun process(payment: Payment, amount: Double) {
    // Exhaustive when expression thanks to sealed class
    when (payment) {
        is Payment.CreditCard -> {
            println("Processing Credit Card payment of $$amount using card ending with ${payment.number.takeLast(4)}")
        }
        is Payment.PayPal -> {
            println("Processing PayPal payment of $$amount for account ${payment.email}")
        }
        Payment.Cash -> {
            println("Processing Cash payment of $$amount.")
        }
        is Payment.Crypto -> {
            println("Processing Crypto payment of $$amount to wallet ${payment.wallet}")
        }
    }
}

fun main() {
    val payment = readPayment() ?: return
    print("Enter amount to pay: ")
    val amount = readlnOrNull()?.trim()?.toDoubleOrNull() ?: 0.0
    process(payment, amount)
}
