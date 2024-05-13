package sv.edu.udb.vr181981.veterinariasantabarbara_clientes.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import sv.edu.udb.vr181981.veterinariasantabarbara_clientes.ui.theme.BluePrimary
import sv.edu.udb.vr181981.veterinariasantabarbara_clientes.ui.theme.GreenPrimary
import sv.edu.udb.vr181981.veterinariasantabarbara_clientes.ui.theme.poppinsFamily

@Composable
fun SteppersButtons(backButtonText: String, nextButtonText: String, backBtnAction: () -> Unit, nextBtnAction: () -> Unit) {
    Row(
        modifier = Modifier
            .background(BluePrimary)
            .padding(horizontal = 25.dp, vertical = 15.dp)
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Box(
            modifier = Modifier
                .weight(0.35f)
                .fillMaxWidth()
                .padding(end = 10.dp)
        ) {
            Button(
                onClick = backBtnAction, shape = RoundedCornerShape(7.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.White
                ),
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                Text(
                    text = backButtonText,
                    fontFamily = poppinsFamily,
                    fontWeight = FontWeight.SemiBold,
                    color = BluePrimary,
                    fontSize = 16.sp
                )
            }
        }

        Button(
            onClick = { nextBtnAction() }, shape = RoundedCornerShape(7.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = GreenPrimary
            ),
            modifier = Modifier
                .weight(0.35f)
                .fillMaxWidth()
        ) {
            Text(
                text = nextButtonText, fontFamily = poppinsFamily,
                fontWeight = FontWeight.SemiBold,
                color = Color.White,
                fontSize = 16.sp
            )
        }
    }
}