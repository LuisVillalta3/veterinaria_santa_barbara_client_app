package sv.edu.udb.vr181981.veterinariasantabarbara_clientes.modules.register.domain

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import sv.edu.udb.vr181981.veterinariasantabarbara_clientes.db.responses.ResponseData
import sv.edu.udb.vr181981.veterinariasantabarbara_clientes.modules.register.data.model.TempPetData
import sv.edu.udb.vr181981.veterinariasantabarbara_clientes.modules.register.data.model.UserTempData
import sv.edu.udb.vr181981.veterinariasantabarbara_clientes.modules.registerPets.data.RegisterPetFormData

class RegisterStep4Controller : RegisterController() {
    suspend fun addTempPetData(petFormData: RegisterPetFormData): ResponseData<UserTempData> {
        val petData = TempPetData(
            fechaNacimiento = petFormData.fechaNacimiento,
            razaDesconocida = petFormData.razaDesconocida,
            razaText = petFormData.razaTxt ?: "",
            generoMascotas = petFormData.generoMascota,
            especieMascota = petFormData.especie,
            especieMascotaText = petFormData.especieTxt ?: "",
            nombreMascota = petFormData.nombre,
        )
        return registerRepository.addTempPetData(petData)
    }

    fun removeTempPetData(petData: TempPetData): UserTempData {
        return registerRepository.removeTempPetData(petData)
    }

    suspend fun registerUser(): Boolean {
        return withContext(Dispatchers.IO) {
            val registerUser = registerRepository.registerUser()

            if (!registerUser) {
                return@withContext false
            }

            val registerUserData = registerRepository.addUserDataToFirestore()

            if (!registerUserData) {
                return@withContext false
            }

            val registerUserPets = registerRepository.addPetsDataToFirestore()

            if (!registerUserPets) {
                return@withContext false
            }

            return@withContext true
        }
    }
}
