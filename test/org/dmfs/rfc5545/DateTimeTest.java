/*
 * Copyright (C) 2015 Marten Gajda <marten@dmfs.org>
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * 
 */

package org.dmfs.rfc5545;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Locale;
import java.util.TimeZone;

import org.dmfs.rfc5545.calendarmetrics.CalendarMetrics;
import org.dmfs.rfc5545.calendarmetrics.GregorianCalendarMetrics;
import org.dmfs.rfc5545.calendarmetrics.IslamicCalendarMetrics;
import org.junit.Test;


public class DateTimeTest
{

	private final static String[] TESTDATES = { "19000101T000000", "19700101T000000", "20140330T010000", "20140414T132231" };


	@Test
	public void testAfter()
	{
		assertTrue(DateTime.parse("19700101").after(DateTime.parse("19691231")));
		assertTrue(DateTime.parse("19700101T000000Z").after(new DateTime(-1)));
		assertTrue(new DateTime(0).after(new DateTime(-1)));
		assertFalse(DateTime.parse("19700101").after(DateTime.parse("19700102")));
		assertFalse(DateTime.parse("19691231").after(DateTime.parse("19700101")));
		assertFalse(DateTime.parse("19700101T000000Z").after(new DateTime(0)));
		assertFalse(new DateTime(-1).after(DateTime.parse("19700101T000000Z")));
		assertFalse(new DateTime(-1).after(new DateTime(0)));

		assertFalse(DateTime.parse(TimeZone.getTimeZone("Europe/Berlin"), "19700101T000000").after(
			DateTime.parse(TimeZone.getTimeZone("UTC"), "19700101T000000")));
		assertFalse(DateTime.parse(TimeZone.getTimeZone("Europe/Berlin"), "19700101T000000").after(new DateTime(0)));
		assertFalse(DateTime.parse(TimeZone.getTimeZone("Europe/Berlin"), "19700101T000000").after(
			DateTime.parse(TimeZone.getTimeZone("Europe/Amsterdam"), "19700101T000000")));
		assertFalse(DateTime.parse(TimeZone.getTimeZone("Europe/Berlin"), "19700101T000000").after(new DateTime(TimeZone.getTimeZone("Europe/Amsterdam"), 0)));

		assertTrue(DateTime.parse(TimeZone.getTimeZone("UTC"), "19700101T000000").after(
			DateTime.parse(TimeZone.getTimeZone("Europe/Berlin"), "19700101T000000")));
		assertTrue(new DateTime(0).after(DateTime.parse(TimeZone.getTimeZone("Europe/Berlin"), "19700101T000000")));
		assertFalse(DateTime.parse(TimeZone.getTimeZone("Europe/Amsterdam"), "19700101T000000").after(
			DateTime.parse(TimeZone.getTimeZone("Europe/Berlin"), "19700101T000000")));
		assertTrue(new DateTime(TimeZone.getTimeZone("Europe/Amsterdam"), 0).after(DateTime.parse(TimeZone.getTimeZone("Europe/Berlin"), "19700101T000000")));
	}


	@Test
	public void testBefore()
	{
		assertFalse(DateTime.parse("19700101").before(DateTime.parse("19691231")));
		assertFalse(DateTime.parse("19700101T000000Z").before(new DateTime(-1)));
		assertFalse(new DateTime(0).before(new DateTime(-1)));
		assertTrue(DateTime.parse("19700101").before(DateTime.parse("19700102")));
		assertTrue(DateTime.parse("19691231").before(DateTime.parse("19700101")));
		assertFalse(DateTime.parse("19700101T000000Z").before(new DateTime(0)));
		assertTrue(new DateTime(-1).before(DateTime.parse("19700101T000000Z")));
		assertTrue(new DateTime(-1).before(new DateTime(0)));

		assertTrue(DateTime.parse(TimeZone.getTimeZone("Europe/Berlin"), "19700101T000000").before(
			DateTime.parse(TimeZone.getTimeZone("UTC"), "19700101T000000")));
		assertTrue(DateTime.parse(TimeZone.getTimeZone("Europe/Berlin"), "19700101T000000").before(new DateTime(0)));
		assertFalse(DateTime.parse(TimeZone.getTimeZone("Europe/Berlin"), "19700101T000000").before(
			DateTime.parse(TimeZone.getTimeZone("Europe/Amsterdam"), "19700101T000000")));
		assertTrue(DateTime.parse(TimeZone.getTimeZone("Europe/Berlin"), "19700101T000000").before(new DateTime(TimeZone.getTimeZone("Europe/Amsterdam"), 0)));

		assertFalse(DateTime.parse(TimeZone.getTimeZone("UTC"), "19700101T000000").before(
			DateTime.parse(TimeZone.getTimeZone("Europe/Berlin"), "19700101T000000")));
		assertFalse(new DateTime(0).before(DateTime.parse(TimeZone.getTimeZone("Europe/Berlin"), "19700101T000000")));
		assertFalse(DateTime.parse(TimeZone.getTimeZone("Europe/Amsterdam"), "19700101T000000").before(
			DateTime.parse(TimeZone.getTimeZone("Europe/Berlin"), "19700101T000000")));
		assertFalse(new DateTime(TimeZone.getTimeZone("Europe/Amsterdam"), 0).before(DateTime.parse(TimeZone.getTimeZone("Europe/Berlin"), "19700101T000000")));
	}


