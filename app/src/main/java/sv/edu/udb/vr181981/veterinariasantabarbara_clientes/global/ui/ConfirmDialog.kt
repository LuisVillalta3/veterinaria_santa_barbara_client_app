package sv.edu.udb.vr181981.veterinariasantabarbara_clientes.global.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import sv.edu.udb.vr181981.veterinariasantabarbara_clientes.ui.theme.BluePrimary
import sv.edu.udb.vr181981.veterinariasantabarbara_clientes.ui.theme.GrayColor
import sv.edu.udb.vr181981.veterinariasantabarbara_clientes.ui.theme.GreenPrimary
import sv.edu.udb.vr181981.veterinariasantabarbara_clientes.ui.theme.poppinsFamily

@Composable
fun ConfirmDialog(
    showConfirmDialog: Boolean,
    titleText: String,
    descriptionText: String,
    cancelButtonText: String,
    confirmButtonText: String,
    onDismissRequest: () -> Unit,
    onConfirmAction: () -> Unit
) {
    if (showConfirmDialog) {
        Dialog(
            onDismissRequest = { onDismissRequest() },
            DialogProperties(dismissOnBackPress = false, dismissOnClickOutside = false),
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(color = Color.White, shape = RoundedCornerShape(15.dp))
                    .padding(25.dp)
            ) {
                Text(
                    text = titleText,
                    fontFamily = poppinsFamily,
                    fontWeight = FontWeight.Bold,
                    fontSize = 20.sp,
                    color = BluePrimary,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.fillMaxWidth(),
                )
                Text(
                    text = descriptionText,
                    fontFamily = poppinsFamily,
                    modifier = Modifier.fillMaxWidth(),
                    fontWeight = FontWeight.Medium,
                    fontSize = 13.sp,
                    color = GrayColor,
                    textAlign = TextAlign.Center
                )
                TextButton(onClick = { onDismissRequest() }) {
                    Text(
                        text = cancelButtonText,
                        fontFamily = poppinsFamily,
                        color = BluePrimary,
                        fontWeight = FontWeight.Medium
                    )
                }

                Button(
                    onClick = { onConfirmAction() }, colors = ButtonDefaults.buttonColors(
                        containerColor = BluePrimary
                    ),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(
                        text = confirmButtonText, fontFamily = poppinsFamily,
                        color = Color.White,
                        fontWeight = FontWeight.Medium
                    )
                }
            }
        }
    }
}