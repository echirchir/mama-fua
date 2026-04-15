package app.mamafua.ui.extensions

import android.text.TextUtils
import android.util.Patterns
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.LinkAnnotation
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.withStyle
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.YearMonth
import java.time.format.DateTimeFormatter
import java.util.Calendar
import java.util.Locale

fun String.isValidEmail(): Boolean {
    return !TextUtils.isEmpty(this) && Patterns.EMAIL_ADDRESS.matcher(this).matches()
}

fun String.matchesPasswordRegex(): Boolean {
    val regex = Constants.PASSWORD_VALIDATION_REGEX.toRegex()
    return regex.matches(this)
}

fun String.ellipsisAfterWords(maxWords: Int): String {
    val words = trim().split("\\s+".toRegex())
    return if (words.size > maxWords) {
        words.take(maxWords).joinToString(" ") + " "
    } else {
        this
    }
}

fun String.buildClickableText(
    clickableText: String,
    style: SpanStyle,
    tag: String,
    onClick: ((link: LinkAnnotation) -> Unit)? = null
): AnnotatedString {
    return buildAnnotatedString {
        val startIndex = this@buildClickableText.indexOf(clickableText)
        val endIndex = startIndex + clickableText.length

        withStyle(style = style) { append(this@buildClickableText) }
        addLink(
            LinkAnnotation.Clickable(
                tag = tag,
                linkInteractionListener = onClick
            ),
            startIndex,
            endIndex
        )
    }
}

fun List<String>.toFormattedString(): String {
    return when (size) {
        0 -> ""
        1 -> this[0]
        2 -> "${this[0]} & ${this[1]}"
        else -> {
            val firstTwo = take(2).joinToString(", ")
            "$firstTwo, & ${this[2]}"
        }
    }
}


fun String.toMonthYearDisplay(): String {
    return try {
        val inputFormat = SimpleDateFormat("yyyy-MM", Locale.ENGLISH)
        val date = inputFormat.parse(this) ?: return this

        val outputFormat = SimpleDateFormat("MMMM yyyy", Locale.ENGLISH)
        outputFormat.format(date)
    } catch (e: Exception) {
        this
    }
}

fun String.toPrettyDate(): String {
    return try {
        val inputFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        val date = inputFormat.parse(this) ?: return this

        val calendar = Calendar.getInstance().apply { time = date }
        val month = SimpleDateFormat("MMM", Locale.getDefault()).format(date)
        val day = calendar.get(Calendar.DAY_OF_MONTH)

        "$month ${day}${day.ordinalSuffix()}"
    } catch (e: Exception) {
        this
    }
}

private fun Int.ordinalSuffix(): String {
    if (this in 11..13) return "th"

    return when (this % 10) {
        1 -> "st"
        2 -> "nd"
        3 -> "rd"
        else -> "th"
    }
}
