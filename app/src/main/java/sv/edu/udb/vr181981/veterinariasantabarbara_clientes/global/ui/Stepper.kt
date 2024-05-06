package sv.edu.udb.vr181981.veterinariasantabarbara_clientes.global.ui

import androidx.compose.foundation.Canvas
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
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import sv.edu.udb.vr181981.veterinariasantabarbara_clientes.ui.theme.BluePrimary
import sv.edu.udb.vr181981.veterinariasantabarbara_clientes.ui.theme.GreenPrimary
import sv.edu.udb.vr181981.veterinariasantabarbara_clientes.ui.theme.poppinsFamily

@Composable
fun Stepper(step: Int, totalSteps: Int, currentStepText: String, nextStepText: String? = null) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(130.dp)
            .background(color = BluePrimary)
            .padding(25.dp),
    ) {
        CircularProgressBar(totalSteps, step, modifier = Modifier.size(85.dp))
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
        ) {
            Text(
                text = currentStepText,
                fontFamily = poppinsFamily,
                fontSize = 22.sp,
                fontWeight = FontWeight.Black,
                textAlign = TextAlign.End,
                color = GreenPrimary,
                modifier = Modifier.fillMaxWidth()
            )
            if (nextStepText != null) {
                Text(
                    text = "Siguiente: $nextStepText", fontFamily = poppinsFamily,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Medium,
                    textAlign = TextAlign.End,
                    color = Color.White,
                    modifier = Modifier.fillMaxWidth()
                )
            }
        }
    }
}

@Composable
fun CircularProgressBar(
    totalSteps: Int,
    currentStep: Int,
    modifier: Modifier = Modifier
) {
    val percentage = (currentStep - 1).toFloat() / totalSteps.toFloat()

    Box(
        modifier = modifier
            .fillMaxSize()
    ) {
        Canvas(
            modifier = Modifier
                .fillMaxSize()
        ) {
            val radius = size.minDimension / 2
            val center = Offset(size.width / 2, size.height / 2)

            val startAngle = -90f
            val sweepAngle = percentage * 360

            // Draw white circle
            drawCircle(
                color = Color.Transparent,
                center = center,
                radius = radius,
                style = Stroke(width = 8.dp.toPx())
            )

            // Draw green arc
            drawArc(
                color = Color.White,
                startAngle = startAngle,
                sweepAngle = 360f,
                useCenter = false,
                style = Stroke(width = 8.dp.toPx())
            )

            drawArc(
                color = GreenPrimary,
                startAngle = startAngle,
                sweepAngle = sweepAngle,
                useCenter = false,
                style = Stroke(width = 8.dp.toPx())
            )
        }

        val progressText = "$currentStep de $totalSteps"
        Text(
            text = progressText,
            style = TextStyle(
                fontFamily = poppinsFamily,
                fontWeight = FontWeight.Bold,
                fontSize = 16.sp,
                color = Color.White
            ),
            modifier = Modifier.align(Alignment.Center)
        )
    }
}