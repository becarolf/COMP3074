package ca.gbc.comp3074.ferreira_beatriz.assignment1

import androidx.compose.foundation.hoverable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsHoveredAsState
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.material3.CheckboxDefaults.colors
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import java.util.Locale

@Composable
fun PayCalculatorScreen(
    modifier: Modifier = Modifier,
    onAboutClick: () -> Unit
) {
    // track when the mouse is over each button
    val calculateInteraction = remember { MutableInteractionSource() }
    val calculateHovered by calculateInteraction.collectIsHoveredAsState()

    val aboutInteraction = remember { MutableInteractionSource() }
    val aboutHovered by aboutInteraction.collectIsHoveredAsState()

    val buttonPurple = Color(0xFF6750A4)
    val darkPurple = Color(0xFF2F1365)

    // keep the entered values when the phone is rotated
    var hoursInput by rememberSaveable { mutableStateOf("") }
    var hourlyRateInput by rememberSaveable { mutableStateOf("") }
    var taxRateInput by rememberSaveable { mutableStateOf("") }

    var result by rememberSaveable { mutableStateOf("") }
    var errorMessage by rememberSaveable { mutableStateOf("") }

    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .imePadding()
            .padding(24.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Text(
            text = "Pay Calculator",
            style = MaterialTheme.typography.headlineMedium
        )

        OutlinedTextField(
            value = hoursInput,
            onValueChange = { hoursInput = it },
            label = { Text("Hours worked") },
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Decimal
            ),
            singleLine = true,
            modifier = Modifier.fillMaxWidth()
        )

        OutlinedTextField(
            value = hourlyRateInput,
            onValueChange = { hourlyRateInput = it },
            label = { Text("Hourly rate ($)") },
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Decimal
            ),
            singleLine = true,
            modifier = Modifier.fillMaxWidth()
        )

        OutlinedTextField(
            value = taxRateInput,
            onValueChange = { taxRateInput = it },
            label = { Text("Tax rate (%)") },
            supportingText = { Text("Enter 20 for 20%") },
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Decimal
            ),
            singleLine = true,
            modifier = Modifier.fillMaxWidth()
        )

        Button(
            onClick = {
                // convert the text into numbers
                val hours = hoursInput.toDoubleOrNull()
                val hourlyRate = hourlyRateInput.toDoubleOrNull()
                val taxRate = taxRateInput.toDoubleOrNull()

                if (
                    hours == null || !hours.isFinite() || hours < 0 ||
                    hourlyRate == null || !hourlyRate.isFinite() || hourlyRate < 0 ||
                    taxRate == null || !taxRate.isFinite() ||
                    taxRate < 0 || taxRate > 100
                ) {
                    errorMessage =
                        "Enter valid, non-negative numbers. Tax must be between 0 and 100."
                    result = ""
                } else {
                    errorMessage = ""

                    val pay: Double
                    val overtimePay: Double

                    if (hours <= 40) {
                        pay = hours * hourlyRate
                        overtimePay = 0.0
                    } else {
                        pay = 40 * hourlyRate
                        overtimePay = (hours - 40) * hourlyRate * 1.5
                    }

                    val totalPay = pay + overtimePay

                    // tax is based on regular pay.
                    val tax = pay * (taxRate / 100)

                    result = String.format(
                        Locale.CANADA,
                        "Pay: $%.2f\nOvertime pay: $%.2f\nTotal pay: $%.2f\nTax: $%.2f",
                        pay,
                        overtimePay,
                        totalPay,
                        tax
                    )
                }
            },
            interactionSource = calculateInteraction,
            colors = ButtonDefaults.buttonColors(
                containerColor = if (calculateHovered) darkPurple else buttonPurple,
                contentColor = Color.White
            ),
            modifier = Modifier
                .fillMaxWidth()
                .hoverable(interactionSource = calculateInteraction),
        ) {
            Text("Calculate")
        }

        if (errorMessage.isNotEmpty()) {
            Text(
                text = errorMessage,
                color = MaterialTheme.colorScheme.error
            )
        }

        if (result.isNotEmpty()) {
            Text(
                text = result,
                style = MaterialTheme.typography.bodyLarge
            )
        }

        Button(
            onClick = onAboutClick,
            interactionSource = aboutInteraction,
            colors = ButtonDefaults.buttonColors(
                containerColor = if (aboutHovered) darkPurple else buttonPurple,
                contentColor = Color.White
            ),
            modifier = Modifier
                .fillMaxWidth()
                .hoverable(interactionSource = aboutInteraction)
        ) {
            Text("About")
        }
    }
}