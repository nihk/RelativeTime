import java.util.*
import kotlin.time.Duration
import kotlin.time.milliseconds

class RelativeTime(
    private val timeRangeFormatters: List<TimeRangeFormatter>,
    private val timeZone: TimeZone = TimeZone.getDefault(),
    private val currentTimeProvider: () -> Duration = { System.currentTimeMillis().milliseconds },
    private val fallback: String? = null,
    private val onThrowableCaught: (Throwable) -> Unit = {},
    checkForOverlappingTimeRanges: Boolean = true
) {

    init {
        if (checkForOverlappingTimeRanges) {
            val ranges = timeRangeFormatters.map(TimeRangeFormatter::range)
            for (i in ranges.indices) {
                for (j in i + 1 until ranges.size) {
                    val left = ranges[i]
                    val right = ranges[j]

                    check(left.start !in right && left.endInclusive !in right) {
                        "$left has an overlapping range with $right"
                    }
                }
            }
        }
    }

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