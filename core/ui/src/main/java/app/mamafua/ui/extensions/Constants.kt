package app.mamafua.ui.extensions

object Constants {
    const val PASSWORD_VALIDATION_REGEX = """.*(?=.*[A-Z])(?=.*[a-z])(?=.*\d).{8,}.*"""
    const val EMAIL_VALIDATION_REGEX = "^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Z|a-z]{2,}\$"

    const val USER_ID = "USER_ID_KEY"
    const val GUEST_ID = "GUEST_ID_KEY"
    const val IS_AUTHENTICATED = "IS_AUTHENTICATED_KEY"
    const val USER_EMAIL = "USER_EMAIL_KEY"
    const val USER_FIRST_NAME = "USER_FIRST_NAME_KEY"
    const val USER_FULL_NAME = "USER_FULL_NAME_KEY"

    const val USER_LAST_NAME = "USER_LAST_NAME_KEY"
    const val USER_AUTH_TOKEN = "USER_AUTH_TOKEN_KEY"
    const val USER_PASSWORD = "USER_PASSWORD_KEY"
    const val CONTINUE_LABEL = "Continue"
}