	@Test
	public void testDateTimeCalendarMetricsDateTime()
	{
		CalendarMetrics gregorian = new GregorianCalendarMetrics(Weekday.MO, 4);
		CalendarMetrics islamic = new IslamicCalendarMetrics(Weekday.MO, 4, IslamicCalendarMetrics.LeapYearPattern.I, true);

		DateTime allday = DateTime.parse(gregorian, null, "20151212");
		DateTime floating = DateTime.parse(gregorian, null, "20151212T130000");
		DateTime absolute = DateTime.parse(gregorian, TimeZone.getTimeZone("Europe/Berlin"), "20151212T130000");

		DateTime alldayI = new DateTime(islamic, allday);
		DateTime floatingI = new DateTime(islamic, floating);
		DateTime absoluteI = new DateTime(islamic, absolute);

		assertEquals(allday.getTimestamp(), alldayI.getTimestamp());
		assertEquals(allday.isAllDay(), alldayI.isAllDay());
		assertEquals(allday.isFloating(), alldayI.isFloating());
		assertEquals(allday.getDayOfWeek(), alldayI.getDayOfWeek());
		assertNull(alldayI.getTimeZone());
		assertEquals(islamic, alldayI.getCalendarMetrics());

		assertEquals(floating.getTimestamp(), floatingI.getTimestamp());
		assertEquals(floating.isAllDay(), floatingI.isAllDay());
		assertEquals(floating.isFloating(), floatingI.isFloating());
		assertEquals(floating.getDayOfWeek(), floatingI.getDayOfWeek());
		assertNull(floatingI.getTimeZone());
		assertEquals(islamic, floatingI.getCalendarMetrics());

		assertEquals(absolute.getTimestamp(), absoluteI.getTimestamp());
		assertEquals(absolute.isAllDay(), absoluteI.isAllDay());
		assertEquals(absolute.isFloating(), absoluteI.isFloating());
		assertEquals(absolute.getDayOfWeek(), absoluteI.getDayOfWeek());
		assertEquals(TimeZone.getTimeZone("Europe/Berlin"), absoluteI.getTimeZone());
		assertEquals(islamic, absoluteI.getCalendarMetrics());
	}


	@Test
	public void testDateTimeCalendarMetricsTimeZoneDateTime()
	{
		CalendarMetrics gregorian = new GregorianCalendarMetrics(Weekday.MO, 4);
		CalendarMetrics islamic = new IslamicCalendarMetrics(Weekday.MO, 4, IslamicCalendarMetrics.LeapYearPattern.I, true);

		DateTime allday = DateTime.parse(gregorian, null, "20151212");
		DateTime floating = DateTime.parse(gregorian, null, "20151212T130000");
		DateTime absolute = DateTime.parse(gregorian, TimeZone.getTimeZone("Europe/Berlin"), "20151212T130000");

		TimeZone tz = TimeZone.getTimeZone("Asia/Tokyo");

		DateTime alldayI = new DateTime(islamic, tz, allday);
		DateTime floatingI = new DateTime(islamic, tz, floating);
		DateTime absoluteI = new DateTime(islamic, tz, absolute);

		assertEquals(allday.getTimestamp(), alldayI.getTimestamp());
		assertEquals(allday.isAllDay(), alldayI.isAllDay());
		assertEquals(allday.isFloating(), alldayI.isFloating());
		assertEquals(allday.getDayOfWeek(), alldayI.getDayOfWeek());
		assertNull(alldayI.getTimeZone());
		assertEquals(islamic, alldayI.getCalendarMetrics());

		assertEquals(floating.getTimestamp(), floatingI.getTimestamp());
		assertEquals(floating.isAllDay(), floatingI.isAllDay());
		assertFalse(floatingI.isFloating());
		assertEquals(floating.getDayOfWeek(), floatingI.getDayOfWeek());
		assertEquals(TimeZone.getTimeZone("Asia/Tokyo"), floatingI.getTimeZone());
		assertEquals(islamic, floatingI.getCalendarMetrics());

		assertEquals(absolute.getTimestamp(), absoluteI.getTimestamp());
		assertEquals(absolute.isAllDay(), absoluteI.isAllDay());
		assertEquals(absolute.isFloating(), absoluteI.isFloating());
		assertEquals(absolute.getDayOfWeek(), absoluteI.getDayOfWeek());
		assertEquals(TimeZone.getTimeZone("Asia/Tokyo"), absoluteI.getTimeZone());
		assertEquals(islamic, absoluteI.getCalendarMetrics());
	}


