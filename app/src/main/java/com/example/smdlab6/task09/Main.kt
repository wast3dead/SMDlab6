package com.example.smdlab6.task09

enum class Severity {
    MINOR, MAJOR, CRITICAL
}

sealed class Incident(val severity: Severity) {
    class FileProblem(severity: Severity, val path: String) : Incident(severity)
    class DatabaseDown(severity: Severity, val dbName: String) : Incident(severity)
    class SlowPage(severity: Severity, val url: String, val latencyMs: Long) : Incident(severity)
    object Crash : Incident(Severity.CRITICAL)
}

fun main() {
    println("Incident Log System")
    val incidents = mutableListOf<Incident>(
        Incident.FileProblem(Severity.MINOR, "/var/log/syslog"),
        Incident.DatabaseDown(Severity.CRITICAL, "ProductionDB"),
        Incident.SlowPage(Severity.MAJOR, "/api/checkout", 2500L),
        Incident.FileProblem(Severity.MAJOR, "/etc/passwd"),
        Incident.Crash,
        Incident.SlowPage(Severity.MINOR, "/home", 400L)
    )

    println("Original Incidents Count: ${incidents.size}")

    // Sort by descending severity (CRITICAL -> MAJOR -> MINOR), using ordinal descending
    val sortedIncidents = incidents.sortedByDescending { it.severity.ordinal }

    println("\nSorted Incidents (Highest Severity First):")
    for (inc in sortedIncidents) {
        val details = when (inc) {
            is Incident.FileProblem -> "File missing/corrupt: ${inc.path}"
            is Incident.DatabaseDown -> "Database unreachable: ${inc.dbName}"
            is Incident.SlowPage -> "Slow response on ${inc.url} (${inc.latencyMs}ms)"
            is Incident.Crash -> "App application fatal crash"
        }
        println(" - [${inc.severity}] $details")
    }

    val criticalCount = incidents.count { it.severity == Severity.CRITICAL }
    println("\nTotal Critical Incidents: $criticalCount")
}
