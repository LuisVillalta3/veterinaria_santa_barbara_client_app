package sv.edu.udb.vr181981.veterinariasantabarbara_clientes.register.domain

import sv.edu.udb.vr181981.veterinariasantabarbara_clientes.global.data.ResponseData
import sv.edu.udb.vr181981.veterinariasantabarbara_clientes.register.data.model.RegisterFormStep2Data
import sv.edu.udb.vr181981.veterinariasantabarbara_clientes.register.data.model.UserTempData

class RegisterStep2Controller : RegisterController() {
    suspend fun verifyTempUser(registerStep2: RegisterFormStep2Data): ResponseData<UserTempData> {
        return registerRepository.verifyTempUser(registerStep2)
    }
}
