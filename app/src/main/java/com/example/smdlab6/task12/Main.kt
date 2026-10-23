package com.example.smdlab6.task12

fun report(error: AppError) {
    println("[REPORT] ${error.getReport()}")
}

fun createError(input: String): AppError {
    return when (input.lowercase()) {
        "missing" -> IoError.FileMissing("config.json")
        "permission" -> IoError.NoPermission("/root/data")
        "timeout" -> Timeout
        "disk" -> DiskFull("C:", 12L)
        else -> CustomError(500, "Unknown system error for input '$input'")
    }
}

fun main() {
    println("Distributed Subclasses Error Reporter")
    val inputs = listOf("missing", "permission", "timeout", "disk", "unknown_fault")
    for (inp in inputs) {
        val err = createError(inp)
        report(err)
    }
}
