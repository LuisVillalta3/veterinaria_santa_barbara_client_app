package sv.edu.udb.vr181981.veterinariasantabarbara_clientes.db.repositories

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import sv.edu.udb.vr181981.veterinariasantabarbara_clientes.db.managers.SharedPreferencesManager

abstract class Repository {
    protected val firebaseAuth: FirebaseAuth by lazy { FirebaseAuth.getInstance() }
    protected val db: FirebaseFirestore by lazy { FirebaseFirestore.getInstance() }
    protected val sharedPreferences: SharedPreferencesManager = SharedPreferencesManager()
}