package com.example.smdlab6

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.smdlab6.ui.theme.SMDlab6Theme

data class TaskItem(
    val id: Int,
    val title: String,
    val description: String,
    val runDemo: () -> String
)

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            SMDlab6Theme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    TaskDashboard(modifier = Modifier.padding(innerPadding))
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TaskDashboard(modifier: Modifier = Modifier) {
    val tasks = remember {
        listOf(
            TaskItem(
                1,
                "Task 1: Enum — Order Status",
                "Demonstrates OrderStatus entries, ordinal positions, and next step lookup."
            ) {
                val status = com.example.smdlab6.task01.OrderStatus.SHIPPED
                buildString {
                    append("All statuses: ${com.example.smdlab6.task01.OrderStatus.entries.joinToString()}\n")
                    append("Tested Status: $status\n")
                    append("Position (Ordinal + 1): ${status.ordinal + 1}\n")
                    append("Total Count: ${com.example.smdlab6.task01.OrderStatus.entries.size}\n")
                    append("Next Step: ${com.example.smdlab6.task01.OrderStatus.entries.getOrNull(status.ordinal + 1)}")
                }
            },
            TaskItem(
                2,
                "Task 2: Enum with Properties & Methods — Calculator",
                "Operation enum with symbol property, abstract apply(), and companion fromSymbol."
            ) {
                val op = com.example.smdlab6.task02.Operation.MULTIPLY
                val a = 12.0
                val b = 4.0
                val result = op.apply(a, b)
                "Expression: $a ${op.symbol} $b = $result\nFromSymbol('*'): ${com.example.smdlab6.task02.Operation.fromSymbol("*")?.name}"
            },
            TaskItem(
                3,
                "Task 3: When with Enum — Shipping Cost",
                "Exhaustive when expression for Delivery shipping cost calculation in UZS."
            ) {
                val type = com.example.smdlab6.task03.Delivery.EXPRESS
                val weight = 5.0
                val cost = com.example.smdlab6.task03.shippingCost(type, weight)
                "Delivery Type: $type\nWeight: ${weight}kg\nShipping Cost: $cost UZS"
            },
            TaskItem(
                4,
                "Task 4: Sealed Class — Payment Method",
                "Sealed class Payment with CreditCard, PayPal, and Cash."
            ) {
                val payment: com.example.smdlab6.task04.Payment = com.example.smdlab6.task04.Payment.CreditCard("1234567890123456", "12/26")
                buildString {
                    append("Selected Payment: ${payment::class.simpleName}\n")
                    append("Details: Card ending with ${(payment as com.example.smdlab6.task04.Payment.CreditCard).number.takeLast(4)}")
                }
            },
            TaskItem(
                5,
                "Task 5: Adding a Sealed Subclass — Crypto Payment",
                "Extends Payment sealed class with Crypto subclass and compiler exhaustiveness."
            ) {
                val payment: com.example.smdlab6.task05.Payment = com.example.smdlab6.task05.Payment.Crypto("0xAbCd...1234")
                "Selected Payment: ${payment::class.simpleName}\nWallet: ${(payment as com.example.smdlab6.task05.Payment.Crypto).wallet}"
            },
            TaskItem(
                6,
                "Task 6: Sealed UI State — Weather Screen",
                "Sealed class UiState for Loading, Success(city, temp), and Error."
            ) {
                val state = com.example.smdlab6.task06.loadWeather("Tashkent")
                when (state) {
                    is com.example.smdlab6.task06.UiState.Success -> "City: ${state.city}\nTemperature: ${state.temperature}°C"
                    is com.example.smdlab6.task06.UiState.Error -> "Error: ${state.message}"
                    is com.example.smdlab6.task06.UiState.Loading -> "Loading..."
                }
            },
            TaskItem(
                7,
                "Task 7: Sealed Request and Response — Login System",
                "Sealed interface ApiRequest and ApiResponse handling auth simulation."
            ) {
                val req = com.example.smdlab6.task07.ApiRequest.LoginRequest("admin", "1234")
                val res = com.example.smdlab6.task07.handleRequest(req)
                when (res) {
                    is com.example.smdlab6.task07.ApiResponse.UserSuccess -> "Success: ${res.message}"
                    is com.example.smdlab6.task07.ApiResponse.Error -> "Error: ${res.reason}"
                    is com.example.smdlab6.task07.ApiResponse.NotLoggedIn -> "Not Logged In"
                }
            },
            TaskItem(
                8,
                "Task 8: Sealed Class Constructors — Form Errors",
                "ValidationError sealed class with field & message parameters and subclasses."
            ) {
                val errors = com.example.smdlab6.task08.validate("", 15, "invalid-email")
                buildString {
                    append("Validation failed with ${errors.size} errors:\n")
                    errors.forEach { append("- [${it.field}] ${it.message}\n") }
                }
            },
            TaskItem(
                9,
                "Task 9: Enum Inside Sealed Class — Incident Log",
                "Severity enum inside Incident sealed class sorted by descending severity."
            ) {
                val incidents = listOf(
                    com.example.smdlab6.task09.Incident.FileProblem(com.example.smdlab6.task09.Severity.MINOR, "sys.log"),
                    com.example.smdlab6.task09.Incident.DatabaseDown(com.example.smdlab6.task09.Severity.CRITICAL, "MainDB"),
                    com.example.smdlab6.task09.Incident.Crash
                )
                val sorted = incidents.sortedByDescending { it.severity.ordinal }
                buildString {
                    append("Incidents sorted by severity:\n")
                    sorted.forEach { append("- [${it.severity}] ${it::class.simpleName}\n") }
                }
            },
            TaskItem(
                10,
                "Task 10: Enum in Sealed Family — Media Player Commands",
                "PlayerCommand sealed interface with Basic enum, Seek, and Volume."
            ) {
                val cmd1 = com.example.smdlab6.task10.PlayerCommand.Basic.PLAY
                val cmd2 = com.example.smdlab6.task10.PlayerCommand.Seek(5000L)
                "Commands tested:\n- $cmd1\n- $cmd2"
            },
            TaskItem(
                11,
                "Task 11: Multiple Sealed Interfaces — Smart Home Devices",
                "Chargeable and Wireless sealed interfaces implemented across devices."
            ) {
                val phone = com.example.smdlab6.task11.Phone("Pixel 8")
                val toaster = com.example.smdlab6.task11.Toaster(2)
                val phoneObj: Any = phone
                val toasterObj: Any = toaster
                buildString {
                    append("Phone is Chargeable: ${phoneObj is com.example.smdlab6.task11.Chargeable}, Wireless: ${phoneObj is com.example.smdlab6.task11.Wireless}\n")
                    append("Toaster is Chargeable: ${toasterObj is com.example.smdlab6.task11.Chargeable}")
                }
            },
            TaskItem(
                12,
                "Task 12: Distributed Subclasses — Error Reporter",
                "Distributed AppError hierarchy (IoError, Timeout, CustomError, DiskFull)."
            ) {
                val err = com.example.smdlab6.task12.DiskFull("C:", 15L)
                "Report: ${err.getReport()}"
            },
            TaskItem(
                13,
                "Task 13: Guard Conditions — Bus Fare Calculator",
                "Passenger sealed class with guard conditions (if age < 4) in when."
            ) {
                val childYoung = com.example.smdlab6.task13.Passenger.Child(2)
                val senior = com.example.smdlab6.task13.Passenger.Senior(70)
                buildString {
                    append("Child (Age 2) Fare: ${com.example.smdlab6.task13.fare(childYoung)} UZS\n")
                    append("Senior (Age 70) Fare: ${com.example.smdlab6.task13.fare(senior)} UZS")
                }
            },
            TaskItem(
                14,
                "Task 14: Generic Sealed Result — Outcome Parser",
                "Generic Outcome<out T> sealed interface with Ok<T> and Fail."
            ) {
                val outcome = com.example.smdlab6.task14.parseQuantity("10")
                when (outcome) {
                    is com.example.smdlab6.task14.Outcome.Ok -> "Parsed Quantity Successfully: ${outcome.value}"
                    is com.example.smdlab6.task14.Outcome.Fail -> "Failed: ${outcome.error}"
                }
            },
            TaskItem(
                15,
                "Task 15: Enum vs Sealed — Support Desk",
                "Priority enum and Action sealed interface managing sorted tickets."
            ) {
                val action = com.example.smdlab6.task15.Action.Open("App crash bug", com.example.smdlab6.task15.Priority.URGENT)
                "Action Created: ${action::class.simpleName}\nTitle: ${action.title}\nPriority: ${action.priority}"
            }
        )
    }

    var selectedTask by remember { mutableStateOf<TaskItem?>(null) }
    var executionResult by remember { mutableStateOf<String?>(null) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Kotlin Enums & Sealed Classes Tasks", fontWeight = FontWeight.Bold) },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.onPrimaryContainer
                )
            )
        }
    ) { padding ->
        Row(
            modifier = modifier
                .fillMaxSize()
                .padding(padding)
        ) {
            LazyColumn(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxHeight()
                    .padding(8.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(tasks) { task ->
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable {
                                selectedTask = task
                                executionResult = task.runDemo()
                            },
                        colors = CardDefaults.cardColors(
                            containerColor = if (selectedTask?.id == task.id)
                                MaterialTheme.colorScheme.secondaryContainer
                            else
                                MaterialTheme.colorScheme.surfaceVariant
                        )
                    ) {
                        Column(modifier = Modifier.padding(12.dp)) {
                            Text(text = task.title, fontWeight = FontWeight.SemiBold, fontSize = 15.sp)
                            Spacer(modifier = Modifier.height(4.dp))
                            Text(text = task.description, fontSize = 12.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
                        }
                    }
                }
            }

            // Right side / Detail view
            Card(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxHeight()
                    .padding(8.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(16.dp),
                    verticalArrangement = Arrangement.Center
                ) {
                    if (selectedTask != null) {
                        Text(text = selectedTask!!.title, fontWeight = FontWeight.Bold, fontSize = 18.sp, color = MaterialTheme.colorScheme.primary)
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(text = selectedTask!!.description, fontSize = 14.sp)
                        Spacer(modifier = Modifier.height(16.dp))
                        HorizontalDivider()
                        Spacer(modifier = Modifier.height(16.dp))
                        Text(text = "Execution Output:", fontWeight = FontWeight.SemiBold, fontSize = 14.sp)
                        Spacer(modifier = Modifier.height(8.dp))
                        Surface(
                            modifier = Modifier.fillMaxWidth(),
                            color = MaterialTheme.colorScheme.surfaceVariant,
                            shape = MaterialTheme.shapes.medium
                        ) {
                            Text(
                                text = executionResult ?: "Select a task to run demo.",
                                modifier = Modifier.padding(12.dp),
                                fontSize = 14.sp,
                                fontFamily = androidx.compose.ui.text.font.FontFamily.Monospace
                            )
                        }
                    } else {
                        Text(
                            text = "Tap on any task from the left list to inspect and run its demo simulation on screen.",
                            fontSize = 14.sp,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                }
            }
        }
    }
}
