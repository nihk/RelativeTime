import org.junit.Assert
import org.junit.Test
import java.util.*
import kotlin.time.milliseconds

class RelativeTimeTest {

    val toronto: TimeZone = TimeZone.getTimeZone("America/Toronto")

    @Test
    fun `just over two years ago`() {
        validateRelativeTime(
            timeZone = toronto,
            timeRangeFormatters = Fakes.timeRangeFormatters,
            expected = "January 1, 2017",
            now = 1547528400000L,  // Tuesday, January 15, 2019 12:00:00 AM GMT-05:00
            date = 1483246800000L  // Sunday, January 1, 2017 12:00:00 AM GMT-05:00
        )
    }

    @Test
    fun `around six months ago`() {
        validateRelativeTime(
            timeZone = toronto,
            timeRangeFormatters = Fakes.timeRangeFormatters,
            expected = "June 15",
            now = 1547528400000L,  // Tuesday, January 15, 2019 12:00:00 AM GMT-05:00
            date = 1529035200000L  // Friday, June 15, 2018 12:00:00 AM GMT-04:00
        )
    }

    @Test
    fun `twelve hours ago`() {
        validateRelativeTime(
            timeZone = toronto,
            timeRangeFormatters = Fakes.timeRangeFormatters,
            expected = "January 14",
            now = 1547528400000L,  // Tuesday, January 15, 2019 12:00:00 AM GMT-05:00
            date = 1547485200000L  // Monday, January 14, 2019 12:00:00 PM GMT-05:00
        )
    }

    @Test
    fun `eleven hours, fifty-nine minutes, and fifty-nine seconds ago`() {
        validateRelativeTime(
            timeZone = toronto,
            timeRangeFormatters = Fakes.timeRangeFormatters,
            expected = "11 hours ago",
            now = 1547528400000L,  // Tuesday, January 15, 2019 12:00:00 AM GMT-05:00
            date = 1547485201000L  // Monday, January 14, 2019 12:00:01 PM GMT-05:00
        )
    }

    @Test
    fun `three hours ago`() {
        validateRelativeTime(
            timeZone = toronto,
            timeRangeFormatters = Fakes.timeRangeFormatters,
            expected = "3 hours ago",
            now = 1547528400000L,  // Tuesday, January 15, 2019 12:00:00 AM GMT-05:00
            date = 1547517600000L  // Monday, January 14, 2019 9:00:00 PM GMT-05:00
        )
    }

    @Test
    fun `one hour ago`() {
        validateRelativeTime(
            timeZone = toronto,
            timeRangeFormatters = Fakes.timeRangeFormatters,
            expected = "An hour ago",
            now = 1547528400000L,  // Tuesday, January 15, 2019 12:00:00 AM GMT-05:00
            date = 1547524800000L  // Monday, January 14, 2019 11:00:00 PM GMT-05:00
        )
    }

    @Test
    fun `thirty three minutes ago`() {
        validateRelativeTime(
            timeZone = toronto,
            timeRangeFormatters = Fakes.timeRangeFormatters,
            expected = "33 minutes ago",
            now = 1547528400000L,  // Tuesday, January 15, 2019 12:00:00 AM GMT-05:00
            date = 1547526420000L  // Monday, January 14, 2019 11:27:00 PM GMT-05:00
        )
    }

    @Test
    fun `one minute, thirty seconds ago`() {
        validateRelativeTime(
            timeZone = toronto,
            timeRangeFormatters = Fakes.timeRangeFormatters,
            expected = "A minute ago",
            now = 1547528400000L,  // Tuesday, January 15, 2019 12:00:00 AM GMT-05:00
            date = 1547528310000L  // Monday, January 14, 2019 11:58:30 PM GMT-05:00
        )
    }