	@Test
	public void testSwapTimeZone()
	{
		String[] timeZones = TimeZone.getAvailableIDs();
		// append null to the zones
		timeZones = Arrays.copyOf(timeZones, timeZones.length + 1);

		// test the conversion between all timezones
		for (String testdate : TESTDATES)
		{
			for (String fromTimezone : timeZones)
			{
				for (String toTimezone : timeZones)
				{
					TimeZone fromZone = fromTimezone == null ? null : TimeZone.getTimeZone(fromTimezone);
					TimeZone toZone = toTimezone == null ? null : TimeZone.getTimeZone(toTimezone);

					DateTime originalDate = DateTime.parse(fromZone, testdate);

					DateTime modifiedDate = originalDate.swapTimeZone(toZone);

					DateTime expectedDate = DateTime.parse(toZone, testdate);

					// the absolute time of the modified instance should equal the one in the new time zone
					assertEquals(expectedDate.getTimestamp(), modifiedDate.getTimestamp());

					// the local time should equal the original local time
					assertEquals(originalDate.getInstance(), modifiedDate.getInstance());

					// the time zone should be correct of course
					assertEquals(toZone, modifiedDate.getTimeZone());
				}
			}
		}
	}


	@Test
	public void testShiftTimeZone()
	{
		String[] timeZones = TimeZone.getAvailableIDs();
		// append null to the zones
		timeZones = Arrays.copyOf(timeZones, timeZones.length + 1);

		// test the conversion between all timezones
		for (String testdate : TESTDATES)
		{
			for (String fromTimezone : timeZones)
			{
				for (String toTimezone : timeZones)
				{
					TimeZone fromZone = fromTimezone == null ? null : TimeZone.getTimeZone(fromTimezone);
					TimeZone toZone = toTimezone == null ? null : TimeZone.getTimeZone(toTimezone);

					DateTime originalDate = DateTime.parse(fromZone, testdate);

					DateTime modifiedDate = originalDate.shiftTimeZone(toZone);

					// DateTime expectedDate = DateTime.parse(toZone, testdate);

					// the absolute time of the modified instance should equal the one in the new time zone
					assertEquals(originalDate.getTimestamp(), modifiedDate.getTimestamp());

					// the time zone should be correct of course
					assertEquals(toZone, modifiedDate.getTimeZone());
					// the local time should equal the original local time
					// assertEquals(originalDate.getInstance(), modifiedDate.getInstance());
				}
			}
		}
	}


