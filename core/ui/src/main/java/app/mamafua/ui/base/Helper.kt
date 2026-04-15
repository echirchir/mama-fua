package app.mamafua.ui.base

object Helper {
    fun generateUuid(): String {
        return java.util.UUID.randomUUID().toString().lowercase()
    }

    fun joinWithColon(first: String, second: String): String {
        return "$first:$second"
    }
}
