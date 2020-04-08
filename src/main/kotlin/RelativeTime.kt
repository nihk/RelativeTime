import java.util.*
import kotlin.time.Duration
import kotlin.time.milliseconds

class RelativeTime(
    private val timeRangeFormatters: List<TimeRangeFormatter>,
    private val timeZone: TimeZone = TimeZone.getDefault(),
    private val currentTimeProvider: () -> Duration = { System.currentTimeMillis().milliseconds },
    private val fallback: String? = null,
    private val onThrowableCaught: (Throwable) -> Unit = {}
) {

    fun from(timeInMillis: String?): String? {
        val parsed = timeInMillis?.toLongOrNull() ?: return fallback
        return from(parsed)
    }

    fun from(timeInMillis: Long): String? {
        return from(timeInMillis.milliseconds)
    }

    fun from(time: Duration): String? {
        val delta = time - currentTimeProvider()

        return try {
            timeRangeFormatters.find { it.contains(delta) }
                ?.format?.invoke(delta, time, timeZone)
                ?: fallback
        } catch (throwable: Throwable) {
            onThrowableCaught(throwable)
            fallback
        }
    }
}