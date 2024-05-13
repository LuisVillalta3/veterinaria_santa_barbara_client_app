package sv.edu.udb.vr181981.veterinariasantabarbara_clientes.modules.citas.citaform.data

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import sv.edu.udb.vr181981.veterinariasantabarbara_clientes.db.managers.CacheManager
import sv.edu.udb.vr181981.veterinariasantabarbara_clientes.db.repositories.Repository
import sv.edu.udb.vr181981.veterinariasantabarbara_clientes.modules.register.data.model.UserTempData

class RegisterCitasRepository : Repository() {
    private val coroutineScope = CoroutineScope(Dispatchers.Default)
    private val _cacheFileName = "register_temp_cita.txt"

    fun createTempCita(mascotaID: String, mascota: String, especie: String): Boolean {
        val tempCita = CitaTempData(mascotaId = mascotaID, mascota = mascota, especie = especie)
        saveTempCitaOnCache(tempCita)
        return true
    }

    private fun saveTempCitaOnCache(tempCita: CitaTempData) {
        val jsonString: String = Json.encodeToString<CitaTempData>(tempCita)
        coroutineScope.launch {
            CacheManager.saveDataToCacheAsync(jsonString, _cacheFileName)
        }
    }

    fun removeTempCitaFromCache(): Boolean {
        return CacheManager.deleteDataFromCache(_cacheFileName)
    }

    fun getTempCitaData(): CitaTempData? {
        val jsonString: String? = CacheManager.getDataFromCache(_cacheFileName)
        if (jsonString.isNullOrBlank()) return null
        return Json.decodeFromString<CitaTempData>(jsonString)
    }

    fun addCitaData(registerCitaFormData: RegisterCitaFormData): Boolean {
        val tempCita = getTempCitaData() ?: return false
        saveTempCitaOnCache(tempCita.copy(
            fecha = registerCitaFormData.fecha,
            tipoConsulta = registerCitaFormData.tipoConsulta,
            doctorId = registerCitaFormData.doctorId,
            doctor = registerCitaFormData.doctorName,
            hora = mapOf(
                "inicio" to registerCitaFormData.horaInicio
            )
        ))
        return true
    }

    suspend fun registerCita(): Boolean {
        val tempCita = getTempCitaData() ?: return false
        val userId = sharedPreferences.getUserID() ?: return false

        return try {
            val cita = hashMapOf(
                "mascotaId" to tempCita.mascotaId,
                "userId" to userId,
                "fecha" to tempCita.fecha,
                "doctorId" to tempCita.doctorId,
                "hora" to tempCita.hora["inicio"],
                "tipoConsulta" to tempCita.tipoConsulta
            )

            db.collection("citas").add(cita).await()

            true
        } catch (e: Exception) {
            false
        }
    }
}