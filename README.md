# RelativeTime
A library that lets you define the language of relative time, e.g. "5 hours ago" or "1 month from now" and so on.

## How to use:
Start with the DSL builder like so:

```
val relativeTime = relativeTime {
    // ...
}
```

Add any number of `timeRangeFormatter` builders in that block to set the kinds of output you want to use within an interval of time:

```
    // From now til 1 hour from now, this will print in a 'minutes from now' style.
    timeRangeFormatter(now exclusiveToInclusive 1.toDuration(DurationUnit.HOURS)) { info ->
        "${info.delta.inWholeMinutes.absoluteValue} minutes from now"
    }
```

There's also other builder functions like `timeZone` and `currentTimeProvider`.

Once that's done, any values passed to its `RelativeTime.from` function that fall within the range(s) of the `TimeRangeFormatter`(s) that it owns will output the language you've defined: 

```
val currentTime = System.currentTimeMillis().toDuration(DurationUnit.MILLISECONDS)
val fiveMinutes = 5.toDuration(DurationUnit.MILLISECONDS)
relativeTime.from(currentTime + fiveMinutes).let(::println)  // prints "5 minutes from now"
```

For more examples, see the sample gradle module in this project or the Fakes.kt file in `library/src/test`.

## Install:

Copy and paste! It's only two small files. This was originally published to JCenter, but that's gonezo.
