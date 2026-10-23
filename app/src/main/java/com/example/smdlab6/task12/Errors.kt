package com.example.smdlab6.task12

sealed interface AppError {
    fun getReport(): String
}

sealed class IoError : AppError {
    class FileMissing(val filename: String) : IoError() {
        override fun getReport() = "File missing error: $filename"
    }
    class NoPermission(val path: String) : IoError() {
        override fun getReport() = "Permission denied error on: $path"
    }
}

object Timeout : AppError {
    override fun getReport() = "Operation timed out."
}

open class CustomError(val errorCode: Int, val description: String) : AppError {
    override fun getReport() = "Custom Error [$errorCode]: $description"
}
