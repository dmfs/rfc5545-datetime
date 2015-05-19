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

import java.util.Arrays;
import java.util.TimeZone;

import org.junit.Test;


public class DateTimeTest
{

	private final static String[] TESTDATES = { "19000101T000000", "19700101T000000", "20140330T010000", "20140414T132231" };


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

					DateTime modifiedDate = new DateTime(originalDate);
					modifiedDate.getTimestamp();
					modifiedDate.swapTimeZone(toZone);

					DateTime expectedDate = DateTime.parse(toZone, testdate);

					assertEquals(expectedDate.getTimestamp(), modifiedDate.getTimestamp());
					assertEquals(originalDate.getInstance(), modifiedDate.getInstance());
				}
			}
		}
	}
}
