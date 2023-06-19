package ca.nihk.library

import java.util.TimeZone
import kotlin.time.Duration
import kotlin.time.DurationUnit
import kotlin.time.toDuration

class TimeRangeFormatter<T>(
    private val range: ClosedRange<Duration>,
    internal val format: (info: Info) -> T
) {
    internal operator fun contains(duration: Duration): Boolean {
        return duration in range
    }

    internal fun overlapsWith(other: TimeRangeFormatter<T>): Boolean {
        return range.start in other.range || range.endInclusive in other.range
    }

    data class Info(
        val delta: Duration,
        val currentTime: Duration,
        val timeZone: TimeZone
    )
}

val now = Duration.ZERO
val dawnOfTime = Long.MIN_VALUE.toDuration(DurationUnit.MILLISECONDS)
val endOfTime = Long.MAX_VALUE.toDuration(DurationUnit.MILLISECONDS)

infix fun Duration.inclusiveToInclusive(end: Duration): ClosedRange<Duration> {
    return this..end
}

infix fun Duration.inclusiveToExclusive(end: Duration): ClosedRange<Duration> {
    return this..(end - 1.toDuration(DurationUnit.MILLISECONDS))
}

infix fun Duration.exclusiveToInclusive(end: Duration): ClosedRange<Duration> {
    return (this + 1.toDuration(DurationUnit.MILLISECONDS))..end
}

infix fun Duration.exclusiveToExclusive(end: Duration): ClosedRange<Duration> {
    return (this + 1.toDuration(DurationUnit.MILLISECONDS))..(end - 1.toDuration(DurationUnit.MILLISECONDS))
}
