@file:OptIn(ExperimentalMaterial3Api::class)

package sv.edu.udb.vr181981.veterinariasantabarbara_clientes.global.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDefaults
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import sv.edu.udb.vr181981.veterinariasantabarbara_clientes.R
import sv.edu.udb.vr181981.veterinariasantabarbara_clientes.global.utils.Utils
import sv.edu.udb.vr181981.veterinariasantabarbara_clientes.ui.theme.BluePrimary
import sv.edu.udb.vr181981.veterinariasantabarbara_clientes.ui.theme.GreenPrimary
import sv.edu.udb.vr181981.veterinariasantabarbara_clientes.ui.theme.poppinsFamily
import java.time.Instant
import java.time.ZoneId

@Composable
fun DateTimePickerField(label: String? = null, onChangeDate: (millis: Long) -> Unit, selectedValue: String? = null) {
    val state = rememberDatePickerState()

    if (!selectedValue.isNullOrBlank()) {
        state.selectedDateMillis = Utils.dateTextToMillis(selectedValue)
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
                        state.selectedDateMillis?.let { onChangeDate(it) }
                    },
                    shape = RoundedCornerShape(7.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = BluePrimary
                    ),
                ) {
                    Text(text = "Confirmar")
                }
            }
        ) {
            DatePicker(
                state = state, showModeToggle = false, colors = DatePickerDefaults.colors(
                    selectedDayContainerColor = BluePrimary,
                    selectedYearContainerColor = BluePrimary
                )
            )
        }
    }

    val date = state.selectedDateMillis

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
            painter = painterResource(id = R.drawable.baseline_calendar_month_24),
            contentDescription = "Calendar icon"
        )
        if (date == null) {
            Text(
                text = "dd/mm/yyyy", Modifier.padding(start = 10.dp),
                fontFamily = poppinsFamily,
                fontWeight = FontWeight.SemiBold,
                fontSize = 14.sp,
                color = BluePrimary
            )
        }
        date?.let {
            val localDateText = Utils.transformMillisToDateString(date)

            Text(
                text = localDateText,
                Modifier.padding(start = 10.dp),
                fontFamily = poppinsFamily,
                fontWeight = FontWeight.SemiBold,
                fontSize = 14.sp,
                color = BluePrimary
            )
        }
    }
}