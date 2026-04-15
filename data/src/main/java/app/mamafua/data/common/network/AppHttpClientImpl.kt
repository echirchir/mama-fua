package app.mamafua.data.common.network

import android.util.Log
import app.mamafua.domain.auth.usecase.SaveBooleanPreferenceUseCase
import app.mamafua.domain.auth.usecase.SavePreferenceUseCase
import app.mamafua.domain.common.events.AppEvent
import app.mamafua.domain.common.events.EventRepository
import app.mamafua.ui.extensions.Constants
import io.ktor.client.HttpClient
import io.ktor.client.engine.android.Android
import io.ktor.client.plugins.HttpResponseValidator
import io.ktor.client.plugins.HttpSend
import io.ktor.client.plugins.HttpTimeout
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.defaultRequest
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.client.plugins.plugin
import io.ktor.client.request.headers
import io.ktor.client.statement.HttpResponse
import io.ktor.http.ContentType
import io.ktor.http.HttpHeaders
import io.ktor.http.HttpStatusCode
import io.ktor.http.contentType
import io.ktor.serialization.kotlinx.json.json
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json


class AppHttpClientImpl(
    private val baseUrlProvider: BaseUrlProvider,
    private val eventRepository: EventRepository,
    private val savePreferenceUseCase: SavePreferenceUseCase,
    private val saveBooleanPreferenceUseCase: SaveBooleanPreferenceUseCase
) : AppHttpClient {
    companion object {
        private const val API_VERSION = "application/vnd.mamafua.v1"
        private const val GUEST_ID_KEY = "X-Guest-UUID"
        private const val TIME_OUT = 60_000L
        private const val TAG = "NetworkClient"
    }

    @OptIn(ExperimentalSerializationApi::class)
    private val client = HttpClient(Android) {
        expectSuccess = false

        defaultRequest {
            url(baseUrlProvider.getBaseUrl())
            contentType(ContentType.Application.Json)
        }

        install(HttpTimeout) {
            connectTimeoutMillis = TIME_OUT
            requestTimeoutMillis = TIME_OUT
            socketTimeoutMillis = TIME_OUT
        }

        install(ContentNegotiation) {
            json(Json {
                explicitNulls = false
                prettyPrint = true
                isLenient = true
                ignoreUnknownKeys = true
                coerceInputValues = true
            })
        }

        install(Logging) {
            logger = object : Logger {
                override fun log(message: String) {
                    Log.d(TAG, "HTTP Log message: $message")
                }
            }
            level = LogLevel.ALL
        }

        HttpResponseValidator {
            validateResponse { response: HttpResponse ->
                if (response.status == HttpStatusCode.Unauthorized) {
                    Log.w(TAG, "401 Unauthorized - Token expired, logging out user")
                    handleUnauthorized()
                }
            }

            handleResponseExceptionWithRequest { exception, request ->
                Log.e(TAG, "HTTP Exception: ${exception.localizedMessage}")
                Log.e(TAG, "Request URL: ${request.url}")
            }
        }
    }

    override fun getClient(guestId: String, isGuestUser: Boolean, authString: String?): HttpClient {
        client.plugin(HttpSend).intercept { request ->
            request.headers {
                if (contains(HttpHeaders.Authorization)) {
                    remove(HttpHeaders.Authorization)
                }
                if (authString != null) {
                    append(HttpHeaders.Authorization, "Basic $authString")
                }

                if (contains(GUEST_ID_KEY)) {
                    remove(GUEST_ID_KEY)
                }

                if (guestId.isNotBlank()) {
                    append(GUEST_ID_KEY, guestId)
                }

                val acceptHeader = HttpHeaders.Accept
                if (contains(acceptHeader)) {
                    remove(acceptHeader)
                }
                append(acceptHeader, API_VERSION)
            }
            execute(request)
        }

        return client
    }

    private fun handleUnauthorized() {
        // Use a coroutine scope to handle the async operations
        CoroutineScope(Dispatchers.IO).launch {
            try {
                Log.d(TAG, "Clearing user authentication data...")

                // Clear all user-related preferences
                savePreferenceUseCase(Constants.USER_FIRST_NAME, null)
                savePreferenceUseCase(Constants.USER_LAST_NAME, null)
                savePreferenceUseCase(Constants.USER_FULL_NAME, null)
                savePreferenceUseCase(Constants.USER_EMAIL, null)
                savePreferenceUseCase(Constants.USER_PASSWORD, null)
                savePreferenceUseCase(Constants.USER_AUTH_TOKEN, null)
                savePreferenceUseCase(Constants.USER_ID, null)
                saveBooleanPreferenceUseCase(Constants.IS_AUTHENTICATED, false)

                // Emit logout event to notify all modules
                eventRepository.emitEvent(AppEvent.UserLoggedOut)

                Log.d(TAG, "User logged out successfully due to expired token")
            } catch (e: Exception) {
                Log.e(TAG, "Error during automatic logout: ${e.localizedMessage}")
            }
        }
    }
}

interface AppHttpClient {
    fun getClient(guestId: String, isGuestUser: Boolean = false, authString: String? = null): HttpClient
}

interface BaseUrlProvider {
    fun getBaseUrl(): String
}
