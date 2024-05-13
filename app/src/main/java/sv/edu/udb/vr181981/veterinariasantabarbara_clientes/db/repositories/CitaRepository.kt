package sv.edu.udb.vr181981.veterinariasantabarbara_clientes.db.repositories

import android.util.Log
import com.google.android.gms.tasks.Task
import com.google.android.gms.tasks.Tasks
import com.google.firebase.firestore.DocumentSnapshot
import kotlinx.coroutines.tasks.await
import sv.edu.udb.vr181981.veterinariasantabarbara_clientes.db.entities.CitaEntity
import sv.edu.udb.vr181981.veterinariasantabarbara_clientes.db.entities.DoctorEntity
import sv.edu.udb.vr181981.veterinariasantabarbara_clientes.db.entities.PetEntity
import sv.edu.udb.vr181981.veterinariasantabarbara_clientes.db.entities.PoweredCitaEntity

class CitaRepository : Repository() {
    private suspend fun getCitasFromFirebase(filterFecha: String? = null): List<PoweredCitaEntity> {
        val userId = sharedPreferences.getUserID() ?: return listOf<PoweredCitaEntity>()
        val mutableList = mutableListOf<PoweredCitaEntity>()
        return try {
            val res = db.collection("citas").get().await()

            res.documents.forEach { cita ->
                cita.data?.let {
                    val petId = it["mascotaId"].toString()
                    val doctorId = it["doctorId"].toString()

                    val doctorSnapshot = db.collection("doctors").document(doctorId).get().await()
                    val petSnapshot = db
                        .collection("users")
                        .document(userId)
                        .collection("mascotas")
                        .document(petId)
                        .get()
                        .await()

                    val doctorData = doctorSnapshot.data ?: mapOf()
                    val petData = petSnapshot.data ?: mapOf()

                    val citaE = PoweredCitaEntity(
                        id = cita.id,
                        doctorId = doctorId,
                        fecha = it["fecha"].toString(),
                        hora = it["hora"].toString(),
                        userId = userId,
                        mascotaId = petId,
                        tipoCita = it["tipoConsulta"].toString(),
                        doctor = DoctorEntity(
                            id = doctorSnapshot.id,
                            nombre = doctorData["nombre"].toString(),
                        ),
                        mascota = PetEntity(
                            id = petSnapshot.id,
                            nombreMascota = petData["nombre"].toString(),
                            razaText = petData["raza"].toString(),
                            razaDesconocida = petData["razaDesconocida"].toString() == "true",
                            generoMascotas = petData["genero"].toString(),
                            fechaNacimiento = petData["fechaNacimiento"].toString(),
                            especie = petData["especie"].toString(),
                            especieMascotaText = petData["especieText"].toString(),
                        )
                    )

                    mutableList.add(citaE)
                }
            }


            mutableList.toList()
        } catch (e: Exception) {
            Log.d("CitaRepositoryError", "Error: ${e.message}")
            listOf<PoweredCitaEntity>()
        }
    }

    suspend fun getCitas(): List<PoweredCitaEntity> {
        return getCitasFromFirebase()
    }

    suspend fun getFutureCitas(): List<PoweredCitaEntity> {
        return getCitasFromFirebase()
    }

    suspend fun getCitasByDate(fecha: String): List<PoweredCitaEntity> {
        return getCitasFromFirebase(fecha)
    }
}