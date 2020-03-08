import java.util.*

class RelativeTime(
    private val timeRangeFormatters: List<TimeRangeFormatter>,
    private val timeZone: TimeZone = TimeZone.getDefault(),
    private val currentTimeProvider: () -> Long = { System.currentTimeMillis() },
    private val fallback: String? = ""
) {

    fun from(timeInMillis: String?): String? {
        val parsed = timeInMillis?.toLongOrNull() ?: return fallback
        return from(parsed)
    }

    fun from(timeInMillis: Long): String? {
        val delta = timeInMillis - currentTimeProvider()

        return try {
            timeRangeFormatters.find { it.contains(delta) }
                ?.format?.invoke(delta, timeInMillis, timeZone) ?: fallback
        } catch (e: Exception) {
            fallback
        }
    }
}