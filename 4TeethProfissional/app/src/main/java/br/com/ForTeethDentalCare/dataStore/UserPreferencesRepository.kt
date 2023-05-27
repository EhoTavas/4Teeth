package br.com.ForTeethDentalCare.dataStore

import android.content.Context
import androidx.core.content.edit

private const val USER_PREFERENCES_NAME = "prefs_tokens"

private const val UID_KEY = "uid"
private const val FCMTOKEN_KEY = "fcmToken"
private const val KEY_USERNAME = "username"
private const val KEY_PASSWORD = "password"
private const val OPENED_BEFORE = "firstTime"

/**
 * Class that handles saving and retrieving user preferences
 */
class UserPreferencesRepository private constructor(context: Context) {

    private val sharedPreferences =
        context.applicationContext.getSharedPreferences(USER_PREFERENCES_NAME, Context.MODE_PRIVATE)

    var uid: String = ""
        get() {
            return sharedPreferences.getString(UID_KEY, "")!!
        }

    fun updateUid(newUid: String) {
        sharedPreferences.edit {
            putString(UID_KEY, newUid)
        }
    }

    var fcmToken: String = ""
        get() {
            return sharedPreferences.getString(FCMTOKEN_KEY, "")!!
        }

    fun updateFcmToken(newFcmToken: String) {
        sharedPreferences.edit {
            putString(FCMTOKEN_KEY, newFcmToken)
        }
    }

    val username: String
        get() {
            return sharedPreferences.getString(KEY_USERNAME, "")!!
        }

    fun updateUsername(username: String) {
        sharedPreferences.edit {
            putString(KEY_USERNAME, username)
        }
    }

    val password: String
        get() {
            return sharedPreferences.getString(KEY_PASSWORD, "")!!
        }

    fun updatePassword(password: String) {
        sharedPreferences.edit {
            putString(KEY_PASSWORD, password)
        }
    }

    val firstTime: Int
        get() {
            return sharedPreferences.getInt(OPENED_BEFORE, 0)
        }

    fun updateFirstTime(firstTime: Int) {
        sharedPreferences.edit {
            putInt(KEY_PASSWORD, firstTime)
        }
    }

    companion object {
        @Volatile
        private var INSTANCE: UserPreferencesRepository? = null

        fun getInstance(context: Context): UserPreferencesRepository {
            return INSTANCE ?: synchronized(this) {
                INSTANCE?.let {
                    return it
                }

                val instance = UserPreferencesRepository(context)
                INSTANCE = instance
                instance
            }
        }
    }
}