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

import org.junit.Test;

import static org.junit.Assert.assertEquals;


public class InstanceTest
{

    @Test
    public void testMakeIntIntIntIntIntIntInt()
    {
        long i = Instance.make(2000, 2, 20, 3, 45, 36, Weekday.MO.ordinal());
        assertEquals(2000, Instance.year(i));
        assertEquals(2, Instance.month(i));
        assertEquals(20, Instance.dayOfMonth(i));
        assertEquals(3, Instance.hour(i));
        assertEquals(45, Instance.minute(i));
        assertEquals(36, Instance.second(i));
        assertEquals(Weekday.MO.ordinal(), Instance.dayOfWeek(i));
    }


    @Test
    public void testMakeIntIntIntIntIntInt()
    {
        long i = Instance.make(2000, 2, 20, 3, 45, 36);
        assertEquals(2000, Instance.year(i));
        assertEquals(2, Instance.month(i));
        assertEquals(20, Instance.dayOfMonth(i));
        assertEquals(3, Instance.hour(i));
        assertEquals(45, Instance.minute(i));
        assertEquals(36, Instance.second(i));
        assertEquals(0, Instance.dayOfWeek(i));
    }


    @Test
    public void testSetYear()
    {
        for (int year = 0; year < 60000; ++year)
        {
            long i = Instance.setYear(Instance.make(0, 2, 20, 3, 45, 36), year);
            assertEquals(year, Instance.year(i));
            assertEquals(2, Instance.month(i));
            assertEquals(20, Instance.dayOfMonth(i));
            assertEquals(3, Instance.hour(i));
            assertEquals(45, Instance.minute(i));
            assertEquals(36, Instance.second(i));
            assertEquals(0, Instance.dayOfWeek(i));
        }
    }


    @Test
    public void testSetMonth()
    {
        for (int month = 0; month < 255; ++month)
        {
            long i = Instance.setMonth(Instance.make(2000, 0, 20, 3, 45, 36), month);
            assertEquals(2000, Instance.year(i));
            assertEquals(month, Instance.month(i));
            assertEquals(20, Instance.dayOfMonth(i));
            assertEquals(3, Instance.hour(i));
            assertEquals(45, Instance.minute(i));
            assertEquals(36, Instance.second(i));
            assertEquals(0, Instance.dayOfWeek(i));
        }
    }


    @Test
    public void testSetDayOfMonth()
    {
        for (int dayOfMonth = 1; dayOfMonth < 60; ++dayOfMonth)
        {
            long i = Instance.setDayOfMonth(Instance.make(2000, 2, 20, 3, 45, 36), dayOfMonth);
            assertEquals(2000, Instance.year(i));
            assertEquals(2, Instance.month(i));
            assertEquals(dayOfMonth, Instance.dayOfMonth(i));
            assertEquals(3, Instance.hour(i));
            assertEquals(45, Instance.minute(i));
            assertEquals(36, Instance.second(i));
            assertEquals(0, Instance.dayOfWeek(i));
        }
    }


    @Test
    public void testSetDayOfWeek()
    {
        for (int dayOfWeek = 0; dayOfWeek < 7; ++dayOfWeek)
        {
            long i = Instance.setDayOfWeek(Instance.make(2000, 2, 20, 3, 45, 36, 5), dayOfWeek);
            assertEquals(2000, Instance.year(i));
            assertEquals(2, Instance.month(i));
            assertEquals(20, Instance.dayOfMonth(i));
            assertEquals(3, Instance.hour(i));
            assertEquals(45, Instance.minute(i));
            assertEquals(36, Instance.second(i));
            assertEquals(dayOfWeek, Instance.dayOfWeek(i));
        }
    }


    @Test
    public void testSetMonthAndDayOfMonth()
    {
        for (int month = 0; month < 12; ++month)
        {
            for (int dayOfMonth = 1; dayOfMonth < 40; ++dayOfMonth)
            {
                long i = Instance.setMonthAndDayOfMonth(Instance.make(2000, 2, 20, 3, 45, 36), month, dayOfMonth);
                assertEquals(2000, Instance.year(i));
                assertEquals(month, Instance.month(i));
                assertEquals(dayOfMonth, Instance.dayOfMonth(i));
                assertEquals(3, Instance.hour(i));
                assertEquals(45, Instance.minute(i));
                assertEquals(36, Instance.second(i));
                assertEquals(0, Instance.dayOfWeek(i));
            }
        }
    }


    @Test
    public void testSetHour()
    {
        for (int hour = 0; hour < 24; ++hour)
        {
            long i = Instance.setHour(Instance.make(2000, 2, 20, 3, 45, 36), hour);
            assertEquals(2000, Instance.year(i));
            assertEquals(2, Instance.month(i));
            assertEquals(20, Instance.dayOfMonth(i));
            assertEquals(hour, Instance.hour(i));
            assertEquals(45, Instance.minute(i));
            assertEquals(36, Instance.second(i));
            assertEquals(0, Instance.dayOfWeek(i));
        }
    }


    @Test
    public void testSetMinute()
    {
        for (int minute = 0; minute < 60; ++minute)
        {
            long i = Instance.setMinute(Instance.make(2000, 2, 20, 3, 45, 36), minute);
            assertEquals(2000, Instance.year(i));
            assertEquals(2, Instance.month(i));
            assertEquals(20, Instance.dayOfMonth(i));
            assertEquals(3, Instance.hour(i));
            assertEquals(minute, Instance.minute(i));
            assertEquals(36, Instance.second(i));
            assertEquals(0, Instance.dayOfWeek(i));
        }
    }


    @Test
    public void testSetSecond()
    {
        for (int second = 0; second < 60; ++second)
        {
            long i = Instance.setSecond(Instance.make(2000, 2, 20, 3, 45, 36), second);
            assertEquals(2000, Instance.year(i));
            assertEquals(2, Instance.month(i));
            assertEquals(20, Instance.dayOfMonth(i));
            assertEquals(3, Instance.hour(i));
            assertEquals(45, Instance.minute(i));
            assertEquals(second, Instance.second(i));
            assertEquals(0, Instance.dayOfWeek(i));
        }
    }


    @Test
    public void testMaskNonrelevant()
    {
        for (int dayOfWeek = 0; dayOfWeek < 7; ++dayOfWeek)
        {
            long i = Instance.maskWeekday(Instance.make(2000, 2, 20, 3, 45, 36, dayOfWeek));
            assertEquals(2000, Instance.year(i));
            assertEquals(2, Instance.month(i));
            assertEquals(20, Instance.dayOfMonth(i));
            assertEquals(3, Instance.hour(i));
            assertEquals(45, Instance.minute(i));
            assertEquals(36, Instance.second(i));
            assertEquals(0, Instance.dayOfWeek(i));
        }
    }
}
