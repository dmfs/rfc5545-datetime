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

package org.dmfs.rfc5545.calendarmetrics;

import org.dmfs.rfc5545.Weekday;
import org.junit.Test;

import static org.junit.Assert.assertEquals;


public class CalendarMetricsTest
{
    @Test
    public void testMonthAndDay()
    {
        CalendarMetrics tools = new GregorianCalendarMetrics(Weekday.MO, 4);
        for (int month = 0; month < 12; ++month)
        {
            for (int monthday = 1; monthday <= tools.getDaysPerPackedMonth(2000, month); ++monthday)
            {
                int mad = CalendarMetrics.monthAndDay(month, monthday);

                int mo = CalendarMetrics.packedMonth(mad);
                int dom = CalendarMetrics.dayOfMonth(mad);

                // all we're asking for is to get the values in return we've put in
                assertEquals(month, mo);
                assertEquals(monthday, dom);
            }
        }
    }

}
