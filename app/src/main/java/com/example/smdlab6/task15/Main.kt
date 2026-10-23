package com.example.smdlab6.task15

enum class Priority {
    LOW, NORMAL, URGENT
}

sealed interface Action {
    data class Open(val title: String, val priority: Priority) : Action
    data class Close(val ticketId: Int) : Action
    data class Comment(val ticketId: Int, val text: String) : Action
    data object ListAll : Action
}

data class Ticket(
    val id: Int,
    val title: String,
    val priority: Priority,
    var status: String = "OPEN",
    val comments: MutableList<String> = mutableListOf()
)

class SupportDesk {
    private val tickets = mutableListOf<Ticket>()
    private var nextId = 1

    fun execute(action: Action) {
        when (action) {
            is Action.Open -> {
                val ticket = Ticket(nextId++, action.title, action.priority)
                tickets.add(ticket)
                println("[SUPPORT] Opened Ticket #${ticket.id}: '${ticket.title}' [Priority: ${ticket.priority}]")
            }
            is Action.Close -> {
                val ticket = tickets.find { it.id == action.ticketId }
                if (ticket != null) {
                    ticket.status = "CLOSED"
                    println("[SUPPORT] Closed Ticket #${ticket.id}")
                } else {
                    println("[ERROR] Ticket #${action.ticketId} not found.")
                }
            }
            is Action.Comment -> {
                val ticket = tickets.find { it.id == action.ticketId }
                if (ticket != null) {
                    ticket.comments.add(action.text)
                    println("[SUPPORT] Added comment to Ticket #${ticket.id}: '${action.text}'")
                } else {
                    println("[ERROR] Ticket #${action.ticketId} not found.")
                }
            }
            is Action.ListAll -> {
                println("\n--- Support Tickets (Sorted by Priority: URGENT -> NORMAL -> LOW) ---")
                if (tickets.isEmpty()) {
                    println("No tickets found.")
                } else {
                    val sorted = tickets.sortedByDescending { it.priority.ordinal }
                    for (t in sorted) {
                        println("Ticket #${t.id} [${t.priority}] Status: ${t.status} - \"${t.title}\"")
                        for (c in t.comments) {
                            println("   -> Comment: $c")
                        }
                    }
                }
                println("-----------------------------------------------------------------\n")
            }
        }
    }
}

fun main() {
    println("Support Desk System")
    val desk = SupportDesk()

    desk.execute(Action.Open("App crashes on startup", Priority.URGENT))
    desk.execute(Action.Open("Update profile picture button alignment", Priority.LOW))
    desk.execute(Action.Open("Payment gateway timeout error", Priority.URGENT))
    desk.execute(Action.Open("Dark mode toggle sluggish", Priority.NORMAL))

    desk.execute(Action.Comment(1, "Investigating stack trace in MainActivity"))
    desk.execute(Action.Close(2))
    
    desk.execute(Action.ListAll)
}
