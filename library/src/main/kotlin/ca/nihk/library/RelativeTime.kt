package ca.nihk.library

import java.util.*
import kotlin.time.Duration
import kotlin.time.DurationUnit
import kotlin.time.toDuration

fun relativeTime(block: RelativeTime.Builder.() -> Unit): RelativeTime {
    return RelativeTime.Builder()
        .apply { block() }
        .build()
}

class RelativeTime(
    private val timeRangeFormatters: List<TimeRangeFormatter>,
    private val timeZone: TimeZone = TimeZone.getDefault(),
    private val currentTimeProvider: () -> Duration,
    private val fallback: String? = null,
    strictMode: Boolean = true
) {
    init {
        if (strictMode) {
            checkForOverlappingTimeRanges()
        }
    }

    fun from(timeInMillis: String?): String? {
        val parsed = timeInMillis?.toLongOrNull() ?: return fallback
        return from(parsed)
    }

    fun from(timeInMillis: Long): String {
        return from(timeInMillis.toDuration(DurationUnit.MILLISECONDS))
    }

    fun from(time: Duration): String {
        val delta = time - currentTimeProvider()

        return timeRangeFormatters.find { delta in it }
            ?.format?.invoke(TimeRangeFormatter.Info(delta, time, timeZone))
            ?: fallback
            ?: error("Unhandled time: $time")
    }

    private fun checkForOverlappingTimeRanges() {
        val ranges = timeRangeFormatters
        for (i in ranges.indices) {
            for (j in i + 1 until ranges.size) {
                val left = ranges[i]
                val right = ranges[j]

                check(!left.overlapsWith(right)) { "$left has an overlapping range with $right" }
            }
        }
    }

    class Builder {
        private val timeRangeFormatters = mutableListOf<TimeRangeFormatter>()
        private var timeZone: TimeZone? = null
        private var currentTimeProvider: (() -> Duration)? = null
        private var fallback: String? = null
        private var strictMode: Boolean = true

        fun timeRangeFormatter(
            range: ClosedRange<Duration>,
            format: (info: TimeRangeFormatter.Info) -> String
        ) = apply {
            timeRangeFormatter(TimeRangeFormatter(range, format))
        }

        fun timeRangeFormatter(timeRangeFormatter: TimeRangeFormatter) = apply {
            timeRangeFormatters += timeRangeFormatter
        }

        fun timeZone(timeZone: TimeZone) = apply {
            this.timeZone = timeZone
        }

        fun currentTimeProvider(currentTimeProvider: () -> Duration) = apply {
            this.currentTimeProvider = currentTimeProvider
        }

        fun fallback(fallback: String) = apply {
            this.fallback = fallback
        }

        fun strictMode(strictMode: Boolean) = apply {
            this.strictMode = strictMode
        }

        fun build(): RelativeTime {
            return RelativeTime(
                timeRangeFormatters = timeRangeFormatters,
                timeZone = timeZone ?: TimeZone.getDefault(),
                currentTimeProvider = currentTimeProvider ?: { System.currentTimeMillis().toDuration(DurationUnit.MILLISECONDS) },
                fallback = fallback,
                strictMode = strictMode
            )
        }
    }
}
