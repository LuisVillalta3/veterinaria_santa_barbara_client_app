package sv.edu.udb.vr181981.veterinariasantabarbara_clientes.modules.register.domain

import sv.edu.udb.vr181981.veterinariasantabarbara_clientes.modules.register.data.model.RegisterFormStep1Data

class RegisterStep1Controller : RegisterController() {
    // REGISTER - STEP 1
    suspend fun createTempUser(registerStep1: RegisterFormStep1Data): Boolean {
        val response =  registerRepository.createTempUser(registerStep1)
        return response.isSuccess
    }
}
