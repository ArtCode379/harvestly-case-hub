package harvestly.accessories.harvestlycasehub.ui.composable.screen.home

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import harvestly.accessories.harvestlycasehub.R
import harvestly.accessories.harvestlycasehub.data.model.Product
import harvestly.accessories.harvestlycasehub.data.model.ProductCategory
import harvestly.accessories.harvestlycasehub.ui.composable.shared.DOFIPContentWrapper
import harvestly.accessories.harvestlycasehub.ui.composable.shared.DOFIPEmptyView
import harvestly.accessories.harvestlycasehub.ui.state.DataUiState
import harvestly.accessories.harvestlycasehub.ui.viewmodel.ProductViewModel
import org.koin.androidx.compose.koinViewModel

@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    viewModel: ProductViewModel = koinViewModel(),
    onNavigateToProductDetails: (productId: Int) -> Unit
) {
    val productsState by viewModel.productsState.collectAsState()
    var category by remember { mutableStateOf<ProductCategory?>(null) }

    DOFIPContentWrapper(
        dataState = productsState,
        dataPopulated = {
            val products = (productsState as DataUiState.Populated).data
            val shown = category?.let { selected -> products.filter { it.category == selected } } ?: products
            LazyColumn(
                modifier = modifier.fillMaxSize(),
                contentPadding = PaddingValues(bottom = 24.dp),
                verticalArrangement = Arrangement.spacedBy(18.dp)
            ) {
                item {
                    HeroProduct(product = products.first(), onClick = { onNavigateToProductDetails(products.first().id) })
                }
                item {
                    Text(
                        text = "Made for life in motion",
                        style = MaterialTheme.typography.headlineMedium,
                        modifier = Modifier.padding(horizontal = 20.dp)
                    )
                }
                item {
                    LazyRow(contentPadding = PaddingValues(horizontal = 20.dp), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                        item {
                            CategoryButton("All", category == null) { category = null }
                        }
                        items(ProductCategory.entries) { item ->
                            CategoryButton(stringResource(item.titleRes), category == item) { category = item }
                        }
                    }
                }
                items(shown.chunked(2)) { rowProducts ->
                    Row(
                        modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp),
                        horizontalArrangement = Arrangement.spacedBy(12.dp),
                        verticalAlignment = Alignment.Top
                    ) {
                        rowProducts.forEachIndexed { index, product ->
                            ProductCard(
                                product = product,
                                tall = (shown.indexOf(product) + index) % 3 == 0,
                                modifier = Modifier.weight(1f),
                                onClick = { onNavigateToProductDetails(product.id) }
                            )
                        }
                        if (rowProducts.size == 1) Box(modifier = Modifier.weight(1f))
                    }
                }
            }
        },
        dataEmpty = {
            DOFIPEmptyView(primaryText = stringResource(R.string.dofip_products_state_empty_primary_text), modifier = Modifier.fillMaxSize())
        }
    )
}

@Composable
private fun HeroProduct(product: Product, onClick: () -> Unit) {
    Box(modifier = Modifier.fillMaxWidth().height(230.dp).clickable(onClick = onClick)) {
        AsyncImage(model = product.imageUrl, contentDescription = product.title, modifier = Modifier.fillMaxSize(), contentScale = ContentScale.Crop)
        Box(
            modifier = Modifier.fillMaxSize().background(Brush.verticalGradient(listOf(Color.Transparent, Color(0xCC1A3A2A))))
        )
        Column(modifier = Modifier.align(Alignment.BottomStart).padding(22.dp)) {
            Text("FEATURED", style = MaterialTheme.typography.labelMedium, color = MaterialTheme.colorScheme.secondary)
            Text(product.title, style = MaterialTheme.typography.headlineMedium, color = Color.White)
            Text(stringResource(R.string.dofip_price, product.price), style = MaterialTheme.typography.titleMedium, color = Color.White)
        }
    }
}

@Composable
private fun CategoryButton(label: String, selected: Boolean, onClick: () -> Unit) {
    OutlinedButton(
        onClick = onClick,
        shape = RoundedCornerShape(50),
        border = BorderStroke(1.dp, if (selected) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.outline)
    ) {
        Text(label, color = if (selected) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onSurfaceVariant)
    }
}

@Composable
private fun ProductCard(product: Product, tall: Boolean, modifier: Modifier = Modifier, onClick: () -> Unit) {
    Card(
        modifier = modifier.clickable(onClick = onClick),
        shape = RoundedCornerShape(18.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
    ) {
        AsyncImage(
            model = product.imageUrl,
            contentDescription = product.title,
            modifier = Modifier.fillMaxWidth().height(if (tall) 210.dp else 155.dp).clip(RoundedCornerShape(topStart = 18.dp, topEnd = 18.dp)),
            contentScale = ContentScale.Crop
        )
        Column(modifier = Modifier.padding(12.dp)) {
            Text(stringResource(product.category.titleRes).uppercase(), style = MaterialTheme.typography.labelMedium, color = MaterialTheme.colorScheme.onSurfaceVariant)
            Text(product.title, style = MaterialTheme.typography.titleMedium, modifier = Modifier.padding(top = 4.dp))
            Text(stringResource(R.string.dofip_price, product.price), fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.primary, modifier = Modifier.padding(top = 6.dp))
        }
    }
}
