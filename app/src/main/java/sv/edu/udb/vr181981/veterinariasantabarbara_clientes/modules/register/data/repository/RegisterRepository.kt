package sv.edu.udb.vr181981.veterinariasantabarbara_clientes.modules.register.data.repository

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import sv.edu.udb.vr181981.veterinariasantabarbara_clientes.db.entities.PetEntity
import sv.edu.udb.vr181981.veterinariasantabarbara_clientes.db.entities.UserEntity
import sv.edu.udb.vr181981.veterinariasantabarbara_clientes.db.managers.CacheManager
import sv.edu.udb.vr181981.veterinariasantabarbara_clientes.db.managers.RoomManager
import sv.edu.udb.vr181981.veterinariasantabarbara_clientes.db.repositories.Repository
import sv.edu.udb.vr181981.veterinariasantabarbara_clientes.db.responses.ErrorData
import sv.edu.udb.vr181981.veterinariasantabarbara_clientes.db.responses.ResponseData
import sv.edu.udb.vr181981.veterinariasantabarbara_clientes.modules.register.data.model.RegisterFormStep1Data
import sv.edu.udb.vr181981.veterinariasantabarbara_clientes.modules.register.data.model.RegisterFormStep2Data
import sv.edu.udb.vr181981.veterinariasantabarbara_clientes.modules.register.data.model.RegisterFormStep3Data
import sv.edu.udb.vr181981.veterinariasantabarbara_clientes.modules.register.data.model.TempPetData
import sv.edu.udb.vr181981.veterinariasantabarbara_clientes.modules.register.data.model.UserTempData

class RegisterRepository : Repository() {
    private val coroutineScope = CoroutineScope(Dispatchers.Default)
    private val _cacheFileName = "register_temp_user.txt"

    @OptIn(DelicateCoroutinesApi::class)
    suspend fun createTempUser(registerStep1: RegisterFormStep1Data): ResponseData<UserTempData> {
        val responseData = MutableStateFlow(ResponseData<UserTempData>())
        try {
            val userTempData = withContext(Dispatchers.Default) {
                createTempUserLocal(registerStep1)
            }
            responseData.update { currentState ->
                currentState.copy(
                    isSuccess = true,
                    data = userTempData,
                    errorData = null
                )
            }
        } catch (ex: Exception) {
            responseData.update { currentState ->
                currentState.copy(
                    errorData = ErrorData(statusCode = null, errorMessage = ex.message)
                )
            }
        }
        return responseData.value
    }

    private fun createTempUserLocal(registerStep1: RegisterFormStep1Data): UserTempData {
        var userData: UserTempData = getTempUserData() ?: UserTempData()

        userData = userData.copy(
            email = registerStep1.email,
            password = registerStep1.password
        )

        coroutineScope.launch {
            saveTempUserOnCache(userData)
        }

        return userData
    }

    private fun simulateVerifiedTempUserLocal(registerStep2: RegisterFormStep2Data): UserTempData {
        var userData: UserTempData? = getTempUserData()

        if (registerStep2.verificationCode != "12345") {
            throw IllegalArgumentException("El código de confirmación no es correcto")
        }

        userData = userData!!.copy(
            isVerified = true
        )

        coroutineScope.launch {
            saveTempUserOnCache(userData)
        }

        return userData
    }

    private fun saveTempUserOnCache(userData: UserTempData) {
        val jsonString: String = Json.encodeToString<UserTempData>(userData)
        coroutineScope.launch {
            CacheManager.saveDataToCacheAsync(jsonString, _cacheFileName)
        }
    }

    fun removeTempUserFromCache(): Boolean {
        return CacheManager.deleteDataFromCache(_cacheFileName)
    }

    fun getTempUserData(): UserTempData? {
        val jsonString: String? = CacheManager.getDataFromCache(_cacheFileName)
        if (jsonString.isNullOrBlank()) return null
        return Json.decodeFromString<UserTempData>(jsonString)
    }

    suspend fun verifyTempUser(registerStep2: RegisterFormStep2Data): ResponseData<UserTempData> {
        val responseData = MutableStateFlow(ResponseData<UserTempData>())
        try {
            val userTempData = withContext(Dispatchers.Default) {
                simulateVerifiedTempUserLocal(registerStep2)
            }
            responseData.update { currentState ->
                currentState.copy(
                    isSuccess = userTempData.isVerified,
                    data = userTempData,
                    errorData = null
                )
            }
        } catch (ex: Exception) {
            responseData.update { currentState ->
                currentState.copy(
                    errorData = ErrorData(statusCode = null, errorMessage = ex.message)
                )
            }
        }
        return responseData.value
    }

    suspend fun addTempUserData(registerStep3: RegisterFormStep3Data): ResponseData<UserTempData> {
        val responseData = MutableStateFlow(ResponseData<UserTempData>())
        try {
            val userTempData = withContext(Dispatchers.Default) {
                simulateAddTempUserDataLocal(registerStep3)
            }
            responseData.update { currentState ->
                currentState.copy(
                    isSuccess = true,
                    data = userTempData,
                    errorData = null
                )
            }
        } catch (ex: Exception) {
            responseData.update { currentState ->
                currentState.copy(
                    errorData = ErrorData(statusCode = null, errorMessage = ex.message)
                )
            }
        }
        return responseData.value
    }