    @Test
    fun `thirty seconds ago`() {
        validateRelativeTime(
            timeZone = toronto,
            timeRangeFormatters = Fakes.timeRangeFormatters,
            expected = "Seconds ago",
            now = 1547528400000L,  // Tuesday, January 15, 2019 12:00:00 AM GMT-05:00
            date = 1547528370000L  // Monday, January 14, 2019 11:59:30 PM GMT-05:00
        )
    }

    @Test
    fun `same time as now`() {
        validateRelativeTime(
            timeZone = toronto,
            timeRangeFormatters = Fakes.timeRangeFormatters,
            expected = "Now",
            now = 1547528400000L,  // Tuesday, January 15, 2019 12:00:00 AM GMT-05:00
            date = 1547528400000L  // Tuesday, January 15, 2019 12:00:00 AM GMT-05:00
        )
    }

    @Test
    fun `five seconds from now`() {
        validateRelativeTime(
            timeZone = toronto,
            timeRangeFormatters = Fakes.timeRangeFormatters,
            expected = "Seconds from now",
            now = 1547528400000L,  // Tuesday, January 15, 2019 12:00:00 AM GMT-05:00
            date = 1547528405000L  // Tuesday, January 15, 2019 12:00:05 AM GMT-05:00
        )
    }

    @Test
    fun `one minute, five seconds from now`() {
        validateRelativeTime(
            timeZone = toronto,
            timeRangeFormatters = Fakes.timeRangeFormatters,
            expected = "In one minute",
            now = 1547528400000L,  // Tuesday, January 15, 2019 12:00:00 AM GMT-05:00
            date = 1547528465000L  // Tuesday, January 15, 2019 12:01:05 AM GMT-05:00
        )
    }

    @Test
    fun `a half hour from now`() {
        validateRelativeTime(
            timeZone = toronto,
            timeRangeFormatters = Fakes.timeRangeFormatters,
            expected = "In 30 minutes",
            now = 1547528400000L,  // Tuesday, January 15, 2019 12:00:00 AM GMT-05:00
            date = 1547530200000L  // Tuesday, January 15, 2019 12:30:00 AM GMT-05:00
        )
    }

    @Test
    fun `ninety minutes from now`() {
        validateRelativeTime(
            timeZone = toronto,
            timeRangeFormatters = Fakes.timeRangeFormatters,
            expected = "In one hour",
            now = 1547528400000L,  // Tuesday, January 15, 2019 12:00:00 AM GMT-05:00
            date = 1547533800000L  // Tuesday, January 15, 2019 1:30:00 AM GMT-05:00
        )
    }

    @Test
    fun `seven hours from now`() {
        validateRelativeTime(
            timeZone = toronto,
            timeRangeFormatters = Fakes.timeRangeFormatters,
            expected = "In 7 hours",
            now = 1547528400000L,  // Tuesday, January 15, 2019 12:00:00 AM GMT-05:00
            date = 1547553600000L  // Tuesday, January 15, 2019 7:00:00 AM GMT-05:00
        )
    }

    @Test
    fun `seven months from now`() {
        validateRelativeTime(
            timeZone = toronto,
            timeRangeFormatters = Fakes.timeRangeFormatters,
            expected = "August 15",
            now = 1547528400000L,  // Tuesday, January 15, 2019 12:00:00 AM GMT-05:00
            date = 1565841600000L  // Thursday, August 15, 2019 12:00:00 AM GMT-04:00
        )
    }

    @Test
    fun `two years from now`() {
        validateRelativeTime(
            timeZone = toronto,
            timeRangeFormatters = Fakes.timeRangeFormatters,
            expected = "January 15, 2021",
            now = 1547528400000L,  // Tuesday, January 15, 2019 12:00:00 AM GMT-05:00
            date = 1610686800000L  // Friday, January 15, 2021 12:00:00 AM GMT-05:00
        )
    }

