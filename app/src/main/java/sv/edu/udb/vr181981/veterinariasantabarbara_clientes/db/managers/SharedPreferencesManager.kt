package sv.edu.udb.vr181981.veterinariasantabarbara_clientes.db.managers

import android.content.Context
import android.content.SharedPreferences
import sv.edu.udb.vr181981.veterinariasantabarbara_clientes.MyApp

class SharedPreferencesManager(private val context: Context = MyApp.instance.applicationContext) {
    private val sharedPreferences: SharedPreferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)

    companion object {
        private const val PREFS_NAME = "MyAppPrefs"
        private const val KEY_USERID = "UserID"
        private const val KEY_EMAIL = "email"
        private const val KEY_TOKEN = "token"
    }

    fun saveUserID(userID: String) {
        sharedPreferences.edit().putString(KEY_USERID, userID).apply()
    }

    fun getUserID(): String? {
        return sharedPreferences.getString(KEY_USERID, null)
    }

    fun saveToken(token: String) {
        sharedPreferences.edit().putString(KEY_TOKEN, token).apply()
    }

    fun getToken(): String? {
        return sharedPreferences.getString(KEY_TOKEN, null)
    }

    fun removeUserID() {
        sharedPreferences.edit().remove(KEY_USERID).apply()
    }

    fun hasUserID(): Boolean {
        return sharedPreferences.contains(KEY_USERID)
    }

    fun saveEmail(email: String) {
        sharedPreferences.edit().putString(KEY_EMAIL, email).apply()
    }

    fun getEmail(): String? {
        return sharedPreferences.getString(KEY_EMAIL, null)
    }

    fun removeEmail() {
        sharedPreferences.edit().remove(KEY_EMAIL).apply()
    }

    fun hasEmail(): Boolean {
        return sharedPreferences.contains(KEY_EMAIL)
    }

    fun clearAll() {
        sharedPreferences.edit().clear().apply()
    }

    fun isLoggedIn(): Boolean {
        return hasUserID() && hasEmail()
    }
}