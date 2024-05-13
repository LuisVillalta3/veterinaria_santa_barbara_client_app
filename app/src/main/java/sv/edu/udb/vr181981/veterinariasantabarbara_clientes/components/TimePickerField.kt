@file:OptIn(ExperimentalMaterial3Api::class)

package sv.edu.udb.vr181981.veterinariasantabarbara_clientes.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TimePicker
import androidx.compose.material3.TimePickerDefaults
import androidx.compose.material3.rememberTimePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import sv.edu.udb.vr181981.veterinariasantabarbara_clientes.R
import sv.edu.udb.vr181981.veterinariasantabarbara_clientes.utils.Utils
import sv.edu.udb.vr181981.veterinariasantabarbara_clientes.ui.theme.BluePrimary
import sv.edu.udb.vr181981.veterinariasantabarbara_clientes.ui.theme.poppinsFamily
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

@Composable
fun TimePickerField(
    label: String? = null,
    onChangeHora: (hora: String) -> Unit,
    selectedValue: String? = null
) {
    val state = rememberTimePickerState()
    var timeValue by remember { mutableStateOf("hh:mm AM") }
    val formatter = remember { SimpleDateFormat("hh:mm a", Locale.getDefault()) }

    if (!selectedValue.isNullOrBlank()) {
        timeValue = selectedValue
    }

    var showDialog by remember {
        mutableStateOf(false)
    }

    if (showDialog) {
        DatePickerDialog(
            onDismissRequest = { showDialog = false },
            confirmButton = {
                Button(
                    onClick = {
                        showDialog = false
                        val cal = Calendar.getInstance()
                        cal.set(Calendar.HOUR_OF_DAY, state.hour)
                        cal.set(Calendar.MINUTE, state.minute)
                        cal.isLenient = false
                        timeValue = formatter.format(cal.time)
                        onChangeHora(timeValue)
                    },
                    shape = RoundedCornerShape(7.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = BluePrimary
                    ),
                ) {
                    Text(text = "Confirmar")
                }
            },
            modifier = Modifier.padding(25.dp)
        ) {
            Column(
                modifier = Modifier.padding(25.dp)
            ) {
                TimePicker(
                    state = state,
                    colors = TimePickerDefaults.colors(
                        clockDialSelectedContentColor = Color.White,
                        periodSelectorSelectedContainerColor = BluePrimary,
                        periodSelectorSelectedContentColor = Color.White,
                    ),
                )
            }
        }
    }

    if (label != null) {
        Text(
            text = label, Modifier.padding(start = 10.dp),
            fontFamily = poppinsFamily,
            fontWeight = FontWeight.SemiBold,
            fontSize = 14.sp,
            color = BluePrimary
        )
    }
    Row(
        Modifier
            .fillMaxWidth()
            .border(width = 1.dp, color = BluePrimary, shape = RoundedCornerShape(6.dp))
            .padding(15.dp)
            .clickable { showDialog = true }
    ) {
        Image(
            painter = painterResource(id = R.drawable.baseline_access_time_filled_24),
            contentDescription = "Calendar icon"
        )
        Text(
            text = timeValue, Modifier.padding(start = 10.dp),
            fontFamily = poppinsFamily,
            fontWeight = FontWeight.SemiBold,
            fontSize = 14.sp,
            color = BluePrimary
        )
    }
}