    @Test
    fun `toronto timezone looking at tokyo`() {
        validateRelativeTime(
            timeZone = toronto,
            timeRangeFormatters = Fakes.timeRangeFormatters,
            expected = "August 13",
            now = 1597150800000L,  // Tuesday, August 11, 2020 9:00:00 AM GMT-04:00
            date = 1597294800000L  // Thursday August 13, 2020 14:00:00 PM GMT+09:00
        )
    }

    @Test
    fun `vancouver timezone looking at tokyo`() {
        validateRelativeTime(
            timeZone = TimeZone.getTimeZone("America/Vancouver"),
            timeRangeFormatters = Fakes.timeRangeFormatters,
            expected = "August 12",
            now = 1597150800000L,  // Tuesday, August 11, 2020 6:00:00 AM GMT-07:00
            date = 1597294800000L  // Thursday August 13, 2020 14:00:00 PM GMT+09:00
        )
    }

    @Test
    fun `from string, five hours ago`() {
        validateRelativeTime(
            timeZone = toronto,
            timeRangeFormatters = Fakes.timeRangeFormatters,
            expected = "5 hours ago",
            now = 1547528400000L,  // Tuesday, January 15, 2019 12:00:00 AM GMT-05:00
            date = "1547510400000"  // Monday, January 14, 2019 7:00:00 PM GMT-05:00
        )
    }

    @Test
    fun `invalid string returns fallback`() {
        validateRelativeTime(
            timeZone = toronto,
            timeRangeFormatters = Fakes.timeRangeFormatters,
            expected = "fallback",
            fallback = "fallback",
            now = 1547528400000L,  // Tuesday, January 15, 2019 12:00:00 AM GMT-05:00
            date = "this is an invalid date"
        )
    }

    @Test
    fun `empty string returns fallback`() {
        validateRelativeTime(
            timeZone = toronto,
            timeRangeFormatters = Fakes.timeRangeFormatters,
            expected = "fallback",
            fallback = "fallback",
            now = 1547528400000L,  // Tuesday, January 15, 2019 12:00:00 AM GMT-05:00
            date = ""
        )
    }

    @Test
    fun `null string returns fallback`() {
        validateRelativeTime(
            timeZone = toronto,
            timeRangeFormatters = Fakes.timeRangeFormatters,
            expected = "fallback",
            fallback = "fallback",
            now = 1547528400000L,  // Tuesday, January 15, 2019 12:00:00 AM GMT-05:00
            date = null
        )
    }

    @Test
    fun `epoch start input`() {
        validateRelativeTime(
            timeZone = toronto,
            timeRangeFormatters = Fakes.timeRangeFormatters,
            expected = "December 31, 1969",
            now = 1547528400000L,  // Tuesday, January 15, 2019 12:00:00 AM GMT-05:00
            date = 0L  // Wednesday, December 31, 1969 7:00:00 PM GMT-05:00
        )
    }

    private fun validateRelativeTime(
        expected: String?,
        date: Long,
        now: Long,
        timeZone: TimeZone,
        timeRangeFormatters: List<TimeRangeFormatter>,
        fallback: String = ""
    ) {
        val relativeTime = createRelativeTime(now, timeZone, timeRangeFormatters, fallback)

        val result = relativeTime.from(date)

        Assert.assertEquals(expected, result)
    }

    private fun validateRelativeTime(
        expected: String?,
        date: String?,
        now: Long,
        timeZone: TimeZone,
        timeRangeFormatters: List<TimeRangeFormatter>,
        fallback: String = ""
    ) {
        val relativeTime = createRelativeTime(now, timeZone, timeRangeFormatters, fallback)

        val result = relativeTime.from(date)

        Assert.assertEquals(expected, result)
    }

    private fun createRelativeTime(
        now: Long,
        timeZone: TimeZone,
        timeRangeFormatters: List<TimeRangeFormatter>,
        fallback: String
    ): RelativeTime {
        return RelativeTime(
            timeRangeFormatters = timeRangeFormatters,
            timeZone = timeZone,
            currentTimeProvider = { now.milliseconds },
            fallback = fallback
        )
    }
}