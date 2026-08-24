package harvestly.accessories.harvestlycasehub.ui.composable.screen.cart

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.DeleteOutline
import androidx.compose.material.icons.filled.Remove
import androidx.compose.material.icons.filled.ShoppingBag
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil3.compose.AsyncImage
import harvestly.accessories.harvestlycasehub.R
import harvestly.accessories.harvestlycasehub.ui.composable.shared.DOFIPContentWrapper
import harvestly.accessories.harvestlycasehub.ui.state.CartItemUiState
import harvestly.accessories.harvestlycasehub.ui.state.DataUiState
import harvestly.accessories.harvestlycasehub.ui.viewmodel.CartViewModel
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun CartScreen(modifier: Modifier = Modifier, viewModel: CartViewModel = koinViewModel(), onNavigateToCheckoutScreen: () -> Unit) {
    val state by viewModel.cartItemsState.collectAsStateWithLifecycle()
    val total by viewModel.totalPrice.collectAsStateWithLifecycle()
    DOFIPContentWrapper(
        dataState = state,
        dataPopulated = {
            val items = (state as DataUiState.Populated).data
            Column(modifier = modifier.fillMaxSize()) {
                LazyColumn(modifier = Modifier.weight(1f), verticalArrangement = Arrangement.spacedBy(10.dp)) {
                    items(items, key = { it.productId }) { item ->
                        CartItem(
                            item = item,
                            onPlus = { viewModel.incrementProductInCart(item.productId) },
                            onMinus = {
                                if (item.quantity == 1) viewModel.deleteFromCart(item.productId) else viewModel.decrementItemInCart(item.productId)
                            },
                            onDelete = { viewModel.deleteFromCart(item.productId) }
                        )
                    }
                }
                Column(modifier = Modifier.fillMaxWidth().padding(18.dp)) {
                    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                        Text("Subtotal", color = MaterialTheme.colorScheme.onSurfaceVariant)
                        Text(stringResource(R.string.dofip_price, total))
                    }
                    Row(modifier = Modifier.fillMaxWidth().padding(top = 8.dp), horizontalArrangement = Arrangement.SpaceBetween) {
                        Text("Total", style = MaterialTheme.typography.titleLarge)
                        Text(stringResource(R.string.dofip_price, total), style = MaterialTheme.typography.titleLarge, color = MaterialTheme.colorScheme.primary)
                    }
                    Button(onClick = onNavigateToCheckoutScreen, modifier = Modifier.fillMaxWidth().padding(top = 16.dp)) {
                        Text("Proceed to Checkout")
                    }
                }
            }
        },
        dataEmpty = {
            Column(modifier = modifier.fillMaxSize(), horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.Center) {
                Icon(Icons.Default.ShoppingBag, contentDescription = null, modifier = Modifier.size(72.dp), tint = MaterialTheme.colorScheme.primary)
                Text("Your basket is ready for something good", style = MaterialTheme.typography.titleLarge, modifier = Modifier.padding(top = 18.dp))
                Text("Start Shopping", color = MaterialTheme.colorScheme.onSurfaceVariant, modifier = Modifier.padding(top = 8.dp))
            }
        }
    )
}

@Composable
private fun CartItem(item: CartItemUiState, onPlus: () -> Unit, onMinus: () -> Unit, onDelete: () -> Unit) {
    Card(modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp), shape = RoundedCornerShape(16.dp)) {
        Row(modifier = Modifier.padding(10.dp), verticalAlignment = Alignment.CenterVertically) {
            AsyncImage(model = item.productImageUrl, contentDescription = item.productTitle, modifier = Modifier.size(72.dp), contentScale = ContentScale.Crop)
            Column(modifier = Modifier.weight(1f).padding(horizontal = 12.dp)) {
                Text(item.productTitle, style = MaterialTheme.typography.titleMedium)
                Text(stringResource(R.string.dofip_price, item.productPrice), color = MaterialTheme.colorScheme.primary, fontWeight = FontWeight.Bold)
                Row(verticalAlignment = Alignment.CenterVertically) {
                    IconButton(onClick = onMinus) { Icon(Icons.Default.Remove, contentDescription = "Decrease quantity") }
                    Text(item.quantity.toString())
                    IconButton(onClick = onPlus) { Icon(Icons.Default.Add, contentDescription = "Increase quantity") }
                }
            }
            IconButton(onClick = onDelete) { Icon(Icons.Default.DeleteOutline, contentDescription = "Remove item") }
        }
    }
}
