import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.toSize
import com.example.testcomposeapp.data.DataStoreManager
import com.example.testcomposeapp.ui.settingsScreen.SettingsScreenViewModel

@Composable
fun SettingsScreen() {

    val settingsScreenViewModel = SettingsScreenViewModel(DataStoreManager(LocalContext.current))
    val cities = listOf("saint_petersburg", "moscow")

    Column {
        DropDown(values = cities, selectedValue = cities.first()) {
            settingsScreenViewModel.setCity(it)
        }
    }
}

@Composable
fun DropDown(
    values: List<String>,
    selectedValue: String,
    onSelected: (str: String) -> Unit,
) {

    var expanded by remember { mutableStateOf(false) }
    var selectedText by remember { mutableStateOf(selectedValue) }
    var textFieldSize by remember { mutableStateOf(Size.Zero) }

    val icon = if (expanded) Icons.Filled.KeyboardArrowUp else Icons.Filled.KeyboardArrowDown

    Column(Modifier.padding(20.dp)) {
        OutlinedTextField(
            value = selectedText,
            onValueChange = { selectedText = it },
            modifier = Modifier
                .fillMaxWidth()
                .onGloballyPositioned { coordinates ->
                    //This value is used to assign to the DropDown the same width
                    textFieldSize = coordinates.size.toSize()
                },
            label = { Text("Label") },
            trailingIcon = {
                Icon(icon, "contentDescription",
                    Modifier.clickable { expanded = !expanded })
            }
        )
        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false },
            modifier = Modifier
                .width(with(LocalDensity.current) { textFieldSize.width.toDp() })
        ) {
            values.forEach { label ->
                DropdownMenuItem(text = { Text(text = label) },
                    onClick = {
                        onSelected(label)
                        selectedText = label
                        expanded = false
                    })
            }
        }
    }
}
