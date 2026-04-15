package app.mamafua.domain.auth.usecase

import app.mamafua.domain.auth.repository.PreferenceRepository

class SaveBooleanPreferenceUseCase(
    private val repository: PreferenceRepository
) {
    suspend operator fun invoke(key: String, value: Boolean) {
        repository.saveBoolean(key, value)
    }
}
