package harvestly.accessories.harvestlycasehub.ui.composable.screen.checkout

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp

@Composable
fun CheckoutDialog(orderNumber: String, orderTotal: Double, onConfirm: () -> Unit) {
    AlertDialog(
        onDismissRequest = onConfirm,
        confirmButton = { TextButton(onClick = onConfirm) { Text("View Orders") } },
        title = { Text("Reservation confirmed", style = MaterialTheme.typography.titleLarge) },
        text = {
            Column(modifier = Modifier.fillMaxWidth()) {
                Text("Order #$orderNumber", style = MaterialTheme.typography.headlineMedium, color = MaterialTheme.colorScheme.primary)
                Text("Total £${"%.2f".format(orderTotal)}", fontWeight = FontWeight.Bold, modifier = Modifier.padding(top = 8.dp))
                Text("Your items are reserved. We look forward to welcoming you in store within the next 24 hours.", modifier = Modifier.padding(top = 14.dp))
            }
        }
    )
}
