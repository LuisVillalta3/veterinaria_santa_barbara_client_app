package sv.edu.udb.vr181981.veterinariasantabarbara_clientes.notifications.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccessTimeFilled
import androidx.compose.material.icons.filled.CalendarMonth
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithCache
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import sv.edu.udb.vr181981.veterinariasantabarbara_clientes.global.models.AnimalTypeEnum
import sv.edu.udb.vr181981.veterinariasantabarbara_clientes.ui.theme.BluePrimary

@Composable
fun NotificationCard() {
    Column(
        modifier = androidx.compose.ui.Modifier
            .fillMaxWidth()
            .shadow(elevation = 5.dp, shape = RoundedCornerShape(5.dp))
            .background(Color.White, shape = RoundedCornerShape(5.dp))
            .drawWithCache {
                onDrawWithContent {
                    val contentHeight = this.size.height
                    // draw behind the content the vertical line on the left
                    drawRoundRect(
                        color = BluePrimary,
                        topLeft = Offset.Zero,
                        size = Size(
                            15f,
                            contentHeight
                        ), // Tamaño de la línea (ancho, alto del contenido)
                        cornerRadius = CornerRadius(5f, 0f)
                    )

                    // draw the content
                    drawContent()
                }
            }
            .padding(15.dp),
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
        ) {
            Text(
                text = "Su cita esta por empezar",
                fontWeight = FontWeight.Bold,
                fontSize = 14.sp,
                color = BluePrimary
            )
            Text(
                text = "Hace 6 min",
                fontWeight = FontWeight.Normal,
                fontSize = 10.sp,
                color = Color(0xFF847D7D)
            )
        }
        Text(
            text = "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua.",
            fontSize = 12.sp,
            fontWeight = FontWeight.Normal,
            color = Color(0xFF676767),
            lineHeight = 12.sp
        )
    }
}