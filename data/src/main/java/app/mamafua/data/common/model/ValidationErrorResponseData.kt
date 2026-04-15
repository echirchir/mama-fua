package app.mamafua.data.common.model

import app.mamafua.domain.common.ValidationErrorResponse
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ValidationErrorResponseData(
    @SerialName("errors")
    val errors: List<String>
)

internal fun ValidationErrorResponseData.toDomain() = ValidationErrorResponse(
    errors = errors
)
