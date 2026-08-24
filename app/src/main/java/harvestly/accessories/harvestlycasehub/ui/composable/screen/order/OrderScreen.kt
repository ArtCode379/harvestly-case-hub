package harvestly.accessories.harvestlycasehub.ui.composable.screen.order

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import harvestly.accessories.harvestlycasehub.data.entity.OrderEntity
import harvestly.accessories.harvestlycasehub.ui.composable.shared.DOFIPContentWrapper
import harvestly.accessories.harvestlycasehub.ui.state.DataUiState
import harvestly.accessories.harvestlycasehub.ui.theme.SuccessGreen
import harvestly.accessories.harvestlycasehub.ui.viewmodel.OrderViewModel
import java.time.format.DateTimeFormatter
import org.koin.androidx.compose.koinViewModel

@Composable
fun OrdersScreen(modifier: Modifier = Modifier, viewModel: OrderViewModel = koinViewModel()) {
    val state by viewModel.ordersState.collectAsState()
    DOFIPContentWrapper(
        dataState = state,
        dataPopulated = {
            val orders = (state as DataUiState.Populated).data.sortedByDescending { it.timestamp }
            LazyColumn(modifier = modifier.fillMaxSize(), verticalArrangement = Arrangement.spacedBy(12.dp)) {
                item { Text("Purchase history", style = MaterialTheme.typography.headlineMedium, modifier = Modifier.padding(18.dp)) }
                items(orders, key = { it.orderNumber }) { order -> OrderCard(order) }
            }
        },
        dataEmpty = {
            Column(modifier = modifier.fillMaxSize(), horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.Center) {
                Text("No orders yet", style = MaterialTheme.typography.titleLarge)
                Text("Your reservations will appear here.", color = MaterialTheme.colorScheme.onSurfaceVariant, modifier = Modifier.padding(top = 8.dp))
            }
        }
    )
}

@Composable
private fun OrderCard(order: OrderEntity) {
    Card(modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp)) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
                Text("Order #${order.orderNumber}", style = MaterialTheme.typography.titleMedium)
                Surface(shape = CircleShape, color = SuccessGreen.copy(alpha = 0.12f)) {
                    Text("Completed", color = SuccessGreen, style = MaterialTheme.typography.labelMedium, modifier = Modifier.padding(horizontal = 10.dp, vertical = 6.dp))
                }
            }
            Text(order.timestamp.format(DateTimeFormatter.ofPattern("dd MMM yyyy, HH:mm")), color = MaterialTheme.colorScheme.onSurfaceVariant, modifier = Modifier.padding(top = 8.dp))
            Text(order.description, style = MaterialTheme.typography.bodyMedium, modifier = Modifier.padding(top = 10.dp))
            Text("£${"%.2f".format(order.price)}", fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.primary, modifier = Modifier.padding(top = 10.dp))
        }
    }
}
