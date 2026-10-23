package com.example.smdlab6.task10

sealed interface PlayerCommand {
    enum class Basic : PlayerCommand {
        PLAY, PAUSE, STOP
    }
    data class Seek(val positionMs: Long) : PlayerCommand
    data class Volume(val level: Int) : PlayerCommand
}

class Player {
    private var isPlaying = false
    private var currentPosition = 0L
    private var volumeLevel = 50

    fun handle(command: PlayerCommand) {
        when (command) {
            is PlayerCommand.Basic -> {
                when (command) {
                    PlayerCommand.Basic.PLAY -> {
                        isPlaying = true
                        println("Player: Playing media at position ${currentPosition}ms")
                    }
                    PlayerCommand.Basic.PAUSE -> {
                        isPlaying = false
                        println("Player: Media paused.")
                    }
                    PlayerCommand.Basic.STOP -> {
                        isPlaying = false
                        currentPosition = 0L
                        println("Player: Media stopped and reset to 0ms.")
                    }
                }
            }
            is PlayerCommand.Seek -> {
                currentPosition = command.positionMs
                println("Player: Seeking to ${currentPosition}ms")
            }
            is PlayerCommand.Volume -> {
                volumeLevel = command.level.coerceIn(0, 100)
                println("Player: Volume set to $volumeLevel%")
            }
        }
    }
}

fun parse(line: String): PlayerCommand? {
    val parts = line.split(Regex("\\s+"))
    if (parts.isEmpty()) return null
    val cmd = parts[0].uppercase()
    return when (cmd) {
        "PLAY" -> PlayerCommand.Basic.PLAY
        "PAUSE" -> PlayerCommand.Basic.PAUSE
        "STOP" -> PlayerCommand.Basic.STOP
        "SEEK" -> {
            val ms = parts.getOrNull(1)?.toLongOrNull() ?: 0L
            PlayerCommand.Seek(ms)
        }
        "VOLUME", "VOL" -> {
            val lvl = parts.getOrNull(1)?.toIntOrNull() ?: 50
            PlayerCommand.Volume(lvl)
        }
        else -> {
            println("Unknown command: '$cmd'")
            null
        }
    }
}

fun main() {
    println("Media Player Command System")
    println("Commands: PLAY | PAUSE | STOP | SEEK <ms> | VOLUME <0-100> | exit")
    val player = Player()

    while (true) {
        print("player> ")
        val line = readlnOrNull()?.trim() ?: break
        if (line.equals("exit", ignoreCase = true)) break
        if (line.isEmpty()) continue

        val command = parse(line)
        if (command != null) {
            player.handle(command)
        }
    }
    println("Exited Media Player.")
}
