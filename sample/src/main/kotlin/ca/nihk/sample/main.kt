package ca.nihk.sample

import ca.nihk.library.*
import kotlin.math.absoluteValue
import kotlin.time.DurationUnit
import kotlin.time.toDuration

fun main() {
    val currentTimeProvider = { System.currentTimeMillis().toDuration(DurationUnit.MILLISECONDS) }

    val relativeTime = relativeTime {
        currentTimeProvider(currentTimeProvider)

        timeRangeFormatter((-1).toDuration(DurationUnit.HOURS) exclusiveToExclusive now) { info ->
            "${info.delta.inWholeMinutes.absoluteValue} minutes ago"
        }
        timeRangeFormatter(now inclusiveToInclusive now) { info ->
            "Now! And you're in ${info.timeZone.displayName}"
        }
        timeRangeFormatter(now exclusiveToInclusive 1.toDuration(DurationUnit.HOURS)) { info ->
            "Epoch: ${(info.currentTime + info.delta).inWholeMilliseconds}"
        }
    }

    relativeTime.from(currentTimeProvider() - 5.toDuration(DurationUnit.MINUTES)).let(::println) // 5 minutes ago
    relativeTime.from(currentTimeProvider()).let(::println) // Now! And you're in Eastern Standard Time
    relativeTime.from(currentTimeProvider() + 5.toDuration(DurationUnit.MINUTES)).let(::println) // Epoch: 1637882818518
}
