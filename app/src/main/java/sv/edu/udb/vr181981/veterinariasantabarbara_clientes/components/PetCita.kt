package sv.edu.udb.vr181981.veterinariasantabarbara_clientes.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithCache
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import sv.edu.udb.vr181981.veterinariasantabarbara_clientes.db.enums.AnimalTypeEnum
import androidx.compose.material3.*
import androidx.compose.ui.unit.sp
import sv.edu.udb.vr181981.veterinariasantabarbara_clientes.db.entities.PoweredCitaEntity

@Composable
fun PetCitaCard(cita: PoweredCitaEntity, onClick: () -> Unit = {}) {
    val cardMainColor = when (cita.tipoCita) {
        "Chequeo" -> Color(0xFF7141BF)
        "Desparasitación" -> Color(0xFFFF3D00)
        "Emergencia" -> Color.Red
        "Estilo" -> Color(0xFF2196F3)
        "Prenatal" -> Color(0xFFEC1087)
        "Cirugia" -> Color(0xFF060DA3)
        else -> Color(0xFFFF3D00)
    }

    val cardContainerColor = when (cita.tipoCita) {
        "Chequeo" -> Color(0xFF8D61D5)
        "Desparasitación" -> Color(0xFFD5A061)
        "Emergencia" -> Color(0xFFD83939)
        "Estilo" -> Color(0xFF61C7D5)
        "Prenatal" -> Color(0xFFD361D5)
        "Cirugia" -> Color(0xFF7161D5)
        else -> Color(0xFFD5A061)
    }

    Row(
        modifier = Modifier
            .background(cardContainerColor.copy(alpha = .2f), shape = RoundedCornerShape(5.dp))
            .fillMaxWidth()
            .drawWithCache {
                onDrawWithContent {
                    val contentHeight = this.size.height
                    // draw behind the content the vertical line on the left
                    drawRoundRect(
                        color = cardMainColor,
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
            .padding(15.dp)
            .clickable { onClick() },
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .background(cardMainColor, shape = CircleShape)
                .padding(15.dp)
                .size(55.dp),
            contentAlignment = Alignment.Center
        ) {
            Image(
                painter = painterResource(id = AnimalTypeEnum.getDrawableRedIdByName(cita.mascota.especie)!!),
                contentDescription = cita.mascota.especie,
                colorFilter = ColorFilter.tint(Color.White),
                modifier = Modifier.size(50.dp)
            )
        }
        Spacer(modifier = Modifier.width(15.dp))
        Column {
            Text(
                text = cita.mascota.nombreMascota,
                color = cardMainColor,
                fontWeight = FontWeight.Bold,
                fontSize = 20.sp
            )
            Text(
                text = cita.tipoCita,
                color = cardMainColor,
                fontWeight = FontWeight.Normal,
                fontSize = 14.sp
            )
            Text(
                text = "Dr. ${cita.doctor.nombre}",
                color = cardMainColor,
                fontWeight = FontWeight.Normal,
                fontSize = 14.sp
            )
            Column(
                modifier = Modifier.fillMaxWidth(),
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = Icons.Filled.CalendarMonth,
                        contentDescription = "Fecha",
                        tint = cardMainColor,
                        modifier = Modifier.size(16.dp)
                    )
                    Text(
                        text = cita.fecha,
                        color = cardMainColor,
                        fontWeight = FontWeight.Normal,
                        fontSize = 14.sp
                    )
                }
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = Icons.Filled.AccessTimeFilled,
                        contentDescription = "Hora",
                        tint = cardMainColor,
                        modifier = Modifier.size(16.dp)
                    )
                    Text(
                        text = cita.hora,
                        color = cardMainColor,
                        fontWeight = FontWeight.Normal,
                        fontSize = 14.sp
                    )
                }
            }
        }
    }
}