package sv.edu.udb.vr181981.veterinariasantabarbara_clientes.components

import android.content.Context
import android.view.inputmethod.InputMethodManager
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import sv.edu.udb.vr181981.veterinariasantabarbara_clientes.ui.theme.BluePrimary

@Composable
fun SearchableDropdown(
    items: List<String>,
    onItemSelected: (String) -> Unit
) {
    var expanded by remember { mutableStateOf(false) }
    var searchText by remember { mutableStateOf("") }
    val keyboardController = LocalSoftwareKeyboardController.current

    Surface {
        DropdownMenu(
            expanded = expanded,
            onDismissRequest = {
                expanded = false
                keyboardController?.hide()
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            val filteredItems = items.filter {
                it.contains(searchText, ignoreCase = true)
            }
            if (filteredItems.isEmpty()) {
                DropdownMenuItem(
                    onClick = { /* No action when empty */ },
                    text = { Text(text = "No items found", color = Color.Gray) },
                )
            } else {
                filteredItems.forEach { item ->
                    DropdownMenuItem(
                        onClick = {
                            expanded = false
                            onItemSelected(item)
                        },
                        text = { Text(text = item) }
                    )
                }
            }
        }

        TextField(
            value = searchText,
            onValueChange = {
                searchText = it
                expanded = true
            },
            label = { Text("Search") },
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Text,
                imeAction = ImeAction.Done
            ),
            keyboardActions = KeyboardActions(
                onDone = {
                    expanded = false
                    keyboardController?.hide()
                }
            ),
            trailingIcon = {
                // Clear button to reset search
                if (searchText.isNotEmpty()) {
                    Text(
                        text = "Clear",
                        color = BluePrimary,
                        modifier = Modifier
                            .padding(end = 8.dp)
                            .clickable {
                                searchText = ""
                            }
                    )
                }
            },
            modifier = Modifier.fillMaxWidth()
        )

        val context = LocalContext.current
        LaunchedEffect(Unit) {
            val keyboard =
                context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            if (expanded) {
                keyboardController?.hide()
                //keyboard.hideSoftInputFromWindow(context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager)
            }
        }
    }
}