	@Test
	public void testAddDuration()
	{
		assertEquals(DateTime.parse("20000101"), DateTime.parse("20000101").addDuration(Duration.parse("P0D")));
		assertEquals(DateTime.parse("20040228"), DateTime.parse("20040228").addDuration(Duration.parse("P0D")));
		assertEquals(DateTime.parse("20030228"), DateTime.parse("20030228").addDuration(Duration.parse("P0D")));

		assertEquals(DateTime.parse("20000102"), DateTime.parse("20000101").addDuration(Duration.parse("P1D")));
		assertEquals(DateTime.parse("20040229"), DateTime.parse("20040228").addDuration(Duration.parse("P1D")));
		assertEquals(DateTime.parse("20030301"), DateTime.parse("20030228").addDuration(Duration.parse("P1D")));

		assertEquals(DateTime.parse("20000115"), DateTime.parse("20000101").addDuration(Duration.parse("P2W")));
		assertEquals(DateTime.parse("20040313"), DateTime.parse("20040228").addDuration(Duration.parse("P2W")));
		assertEquals(DateTime.parse("20030314"), DateTime.parse("20030228").addDuration(Duration.parse("P2W")));

		assertEquals(DateTime.parse("20000102T120000"), DateTime.parse("20000101T120000").addDuration(Duration.parse("P1D")));
		assertEquals(DateTime.parse("20040229T120000"), DateTime.parse("20040228T120000").addDuration(Duration.parse("P1D")));
		assertEquals(DateTime.parse("20030301T120000"), DateTime.parse("20030228T120000").addDuration(Duration.parse("P1D")));

		assertEquals(DateTime.parse("UTC", "20150329T120000"), DateTime.parse("UTC", "20150328T120000").addDuration(Duration.parse("PT24H")));
		assertEquals(DateTime.parse("Europe/Berlin", "20150329T130000"), DateTime.parse("Europe/Berlin", "20150328T120000")
			.addDuration(Duration.parse("PT24H")));

		assertEquals(DateTime.parse("UTC", "20150329T120000"), DateTime.parse("UTC", "20150326T120000").addDuration(Duration.parse("P2DT24H")));
		assertEquals(DateTime.parse("Europe/Berlin", "20150329T130000"),
			DateTime.parse("Europe/Berlin", "20150326T120000").addDuration(Duration.parse("P2DT24H")));

		assertEquals(DateTime.parse("19991231"), DateTime.parse("20000101").addDuration(Duration.parse("-P1D")));
		assertEquals(DateTime.parse("20040229"), DateTime.parse("20040301").addDuration(Duration.parse("-P1D")));
		assertEquals(DateTime.parse("20030228"), DateTime.parse("20030301").addDuration(Duration.parse("-P1D")));

		assertEquals(DateTime.parse("19991218"), DateTime.parse("20000101").addDuration(Duration.parse("-P2W")));
		assertEquals(DateTime.parse("20040216"), DateTime.parse("20040301").addDuration(Duration.parse("-P2W")));
		assertEquals(DateTime.parse("20030215"), DateTime.parse("20030301").addDuration(Duration.parse("-P2W")));

		assertEquals(DateTime.parse("19991231T120000"), DateTime.parse("20000101T120000").addDuration(Duration.parse("-P1D")));
		assertEquals(DateTime.parse("20040229T120000"), DateTime.parse("20040301T120000").addDuration(Duration.parse("-P1D")));
		assertEquals(DateTime.parse("20030228T120000"), DateTime.parse("20030301T120000").addDuration(Duration.parse("-P1D")));

		assertEquals(DateTime.parse("UTC", "20150328T120000"), DateTime.parse("UTC", "20150329T120000").addDuration(Duration.parse("-PT24H")));
		assertEquals(DateTime.parse("Europe/Berlin", "20150328T110000"),
			DateTime.parse("Europe/Berlin", "20150329T120000").addDuration(Duration.parse("-PT24H")));

		assertEquals(DateTime.parse("UTC", "20150326T120000"), DateTime.parse("UTC", "20150329T120000").addDuration(Duration.parse("-P2DT24H")));
		assertEquals(DateTime.parse("Europe/Berlin", "20150328T110000"),
			DateTime.parse("Europe/Berlin", "20150331T120000").addDuration(Duration.parse("-P2DT24H")));

	}


	@Test
	public void testDateTimeIntIntInt()
	{
		CalendarMetrics tools = new GregorianCalendarMetrics(Weekday.MO, 4);

		for (int year = 1600; year < 3000; ++year)
		{
			for (int month = 0; month < tools.getMonthsPerYear(year); ++month)
			{
				for (int day = 1; day <= tools.getDaysPerPackedMonth(year, month); ++day)
				{

					DateTime dt = new DateTime(year, month, day);
					assertEquals(year, dt.getYear());
					assertEquals(month, dt.getMonth());
					assertEquals(day, dt.getDayOfMonth());
				}
			}
		}
	}


	@Test
	public void testGetTimestamp()
	{
		for (String z : new String[] { "America/Bogota", "America/Los_Angeles", "America/New_York", "UTC", "GMT", "Europe/Berlin", "Asia/Peking", "Asia/Tokyo",
			"Australia/Sidney" })
		{
			TimeZone zone = TimeZone.getTimeZone(z);
			java.util.Calendar testCal = new GregorianCalendar(zone, Locale.US);
			testCal.setMinimalDaysInFirstWeek(4);
			testCal.setFirstDayOfWeek(Calendar.MONDAY);
			testCal.setTimeInMillis(0);

			CalendarMetrics tools = new GregorianCalendarMetrics(Weekday.MO, 4);

			for (int year = 1600; year < 3000; ++year)
			{
				for (int month = 0; month < tools.getMonthsPerYear(year); ++month)
				{
					for (int day = 1; day <= tools.getDaysPerPackedMonth(year, month); ++day)
					{
						for (int hour = 0; hour < 24; ++hour)
						{
							for (int minute = 0; minute < 60; ++minute)
							{
								testCal.set(year, month, day, hour, minute, 0);

								long timestamp = new DateTime(tools, zone, year, month, day, hour, minute, 0).getTimestamp();
								assertEquals(testCal.getTimeInMillis(), timestamp);
							}
						}
					}
				}
			}
		}
	}

}
