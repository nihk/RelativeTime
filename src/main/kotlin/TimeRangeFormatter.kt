import java.util.*
import kotlin.time.Duration
import kotlin.time.milliseconds

class TimeRangeFormatter(
    internal val range: ClosedRange<Duration>,
    internal val format: (delta: Duration, time: Duration, timeZone: TimeZone) -> String
) {

    fun contains(duration: Duration): Boolean {
        return duration in range
    }
}

val now = Duration.ZERO
val dawnOfTime = Long.MIN_VALUE.milliseconds
val endOfTime = Long.MAX_VALUE.milliseconds

infix fun Duration.inclusiveToInclusive(end: Duration): ClosedRange<Duration> {
    return this..end
}

infix fun Duration.inclusiveToExclusive(end: Duration): ClosedRange<Duration> {
    return this..(end - 1.milliseconds)
}

infix fun Duration.exclusiveToInclusive(end: Duration): ClosedRange<Duration> {
    return (this + 1.milliseconds)..end
}

infix fun Duration.exclusiveToExclusive(end: Duration): ClosedRange<Duration> {
    return (this + 1.milliseconds)..(end - 1.milliseconds)
}