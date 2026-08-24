package harvestly.accessories.harvestlycasehub.ui.composable.screen.productdetails

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import harvestly.accessories.harvestlycasehub.R
import harvestly.accessories.harvestlycasehub.data.model.Product
import harvestly.accessories.harvestlycasehub.ui.composable.shared.DOFIPContentWrapper
import harvestly.accessories.harvestlycasehub.ui.composable.shared.DOFIPEmptyView
import harvestly.accessories.harvestlycasehub.ui.state.DataUiState
import harvestly.accessories.harvestlycasehub.ui.viewmodel.ProductDetailsViewModel
import kotlinx.coroutines.delay
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun ProductDetailsScreen(productId: Int, modifier: Modifier = Modifier, viewModel: ProductDetailsViewModel = koinViewModel()) {
    val state by viewModel.productDetailsState.collectAsState()
    LaunchedEffect(productId) { viewModel.observeProductDetails(productId) }
    DOFIPContentWrapper(
        dataState = state,
        dataPopulated = {
            ProductDetail((state as DataUiState.Populated).data, modifier) { viewModel.addProductToCart() }
        },
        dataEmpty = {
            DOFIPEmptyView(primaryText = stringResource(R.string.dofip_product_details_state_empty_primary_text), modifier = Modifier.fillMaxSize())
        }
    )
}

@Composable
private fun ProductDetail(product: Product, modifier: Modifier, onAddToCart: () -> Unit) {
    var cartAdded by remember { mutableStateOf(false) }
    LaunchedEffect(cartAdded) {
        if (cartAdded) {
            delay(2000)
            cartAdded = false
        }
    }
    Box(modifier = modifier.fillMaxSize()) {
        Column(modifier = Modifier.fillMaxSize().verticalScroll(rememberScrollState()).padding(bottom = 92.dp)) {
            AsyncImage(model = product.imageUrl, contentDescription = product.title, modifier = Modifier.fillMaxWidth().height(330.dp), contentScale = ContentScale.Crop)
            Column(modifier = Modifier.padding(22.dp)) {
                Surface(shape = CircleShape, color = MaterialTheme.colorScheme.surfaceVariant) {
                    Text(stringResource(product.category.titleRes), style = MaterialTheme.typography.labelMedium, modifier = Modifier.padding(horizontal = 12.dp, vertical = 7.dp))
                }
                Text(product.title, style = MaterialTheme.typography.headlineMedium, modifier = Modifier.padding(top = 16.dp))
                Text("Designed for the everyday", style = MaterialTheme.typography.titleMedium, color = MaterialTheme.colorScheme.primary, modifier = Modifier.padding(top = 20.dp))
                Text(product.description, style = MaterialTheme.typography.bodyLarge, color = MaterialTheme.colorScheme.onSurfaceVariant, modifier = Modifier.padding(top = 8.dp))
                Text(
                    text = "Carefully selected by Harvestly for dependable quality, clean design and a comfortable fit in your daily routine.",
                    style = MaterialTheme.typography.bodyMedium,
                    modifier = Modifier.padding(top = 14.dp)
                )
            }
        }
        Surface(modifier = Modifier.align(Alignment.BottomCenter).fillMaxWidth(), shadowElevation = 12.dp, color = MaterialTheme.colorScheme.surface) {
            Row(modifier = Modifier.padding(16.dp), verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(18.dp)) {
                Text(stringResource(R.string.dofip_price, product.price), style = MaterialTheme.typography.titleLarge, color = MaterialTheme.colorScheme.primary, fontWeight = FontWeight.Bold)
                Button(
                    onClick = {
                        onAddToCart()
                        cartAdded = true
                    },
                    modifier = Modifier.weight(1f)
                ) {
                    Text(stringResource(R.string.dofip_button_add_to_cart_label))
                }
            }
        }
        AnimatedVisibility(visible = cartAdded, enter = slideInVertically { it }, exit = fadeOut(), modifier = Modifier.align(Alignment.BottomCenter).padding(bottom = 78.dp)) {
            Row(modifier = Modifier.fillMaxWidth().background(MaterialTheme.colorScheme.surface).padding(14.dp), verticalAlignment = Alignment.CenterVertically) {
                Icon(Icons.Default.CheckCircle, contentDescription = null, tint = MaterialTheme.colorScheme.primary, modifier = Modifier.size(20.dp))
                Text("Added to cart", style = MaterialTheme.typography.bodyMedium, modifier = Modifier.padding(start = 10.dp))
            }
        }
    }
}