    private fun simulateAddTempUserDataLocal(registerStep3: RegisterFormStep3Data): UserTempData {
        var userData: UserTempData? = getTempUserData()

        userData = userData!!.copy(
            nombre = registerStep3.nombre,
            apellido = registerStep3.apellido,
            fechaNacimiento = registerStep3.fechaNacimiento,
            telefono = registerStep3.telefono
        )

        coroutineScope.launch {
            saveTempUserOnCache(userData)
        }

        return userData
    }

    suspend fun addTempPetData(tempPetData: TempPetData): ResponseData<UserTempData> {
        val responseData = MutableStateFlow(ResponseData<UserTempData>())
        try {
            val userTempData = withContext(Dispatchers.Default) {
                addTempPetLocal(tempPetData)
            }
            responseData.update { currentState ->
                currentState.copy(
                    isSuccess = true,
                    data = userTempData,
                    errorData = null
                )
            }
        } catch (ex: Exception) {
            responseData.update { currentState ->
                currentState.copy(
                    errorData = ErrorData(statusCode = null, errorMessage = ex.message)
                )
            }
        }
        return responseData.value
    }

    private fun addTempPetLocal(tempPetData: TempPetData): UserTempData {
        var userData: UserTempData? = getTempUserData()

        val mascotas: MutableList<TempPetData> = userData!!.mascotas
        mascotas.add(tempPetData)

        userData = userData!!.copy(
            mascotas = mascotas
        )

        coroutineScope.launch {
            saveTempUserOnCache(userData)
        }

        return userData
    }

    fun removeTempPetData(tempPetData: TempPetData): UserTempData {
        var userData: UserTempData? = getTempUserData()

        val mascotas: MutableList<TempPetData> = userData!!.mascotas
        mascotas.remove(tempPetData)

        userData = userData.copy(
            mascotas = mascotas
        )

        coroutineScope.launch {
            saveTempUserOnCache(userData)
        }

        return userData
    }

    suspend fun registerUser(): Boolean {
        return try {
            val userTempData = this.getTempUserData() ?: return false

            val authResult = firebaseAuth.createUserWithEmailAndPassword(
                userTempData.email,
                userTempData.password
            ).await()
            sharedPreferences.saveEmail(authResult.user?.email!!)
            sharedPreferences.saveUserID(authResult.user?.uid!!)
            true
        } catch (ex: Exception) {
            return false
        }
    }

    suspend fun addUserDataToFirestore(): Boolean {
        val userTempData = this.getTempUserData() ?: return false

        val user = hashMapOf(
            "email" to userTempData.email,
            "nombre" to userTempData.nombre,
            "apellido" to userTempData.apellido,
            "fechaNacimiento" to userTempData.fechaNacimiento,
            "telefono" to userTempData.telefono
        )

        val userId = sharedPreferences.getUserID() ?: return false

        return try {
            val result = db.collection("users").document(userId).set(user).await()
            addUserDataToSqlite()
            true
        } catch (ex: Exception) {
            false
        }
    }

    private suspend fun addUserDataToSqlite() {
        val userTempData = this.getTempUserData() ?: return

        val user = UserEntity(
            name = userTempData.nombre,
            lastname = userTempData.apellido,
            email = userTempData.email,
            fechaNacimiento = userTempData.fechaNacimiento,
            telefono = userTempData.telefono,
            id = sharedPreferences.getUserID()!!
        )

        RoomManager.db.userDao().insertUser(user)
    }

    suspend fun addPetsDataToFirestore(): Boolean {
        val userTempData = this.getTempUserData() ?: return false

        val userId = sharedPreferences.getUserID() ?: return false

        return try {
            // Add pets data to Firestore
            userTempData.mascotas.forEach { pet ->
                val petHashMap = hashMapOf(
                    "nombre" to pet.nombreMascota,
                    "especie" to pet.especieMascota,
                    "especieTxt" to pet.especieMascotaText,
                    "razaDesconocida" to pet.razaDesconocida,
                    "raza" to pet.razaText,
                    "genero" to pet.generoMascotas,
                    "fechaNacimiento" to pet.fechaNacimiento
                )
                val r =
                    db.collection("users").document(userId).collection("mascotas").add(petHashMap).await()
                addPetDataToSqlite(pet, r.id)
            }
            true
        } catch (ex: Exception) {
            false
        }
    }

    private suspend fun addPetDataToSqlite(pet: TempPetData, id: String) {
        val petEntity = PetEntity(
            id = id,
            nombreMascota = pet.nombreMascota,
            especie = pet.especieMascota,
            especieMascotaText = pet.especieMascotaText,
            razaDesconocida = pet.razaDesconocida,
            razaText = pet.razaText,
            generoMascotas = pet.generoMascotas,
            fechaNacimiento = pet.fechaNacimiento
        )

        RoomManager.db.petsDao().insert(petEntity)
    }
}
