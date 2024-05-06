package sv.edu.udb.vr181981.veterinariasantabarbara_clientes.global.utils

import java.time.Instant
import java.time.LocalDate
import java.time.ZoneId
import java.time.ZoneOffset
import java.time.format.DateTimeFormatter

object Utils {
    fun transformMillisToDateString(millis: Long? = 0): String {
        if (millis == null) return "dd/mm/yyyy"
        val localDate = Instant.ofEpochMilli(millis).atZone(ZoneId.of("UTC")).toLocalDate()
        return "${localDate.dayOfMonth.toString().padStart(2, '0')}/${
            localDate.monthValue.toString().padStart(2, '0')
        }/${localDate.year}"
    }

    fun dateTextToMillis(date: String, dateformat: String = "dd/MM/yyyy"): Long {
        val formatter = DateTimeFormatter.ofPattern(dateformat)
        val localDate = LocalDate.parse(date, formatter)
        val instant = localDate.atStartOfDay(ZoneOffset.UTC).toInstant()
        return instant.toEpochMilli()
    }
}