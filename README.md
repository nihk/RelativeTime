[ ![Download](https://api.bintray.com/packages/nickjrose/relativetime/relativetime/images/download.svg?version=0.0.3) ](https://bintray.com/nickjrose/relativetime/relativetime/0.0.3/link)

# RelativeTime
A library that lets you define the language of relative time, e.g. "5 hours ago" or "1 month from now" and so on.

Note: this library uses `kotlin.time.Duration` which is, at the time of writing, an experimental API requiring Kotlin 1.3.50 and above.

## Install:

With Gradle (make sure you have `jcenter()` set as a repository):

```implementation "ca.nihk:relativetime:0.0.3"```

With Maven:

```
<dependency>
	<groupId>ca.nihk</groupId>
	<artifactId>relativetime</artifactId>
	<version>0.0.3</version>
	<type>pom</type>
</dependency>
```

## How to use:

In code set the language you want to use depending on how far away a given value is from current time. Do this using instances of the `TimeRangeFormatter` class, like so:

```
// Deltas between now (inclusive) and an hour from now (exclusive) will use the language "X minutes from now"
val minutesFromNow = TimeRangeFormatter(now inclusiveToExclusive 1.hours) { delta, _, _ ->
    "${delta.inMinutes.toInt()} minutes from now"
}
```
 
Finally, plug that into an instance of `RelativeTime`:

```
val relativeTime = RelativeTime(timeRangeFormatters = listOf(minutesFromNow))
```

Now any values given to its `RelativeTime#from` function that fall within the range(s) of the `TimeRangeFormatter`(s) that it owns will output the language you've defined: 

```
println(relativeTime.from(System.currentTimeMillis().milliseconds + 5.minutes))  // prints "5 minutes from now"
```

Optionally you can instantiate `RelativeTime` without any arguments and it will use a set of default values.
