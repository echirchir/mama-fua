package app.mamafua.domain.common.events

import kotlinx.serialization.Serializable

@Serializable
sealed class AppEvent {
    @Serializable
    data class UserAuthenticated(
        val userId: String,
        val email: String,
        val timestamp: Long = System.currentTimeMillis()
    ) : AppEvent()

    @Serializable
    data class UserSignedUp(
        val userId: String,
        val email: String,
        val timestamp: Long = System.currentTimeMillis()
    ) : AppEvent()

    @Serializable
    object UserLoggedOut : AppEvent()

    @Serializable
    object ShouldRefreshData : AppEvent()
}
