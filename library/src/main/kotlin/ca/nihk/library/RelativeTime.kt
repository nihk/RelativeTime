package ca.nihk.library

import java.util.TimeZone
import kotlin.time.Duration
import kotlin.time.DurationUnit
import kotlin.time.toDuration

fun <T> relativeTime(block: RelativeTime.Builder<T>.() -> Unit): RelativeTime<T> {
    return RelativeTime.Builder<T>()
        .apply(block)
        .build()
}

class RelativeTime<T>(
    private val timeRangeFormatters: List<TimeRangeFormatter<T>>,
    private val timeZone: TimeZone = TimeZone.getDefault(),
    private val currentTimeProvider: () -> Duration,
    private val fallback: T? = null,
    strictMode: Boolean = true
) {
    init {
        if (strictMode) {
            checkForOverlappingTimeRanges()
        }
    }

    fun from(timeInMillis: String?): T? {
        val parsed = timeInMillis?.toLongOrNull() ?: return fallback
        return from(parsed)
    }

    fun from(timeInMillis: Long): T {
        return from(timeInMillis.toDuration(DurationUnit.MILLISECONDS))
    }

    fun from(time: Duration): T {
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

    class Builder<T> {
        private val timeRangeFormatters = mutableListOf<TimeRangeFormatter<T>>()
        private var timeZone: TimeZone? = null
        private var currentTimeProvider: (() -> Duration)? = null
        private var fallback: T? = null
        private var strictMode: Boolean = true

        fun timeRangeFormatter(
            range: ClosedRange<Duration>,
            format: (info: TimeRangeFormatter.Info) -> T
        ) = apply {
            timeRangeFormatter(TimeRangeFormatter(range, format))
        }

        fun timeRangeFormatter(timeRangeFormatter: TimeRangeFormatter<T>) = apply {
            timeRangeFormatters += timeRangeFormatter
        }

        fun timeZone(timeZone: TimeZone) = apply {
            this.timeZone = timeZone
        }

        fun currentTimeProvider(currentTimeProvider: () -> Duration) = apply {
            this.currentTimeProvider = currentTimeProvider
        }

        fun fallback(fallback: T) = apply {
            this.fallback = fallback
        }

        fun strictMode(strictMode: Boolean) = apply {
            this.strictMode = strictMode
        }

        fun build(): RelativeTime<T> {
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
