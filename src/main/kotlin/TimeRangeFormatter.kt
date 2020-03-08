import java.util.*

class TimeRangeFormatter(
    val format: (delta: Long, timeInMillis: Long, timeZone: TimeZone) -> String,
    private val min: Long,
    private val max: Long,
    private val inclusiveMin: Boolean,
    private val inclusiveMax: Boolean
) {

    fun contains(value: Long): Boolean {
        return (min == value && inclusiveMin || min < value)
                && (value < max || value == max && inclusiveMax)
    }
}