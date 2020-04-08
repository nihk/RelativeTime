import java.text.SimpleDateFormat
import java.util.*
import kotlin.math.absoluteValue
import kotlin.time.Duration
import kotlin.time.days
import kotlin.time.hours
import kotlin.time.minutes

internal object Fakes {
    private fun toDateString(datePattern: String, time: Duration, timeZone: TimeZone): String {
        // Keep a consistent style of Locale, regardless of device Locale
        val locale = Locale.US

        return SimpleDateFormat(datePattern, locale)
            .apply { this.timeZone = timeZone }
            .format(Date(time.toLongMilliseconds()))
    }

    internal val timeRangeFormatters = listOf(
        TimeRangeFormatter(dawnOfTime inclusiveToInclusive (-365).days) { _, time, timeZone ->
            toDateString("MMMM d, yyyy", time, timeZone)
        },
        TimeRangeFormatter((-365).days exclusiveToInclusive (-12).hours) { _, time, timeZone ->
            toDateString("MMMM d", time, timeZone)
        },
        TimeRangeFormatter((-12).hours exclusiveToInclusive (-2).hours) { delta, _, _ ->
            "${delta.inHours.absoluteValue.toInt()} hours ago"
        },
        TimeRangeFormatter((-2).hours exclusiveToInclusive (-1).hours) { _, _, _ ->
            "An hour ago"
        },
        TimeRangeFormatter((-1).hours exclusiveToInclusive (-2).minutes) { delta, _, _ ->
            "${delta.inMinutes.absoluteValue.toInt()} minutes ago"
        },
        TimeRangeFormatter((-2).minutes exclusiveToInclusive (-1).minutes) { _, _, _ ->
            "A minute ago"
        },
        TimeRangeFormatter((-1).minutes exclusiveToExclusive now) { _, _, _ ->
            "Seconds ago"
        },
        TimeRangeFormatter(now inclusiveToInclusive now) { _, _, _ ->
            "Now"
        },
        TimeRangeFormatter(now exclusiveToExclusive 1.minutes) { _, _, _ ->
            "Seconds from now"
        },
        TimeRangeFormatter(1.minutes inclusiveToExclusive 2.minutes) { _, _, _ ->
            "In one minute"
        },
        TimeRangeFormatter(2.minutes inclusiveToExclusive 1.hours) { delta, _, _ ->
            "In ${delta.inMinutes.toInt()} minutes"
        },
        TimeRangeFormatter(1.hours inclusiveToExclusive 2.hours) { _, _, _ ->
            "In one hour"
        },
        TimeRangeFormatter(2.hours inclusiveToExclusive 12.hours) { delta, _, _ ->
            "In ${delta.inHours.toInt()} hours"
        },
        TimeRangeFormatter(12.hours inclusiveToExclusive 365.days) { _, time, timeZone ->
            toDateString("MMMM d", time, timeZone)
        },
        TimeRangeFormatter(365.days inclusiveToInclusive endOfTime) { _, time, timeZone ->
            toDateString("MMMM d, yyyy", time, timeZone)
        }
    )
}