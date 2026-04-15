package app.mamafua.ui.base

import timber.log.Timber

object AppLogger {

    private const val TAG = "MAMAFUA: "

    fun d(message: String) {
        Timber.d("$TAG $message")
    }

    fun e(message: String, throwable: Throwable? = null) {
        Timber.e(throwable, message)
    }

    fun i(message: String) {
        Timber.i("$TAG $message")
    }

    fun v(message: String) {
        Timber.v("$TAG $message")
    }

    fun w(message: String) {
        Timber.w("$TAG $message")
    }
}
