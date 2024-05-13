package sv.edu.udb.vr181981.veterinariasantabarbara_clientes.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import sv.edu.udb.vr181981.veterinariasantabarbara_clientes.ui.theme.BluePrimary
import sv.edu.udb.vr181981.veterinariasantabarbara_clientes.ui.theme.poppinsFamily

@Composable
fun IconRadioGroup(
    options: List<Pair<Int, String>>,
    selectedOption: String,
    label: String? = null,
    onChangeOption: (String) -> Unit = {},
    horizontalArrangement: Arrangement.Horizontal = Arrangement.SpaceBetween
) {
    if (label != null) {
        Text(
            text = label,
            fontFamily = poppinsFamily,
            fontWeight = FontWeight.SemiBold,
            fontSize = 14.sp,
            color = BluePrimary
        )
    }
    Row(
        modifier = Modifier
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        for (option in options) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .padding(vertical = 8.dp)
                    .background(
                        color = if (selectedOption == option.second) BluePrimary else Color.White,
                        shape = MaterialTheme.shapes.medium
                    )
                    .border(
                        width = 3.dp,
                        color = BluePrimary,
                        shape = MaterialTheme.shapes.medium
                    )
                    .clickable { onChangeOption(option.second) }
                    .padding(12.dp),
                horizontalArrangement = horizontalArrangement
            ) {
                Image(
                    painter = painterResource(id = option.first),
                    contentDescription = option.second,
                    modifier = Modifier.size(30.dp),
                    colorFilter = ColorFilter
                        .tint(color = if (selectedOption == option.second) Color.White else BluePrimary)
                )
            }
        }
    }
}
