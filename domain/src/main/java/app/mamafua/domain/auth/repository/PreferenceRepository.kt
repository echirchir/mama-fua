package app.mamafua.domain.auth.repository

interface PreferenceRepository {
    suspend fun saveValue(key: String, value: String?)
    suspend fun saveBoolean(key: String, value: Boolean)
    suspend fun getValue(key: String): String?
    suspend fun getBoolean(key: String): Boolean
}
