import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.TimeUnit
import kotlin.math.absoluteValue

object Fakes {

    private fun toDateString(datePattern: String, timeInMillis: Long, timeZone: TimeZone): String {
        // Keep a consistent style of Locale, regardless of device Locale
        val locale = Locale.US

        return SimpleDateFormat(datePattern, locale)
            .apply { this.timeZone = timeZone }
            .format(Date(timeInMillis))
    }

    private val fromMinimumPossibleTo1YearInPast = TimeRangeFormatter(
        format = { _, timeInMillis, timeZone ->
            toDateString("MMMM d, yyyy", timeInMillis, timeZone)
        },
        min = Long.MIN_VALUE,
        max = -TimeUnit.DAYS.toMillis(365L),
        inclusiveMin = true,
        inclusiveMax = true
    )

    private val from1YearTo12HoursInPast = TimeRangeFormatter(
        format = { _, timeInMillis, timeZone ->
            toDateString("MMMM d", timeInMillis, timeZone)
        },
        min = -TimeUnit.DAYS.toMillis(365L),
        max = -TimeUnit.HOURS.toMillis(12L),
        inclusiveMin = false,
        inclusiveMax = true
    )

    private val from12HoursTo2HoursInPast = TimeRangeFormatter(
        format = { delta, _, _ ->
            "${TimeUnit.MILLISECONDS.toHours(delta).absoluteValue} hours ago"
        },
        min = -TimeUnit.HOURS.toMillis(12L),
        max = -TimeUnit.HOURS.toMillis(2L),
        inclusiveMin = false,
        inclusiveMax = true
    )

    private val from2HoursTo1HourInPast = TimeRangeFormatter(
        format = { _, _, _ -> "An hour ago" },
        min = -TimeUnit.HOURS.toMillis(2L),
        max = -TimeUnit.HOURS.toMillis(1L),
        inclusiveMin = false,
        inclusiveMax = true
    )

    private val from1HourTo2MinutesInPast = TimeRangeFormatter(
        format = { delta, _, _ ->
            "${TimeUnit.MILLISECONDS.toMinutes(delta).absoluteValue} minutes ago"
        },
        min = -TimeUnit.HOURS.toMillis(1L),
        max = -TimeUnit.MINUTES.toMillis(2L),
        inclusiveMin = false,
        inclusiveMax = true
    )

    private val from2MinutesTo1MinuteInPast = TimeRangeFormatter(
        format = { _, _, _ -> "A minute ago" },
        min = -TimeUnit.MINUTES.toMillis(2L),
        max = -TimeUnit.MINUTES.toMillis(1L),
        inclusiveMin = false,
        inclusiveMax = true
    )

    private val from1MinuteInPastToPresent = TimeRangeFormatter(
        format = { _, _, _ -> "Just now" },
        min = -TimeUnit.MINUTES.toMillis(1L),
        max = 0L,
        inclusiveMin = false,
        inclusiveMax = true
    )

    private val fromPresentTo1MinuteInFuture = TimeRangeFormatter(
        format = { _, _, _ -> "Starting now" },
        min = 0L,
        max = TimeUnit.MINUTES.toMillis(1L),
        inclusiveMin = false,
        inclusiveMax = false
    )

    private val from1MinuteTo2MinutesInFuture = TimeRangeFormatter(
        format = { _, _, _ -> "In one minute" },
        min = TimeUnit.MINUTES.toMillis(1L),
        max = TimeUnit.MINUTES.toMillis(2L),
        inclusiveMin = true,
        inclusiveMax = false
    )

    private val from2MinutesTo1HourInFuture = TimeRangeFormatter(
        format = { delta, _, _ ->
            "In ${TimeUnit.MILLISECONDS.toMinutes(delta)} minutes"
        },
        min = TimeUnit.MINUTES.toMillis(2L),
        max = TimeUnit.HOURS.toMillis(1L),
        inclusiveMin = true,
        inclusiveMax = false
    )

    private val from1HourTo2HoursInFuture = TimeRangeFormatter(
        format = { _, _, _ -> "In one hour" },
        min = TimeUnit.HOURS.toMillis(1L),
        max = TimeUnit.HOURS.toMillis(2L),
        inclusiveMin = true,
        inclusiveMax = false
    )

    private val from2HoursTo12HoursInFuture = TimeRangeFormatter(
        format = { delta, _, _ ->
            "In ${TimeUnit.MILLISECONDS.toHours(delta)} hours"
        },
        min = TimeUnit.HOURS.toMillis(1L),
        max = TimeUnit.HOURS.toMillis(12L),
        inclusiveMin = true,
        inclusiveMax = false
    )

    private val from12HoursTo1YearInFuture = TimeRangeFormatter(
        format = { _, timeInMillis, timeZone ->
            toDateString("MMMM d", timeInMillis, timeZone)
        },
        min = TimeUnit.HOURS.toMillis(12L),
        max = TimeUnit.DAYS.toMillis(365L),
        inclusiveMin = true,
        inclusiveMax = false
    )

    private val from1YearToMaximumPossibleInFuture = TimeRangeFormatter(
        format = { _, timeInMillis, timeZone ->
            toDateString("MMMM d, yyyy", timeInMillis, timeZone)
        },
        min = TimeUnit.DAYS.toMillis(365L),
        max = Long.MAX_VALUE,
        inclusiveMin = true,
        inclusiveMax = true
    )

    @JvmStatic
    val timeRangeFormatters = listOf(
        fromMinimumPossibleTo1YearInPast,
        from1YearTo12HoursInPast,
        from12HoursTo2HoursInPast,
        from2HoursTo1HourInPast,
        from1HourTo2MinutesInPast,
        from2MinutesTo1MinuteInPast,
        from1MinuteInPastToPresent,
        fromPresentTo1MinuteInFuture,
        from1MinuteTo2MinutesInFuture,
        from2MinutesTo1HourInFuture,
        from1HourTo2HoursInFuture,
        from2HoursTo12HoursInFuture,
        from12HoursTo1YearInFuture,
        from1YearToMaximumPossibleInFuture
    )
}