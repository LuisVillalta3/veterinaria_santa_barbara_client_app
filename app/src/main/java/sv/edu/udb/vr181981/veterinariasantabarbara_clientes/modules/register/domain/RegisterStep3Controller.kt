package sv.edu.udb.vr181981.veterinariasantabarbara_clientes.modules.register.domain

import sv.edu.udb.vr181981.veterinariasantabarbara_clientes.db.responses.ResponseData
import sv.edu.udb.vr181981.veterinariasantabarbara_clientes.modules.register.data.model.RegisterFormStep3Data
import sv.edu.udb.vr181981.veterinariasantabarbara_clientes.modules.register.data.model.UserTempData

class RegisterStep3Controller : RegisterController() {
    suspend fun addTempUserData(registerStep3: RegisterFormStep3Data): ResponseData<UserTempData> {
        return registerRepository.addTempUserData(registerStep3)
    }
}
