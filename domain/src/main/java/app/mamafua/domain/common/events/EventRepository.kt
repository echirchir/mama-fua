package app.mamafua.domain.common.events

import kotlinx.coroutines.flow.Flow

interface EventRepository {
    val appEvents: Flow<AppEvent?>
    suspend fun emitEvent(event: AppEvent)
    suspend fun clearEvent()
}
