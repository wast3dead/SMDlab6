package com.example.smdlab6.task07

sealed interface ApiRequest {
    data class LoginRequest(val username: String, val pass: String) : ApiRequest
    data class LogoutRequest(val username: String) : ApiRequest
}

sealed class ApiResponse {
    data class UserSuccess(val message: String) : ApiResponse()
    data object NotLoggedIn : ApiResponse()
    data class Error(val reason: String) : ApiResponse()
}

val userDatabase = mutableMapOf("admin" to "1234", "john" to "password")
val activeSessions = mutableSetOf<String>()

fun handleRequest(request: ApiRequest): ApiResponse {
    return when (request) {
        is ApiRequest.LoginRequest -> {
            val correctPass = userDatabase[request.username]
            if (correctPass == null) {
                ApiResponse.Error("User '${request.username}' does not exist.")
            } else if (correctPass != request.pass) {
                ApiResponse.Error("Incorrect password for '${request.username}'.")
            } else {
                activeSessions.add(request.username)
                ApiResponse.UserSuccess("Welcome back, ${request.username}!")
            }
        }
        is ApiRequest.LogoutRequest -> {
            if (activeSessions.contains(request.username)) {
                activeSessions.remove(request.username)
                ApiResponse.UserSuccess("Successfully logged out ${request.username}.")
            } else {
                ApiResponse.NotLoggedIn
            }
        }
    }
}

fun show(response: ApiResponse) {
    when (response) {
        is ApiResponse.UserSuccess -> println("[SUCCESS] ${response.message}")
        is ApiResponse.NotLoggedIn -> println("[WARNING] User is not currently logged in.")
        is ApiResponse.Error -> println("[ERROR] ${response.reason}")
    }
}

fun main() {
    println("Login System Simulation")
    println("Commands: login <user> <pass> | logout <user> | exit")
    while (true) {
        print("> ")
        val line = readlnOrNull()?.trim() ?: break
        if (line.equals("exit", ignoreCase = true)) break
        val parts = line.split(Regex("\\s+"))
        if (parts.isEmpty()) continue

        val cmd = parts[0].lowercase()
        when (cmd) {
            "login" -> {
                if (parts.size < 3) {
                    println("Usage: login <username> <password>")
                    continue
                }
                val req = ApiRequest.LoginRequest(parts[1], parts[2])
                show(handleRequest(req))
            }
            "logout" -> {
                if (parts.size < 2) {
                    println("Usage: logout <username>")
                    continue
                }
                val req = ApiRequest.LogoutRequest(parts[1])
                show(handleRequest(req))
            }
            else -> {
                println("Unknown command. Use 'login', 'logout', or 'exit'.")
            }
        }
    }
}
