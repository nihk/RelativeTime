package ca.nihk.library

import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import java.util.TimeZone
import kotlin.math.absoluteValue
import kotlin.time.Duration
import kotlin.time.DurationUnit
import kotlin.time.toDuration

internal object TestUtils {
    private fun toDateString(datePattern: String, time: Duration, timeZone: TimeZone): String {
        // Keep a consistent style of Locale, regardless of device Locale
        val locale = Locale.US

        return SimpleDateFormat(datePattern, locale)
            .apply { this.timeZone = timeZone }
            .format(Date(time.inWholeMilliseconds))
    }

    internal val timeRangeFormatters = listOf(
        TimeRangeFormatter(dawnOfTime inclusiveToInclusive (-365).toDuration(DurationUnit.DAYS)) { info ->
            toDateString("MMMM d, yyyy", info.currentTime, info.timeZone)
        },
        TimeRangeFormatter((-365).toDuration(DurationUnit.DAYS) exclusiveToInclusive (-12).toDuration(DurationUnit.HOURS)) { info ->
            toDateString("MMMM d", info.currentTime, info.timeZone)
        },
        TimeRangeFormatter((-12).toDuration(DurationUnit.HOURS) exclusiveToInclusive (-2).toDuration(DurationUnit.HOURS)) { info ->
            "${info.delta.inWholeHours.absoluteValue.toInt()} hours ago"
        },
        TimeRangeFormatter((-2).toDuration(DurationUnit.HOURS) exclusiveToInclusive (-1).toDuration(DurationUnit.HOURS)) {
            "An hour ago"
        },
        TimeRangeFormatter((-1).toDuration(DurationUnit.HOURS) exclusiveToInclusive (-2).toDuration(DurationUnit.MINUTES)) { info ->
            "${info.delta.inWholeMinutes.absoluteValue.toInt()} minutes ago"
        },
        TimeRangeFormatter((-2).toDuration(DurationUnit.MINUTES) exclusiveToInclusive (-1).toDuration(DurationUnit.MINUTES)) {
            "A minute ago"
        },
        TimeRangeFormatter((-1).toDuration(DurationUnit.MINUTES) exclusiveToExclusive now) {
            "Seconds ago"
        },
        TimeRangeFormatter(now inclusiveToInclusive now) {
            "Now"
        },
        TimeRangeFormatter(now exclusiveToExclusive 1.toDuration(DurationUnit.MINUTES)) {
            "Seconds from now"
        },
        TimeRangeFormatter(1.toDuration(DurationUnit.MINUTES) inclusiveToExclusive 2.toDuration(DurationUnit.MINUTES)) {
            "In one minute"
        },
        TimeRangeFormatter(2.toDuration(DurationUnit.MINUTES) inclusiveToExclusive 1.toDuration(DurationUnit.HOURS)) { info ->
            "In ${info.delta.inWholeMinutes.toInt()} minutes"
        },
        TimeRangeFormatter(1.toDuration(DurationUnit.HOURS) inclusiveToExclusive 2.toDuration(DurationUnit.HOURS)) {
            "In one hour"
        },
        TimeRangeFormatter(2.toDuration(DurationUnit.HOURS) inclusiveToExclusive 12.toDuration(DurationUnit.HOURS)) { info ->
            "In ${info.delta.inWholeHours.toInt()} hours"
        },
        TimeRangeFormatter(12.toDuration(DurationUnit.HOURS) inclusiveToExclusive 365.toDuration(DurationUnit.DAYS)) { info ->
            toDateString("MMMM d", info.currentTime, info.timeZone)
        },
        TimeRangeFormatter(365.toDuration(DurationUnit.DAYS) inclusiveToInclusive endOfTime) { info ->
            toDateString("MMMM d, yyyy", info.currentTime, info.timeZone)
        }
    )

    internal val overlapping = listOf(
        TimeRangeFormatter(1.toDuration(DurationUnit.MINUTES) inclusiveToExclusive 2.toDuration(DurationUnit.MINUTES)) {
            ""
        },
        TimeRangeFormatter(1.toDuration(DurationUnit.MINUTES) inclusiveToExclusive 1.toDuration(DurationUnit.HOURS)) {
            ""
        }
    )
}
