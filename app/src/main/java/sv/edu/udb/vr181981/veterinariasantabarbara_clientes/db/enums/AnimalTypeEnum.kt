package sv.edu.udb.vr181981.veterinariasantabarbara_clientes.db.enums

import sv.edu.udb.vr181981.veterinariasantabarbara_clientes.R

enum class AnimalTypeEnum(val drawableRedId: Int, val displayName: String) {
    PERRO(R.drawable.ic_dog, "Perro"),
    GATO(R.drawable.ic_cat, "Gato"),
    CONEJO(R.drawable.ic_rabbit, "Conejo"),
    PAJARO(R.drawable.ic_bird, "Pájaro"),
    PREGUNTA(R.drawable.ic_question, "Pregunta");

    companion object {
        // Función para obtener el drawableRedId por nombre
        fun getDrawableRedIdByName(name: String): Int? {
            return values().find { it.displayName.equals(name, ignoreCase = true) }?.drawableRedId
        }
    }
}
