package app.mamafua.data.common.model

import kotlinx.serialization.Serializable

@Serializable
data class ErrorResponseData(val title: String, val message: String)
