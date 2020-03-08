[ ![Download](https://api.bintray.com/packages/nickjrose/relativetime/relativetime/images/download.svg?version=0.0.1) ](https://bintray.com/nickjrose/relativetime/relativetime/0.0.1/link)

# RelativeTime
A library that lets you get really customized and specific with relative time, e.g. "5 hours ago" or "1 month from now" - sky's the limit. 

## How to use:
First add it as a gradle dependency:

```implementation "ca.nihk:relativetime:0.0.1"```

Then in code simply define the min/max range that any delta from a current timestamp can fall into, how to map that value to a `String`, and plug it into an instance of `RelativeTime`

For example, if you wanted a `Long` timestamp that falls between 2 minutes and 1 hour in the past to map to a `String` like  "5 molasses-like minutes ago", create a `TimeRangeFormatter` like so:

```
val minutesAgo = TimeRangeFormatter(
    format = { delta, _, _ ->
        "${TimeUnit.MILLISECONDS.toMinutes(delta).absoluteValue} molasses-like minutes ago"
    },
    min = -TimeUnit.HOURS.toMillis(1L),
    max = -TimeUnit.MINUTES.toMillis(2L),
    inclusiveMin = false,
    inclusiveMax = true
)
```

Then provide that to a new `RelativeTime` instance:

```
val relativeTime = RelativeTime(timeRangeFormatters = listOf(minutesAgo))
```

Any `Long` values given to its `RelativeTime.from(Long)` function that fall within the range of `minutesAgo` away from the current time will output the "${minutes} molasses-like minutes ago" `String`. 

In the above example I only provided one small range of time to the `RelativeTime` instance - you can partition the entire gamut of `Long.MIN_VALUE` to `Long.MAX_VALUE` however you choose to. Just populate a list of `TimeRangerFormatter`s and pass them all to a single new instance of `RelativeTime`.

The `RelativeTime` constructor also has default values that can be overwritten for further customization and testing.

See the [test directory](https://github.com/nihk/RelativeTime/tree/master/src/test/kotlin) for a bunch of other examples.
