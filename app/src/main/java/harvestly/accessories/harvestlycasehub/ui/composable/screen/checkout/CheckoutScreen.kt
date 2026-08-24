package harvestly.accessories.harvestlycasehub.ui.composable.screen.checkout

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusManager
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import harvestly.accessories.harvestlycasehub.ui.state.DataUiState
import harvestly.accessories.harvestlycasehub.ui.viewmodel.CheckoutViewModel
import org.koin.androidx.compose.koinViewModel

@Composable
fun CheckoutScreen(modifier: Modifier = Modifier, viewModel: CheckoutViewModel = koinViewModel(), onNavigateToOrdersScreen: () -> Unit) {
    val orderState by viewModel.orderState.collectAsStateWithLifecycle()
    val emailInvalid by viewModel.emailInvalidState.collectAsStateWithLifecycle()
    val order = (orderState as? DataUiState.Populated)?.data
    var phone by remember { mutableStateOf("") }
    if (order != null) {
        CheckoutDialog(orderNumber = order.orderNumber, orderTotal = order.price, onConfirm = onNavigateToOrdersScreen)
    }
    CheckoutContent(
        customerName = viewModel.customerFirstName,
        collectionAddress = viewModel.customerLastName,
        customerEmail = viewModel.customerEmail,
        customerPhone = phone,
        isEmailInvalid = emailInvalid,
        modifier = modifier,
        focusManager = LocalFocusManager.current,
        onNameChanged = viewModel::updateCustomerFirstName,
        onAddressChanged = viewModel::updateCustomerLastName,
        onEmailChanged = viewModel::updateCustomerEmail,
        onPhoneChanged = { phone = it },
        onPlaceOrder = viewModel::placeOrder
    )
}

@Composable
private fun CheckoutContent(
    customerName: String,
    collectionAddress: String,
    customerEmail: String,
    customerPhone: String,
    isEmailInvalid: Boolean,
    modifier: Modifier,
    focusManager: FocusManager,
    onNameChanged: (String) -> Unit,
    onAddressChanged: (String) -> Unit,
    onEmailChanged: (String) -> Unit,
    onPhoneChanged: (String) -> Unit,
    onPlaceOrder: () -> Unit
) {
    val complete = customerName.isNotBlank() && collectionAddress.isNotBlank() && customerEmail.isNotBlank() && customerPhone.isNotBlank()
    Column(
        modifier = modifier.fillMaxSize().verticalScroll(rememberScrollState()).padding(20.dp),
        verticalArrangement = Arrangement.spacedBy(14.dp)
    ) {
        Text("Reserve your order", style = MaterialTheme.typography.headlineMedium)
        Text("Tell us who is collecting. We will hold confirmed reservations in store for 24 hours.", color = MaterialTheme.colorScheme.onSurfaceVariant)
        CheckoutTextField(customerName, onNameChanged, "Full name", Modifier.fillMaxWidth(), keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next))
        CheckoutTextField(collectionAddress, onAddressChanged, "Address", Modifier.fillMaxWidth(), keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next))
        CheckoutTextField(
            customerPhone,
            onPhoneChanged,
            "Phone number",
            Modifier.fillMaxWidth(),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone, imeAction = ImeAction.Next)
        )
        CheckoutTextField(
            customerEmail,
            onEmailChanged,
            "Contact email",
            Modifier.fillMaxWidth(),
            isError = isEmailInvalid,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email, imeAction = ImeAction.Done),
            keyboardActions = KeyboardActions(onDone = { focusManager.clearFocus() })
        )
        if (isEmailInvalid) Text("Enter a valid email address", color = MaterialTheme.colorScheme.error)
        Card(modifier = Modifier.fillMaxWidth()) {
            Column(modifier = Modifier.padding(16.dp)) {
                Text("What happens next", style = MaterialTheme.typography.titleMedium)
                Text("Your reservation number and total appear immediately after confirmation. Bring the number to the store within 24 hours.", modifier = Modifier.padding(top = 6.dp))
            }
        }
        Button(onClick = onPlaceOrder, enabled = complete, modifier = Modifier.fillMaxWidth()) {
            Text("Place Reservation")
        }
    }
}

@Composable
fun CheckoutTextField(
    input: String,
    onInputChange: (String) -> Unit,
    labelText: String,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    isError: Boolean = false,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    keyboardActions: KeyboardActions = KeyboardActions.Default
) {
    OutlinedTextField(
        value = input,
        onValueChange = onInputChange,
        modifier = modifier,
        enabled = enabled,
        label = { Text(labelText) },
        isError = isError,
        keyboardOptions = keyboardOptions,
        keyboardActions = keyboardActions,
        singleLine = true,
        colors = OutlinedTextFieldDefaults.colors(focusedBorderColor = MaterialTheme.colorScheme.primary)
    )
}
