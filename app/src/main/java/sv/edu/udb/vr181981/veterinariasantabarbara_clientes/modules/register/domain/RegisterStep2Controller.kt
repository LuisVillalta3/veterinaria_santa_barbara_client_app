package sv.edu.udb.vr181981.veterinariasantabarbara_clientes.modules.register.domain

import sv.edu.udb.vr181981.veterinariasantabarbara_clientes.db.responses.ResponseData
import sv.edu.udb.vr181981.veterinariasantabarbara_clientes.modules.register.data.model.*

class RegisterStep2Controller : RegisterController() {
    suspend fun verifyTempUser(registerStep2: RegisterFormStep2Data): ResponseData<UserTempData> {
        return registerRepository.verifyTempUser(registerStep2)
    }
}
