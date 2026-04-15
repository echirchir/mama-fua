package app.mamafua.domain.auth.usecase

import app.mamafua.domain.auth.repository.PreferenceRepository

class SavePreferenceUseCase(
    private val repository: PreferenceRepository
) {
    suspend operator fun invoke(key: String, value: String?) {
        repository.saveValue(key, value)
    }
}
