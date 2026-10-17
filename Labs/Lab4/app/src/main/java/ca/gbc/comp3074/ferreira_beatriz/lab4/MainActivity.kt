package ca.gbc.comp3074.ferreira_beatriz.lab4

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import ca.gbc.comp3074.ferreira_beatriz.lab4.data.AppDatabase
import ca.gbc.comp3074.ferreira_beatriz.lab4.data.Customer
import ca.gbc.comp3074.ferreira_beatriz.lab4.data.CustomerDao
import ca.gbc.comp3074.ferreira_beatriz.lab4.ui.theme.Lab4Theme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        val database = AppDatabase.getDatabase(applicationContext)
        setContent {
            Lab4Theme {
                val customerViewModel: CustomerViewModel = viewModel(
                    factory = CustomerViewModelFactory(database.customerDao())
                )
                CustomerApp(customerViewModel)
            }
        }
    }
}

@Composable
fun CustomerApp(viewModel: CustomerViewModel) {
    val customers by viewModel.customers.collectAsStateWithLifecycle(initialValue = emptyList())
    CustomerScreen(customers, viewModel::addCustomer, viewModel::deleteCustomer)
}

@Composable
fun CustomerScreen(customers: List<Customer>, onAddCustomer: (String, Int, Boolean) -> Unit, onDeleteCustomer: (Customer) -> Unit) {
    var name by rememberSaveable { mutableStateOf("") }
    var ageText by rememberSaveable { mutableStateOf("") }
    var isActive by rememberSaveable { mutableStateOf(false) }
    var message by rememberSaveable { mutableStateOf("") }
    Column(
        Modifier
            .fillMaxSize()
            .statusBarsPadding()
            .padding(24.dp),
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        Text("Room Customer Demo", style = MaterialTheme.typography.headlineSmall)
        OutlinedTextField(name, { name = it }, label = { Text("Customer name") }, singleLine = true, modifier = Modifier.fillMaxWidth())
        OutlinedTextField(ageText, { value -> if (value.all { it.isDigit() }) ageText = value }, label = { Text("Age") }, keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number), singleLine = true, modifier = Modifier.fillMaxWidth())
        Row(verticalAlignment = Alignment.CenterVertically) {
            Text("Active customer"); Spacer(Modifier.weight(1f)); Switch(isActive, { isActive = it })
        }
        Button(onClick = {
            val age = ageText.toIntOrNull()
            if (name.isBlank() || age == null) message = "Enter a valid name and age."
            else { onAddCustomer(name.trim(), age, isActive); name = ""; ageText = ""; isActive = false; message = "Customer added." }
        },
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .width(180.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xFF2D6A4F),
                contentColor = Color.White
            )
        ) { Text("Add Customer") }
        if (message.isNotEmpty()) Text(message)
        Text("Customers", style = MaterialTheme.typography.titleLarge)
        CustomerList(customers, onDeleteCustomer, Modifier.weight(1f))
    }
}

@Composable
fun CustomerList(customers: List<Customer>, onDeleteCustomer: (Customer) -> Unit, modifier: Modifier) {
    if (customers.isEmpty()) Box(modifier.fillMaxWidth(), contentAlignment = Alignment.Center) { Text("No customers in the database.") }
    else LazyColumn(modifier.fillMaxWidth(), verticalArrangement = Arrangement.spacedBy(8.dp)) {
        items(customers, key = { it.id }) { customer ->
            Card(Modifier.fillMaxWidth()) {
                Row(Modifier.fillMaxWidth().padding(12.dp), verticalAlignment = Alignment.CenterVertically) {
                    Column(Modifier.weight(1f)) {
                        Text(customer.name, style = MaterialTheme.typography.titleMedium)
                        Text("ID: " + customer.id + "  •  Age: " + customer.age)
                        Text(if (customer.isActive) "Active customer" else "Inactive customer")
                    }
                    TextButton(onClick = { onDeleteCustomer(customer) }) { Text("Delete") }
                }
            }
        }
    }
}
