package sv.edu.udb.vr181981.veterinariasantabarbara_clientes.db.enums

import sv.edu.udb.vr181981.veterinariasantabarbara_clientes.R

enum class TipoConsultaEnum(val drawableRedId: Int, val displayName: String) {
    ESTILO(R.drawable.baseline_bathtub_24, "Estilo"),
    DESPARASITACION(R.drawable.baseline_bug_report_24, "Desparasitación"),
    CHEQUEO(R.drawable.baseline_check_circle_24, "Chequeo");

    companion object {
        // Función para obtener el drawableRedId por nombre
        fun getDrawableRedIdByName(name: String): Int? {
            return AnimalTypeEnum.values().find { it.displayName.equals(name, ignoreCase = true) }?.drawableRedId
        }
    }
}