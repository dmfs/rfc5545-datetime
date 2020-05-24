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

import org.dmfs.rfc5545.Instance;
import org.dmfs.rfc5545.Weekday;
import org.junit.Before;
import org.junit.Test;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Locale;
import java.util.TimeZone;

import static org.junit.Assert.assertEquals;


/**
 * Test {@link GregorianCalendarMetrics}.
 * <p>
 * TODO: complete tests
 *
 * @author Marten Gajda <marten@dmfs.org>
 */
public class GregorianCalendarMetricsTest
{

    public static final int MAX_YEAR = 2100;
    public static final int MIN_YEAR = 1900;


    @Before
    public void setUp() throws Exception
    {
    }


    @Test
    public void testGetMaxMonthDays()
    {
        for (int minDaysInFirstWeek = 1; minDaysInFirstWeek < 8; ++minDaysInFirstWeek)
        {
            for (int weekStart = 0; weekStart < 7; ++weekStart)
            {
                CalendarMetrics tools = new GregorianCalendarMetrics(Weekday.values()[weekStart], minDaysInFirstWeek);
                assertEquals(31, tools.getMaxMonthDayNum());
            }
        }
    }


    @Test
    public void testGetMaxYearDays()
    {
        for (int minDaysInFirstWeek = 1; minDaysInFirstWeek < 8; ++minDaysInFirstWeek)
        {
            for (int weekStart = 0; weekStart < 7; ++weekStart)
            {
                CalendarMetrics tools = new GregorianCalendarMetrics(Weekday.values()[weekStart], minDaysInFirstWeek);
                assertEquals(366, tools.getMaxYearDayNum());
            }
        }
    }


    @Test
    public void testGetMaxWeeks()
    {
        for (int minDaysInFirstWeek = 1; minDaysInFirstWeek < 8; ++minDaysInFirstWeek)
        {
            for (int weekStart = 0; weekStart < 7; ++weekStart)
            {
                CalendarMetrics tools = new GregorianCalendarMetrics(Weekday.values()[weekStart], minDaysInFirstWeek);
                assertEquals(53, tools.getMaxWeekNoNum());
            }
        }
    }


    @Test
    public void testGetDaysPerMonth()
    {
        java.util.Calendar testCal = new GregorianCalendar(TimeZone.getTimeZone("UTC"));
        for (int minDaysInFirstWeek = 1; minDaysInFirstWeek < 8; ++minDaysInFirstWeek)
        {
            testCal.setMinimalDaysInFirstWeek(minDaysInFirstWeek);
            for (int weekStart = 0; weekStart < 7; ++weekStart)
            {
                CalendarMetrics tools = new GregorianCalendarMetrics(Weekday.values()[weekStart], minDaysInFirstWeek);
                testCal.setFirstDayOfWeek(weekStart + 1);
                for (int year = MIN_YEAR; year < MAX_YEAR; ++year)
                {
                    for (int month = 0; month < 12; ++month)
                    {
                        testCal.set(year, month, 1);

                        String errMsg = "";
                        // errMsg = "failed for year " + year + " weekstart " + weekStart + " minDays " + minDaysInFirstWeek;
                        assertEquals(errMsg, testCal.getActualMaximum(java.util.Calendar.DAY_OF_MONTH), tools.getDaysPerPackedMonth(year, month));
                    }
                }
            }
        }
    }


    @Test
    public void testGetMonthsPerYear()
    {
        java.util.Calendar testCal = new GregorianCalendar(TimeZone.getTimeZone("UTC"));
        for (int minDaysInFirstWeek = 1; minDaysInFirstWeek < 8; ++minDaysInFirstWeek)
        {
            testCal.setMinimalDaysInFirstWeek(minDaysInFirstWeek);
            for (int weekStart = 0; weekStart < 7; ++weekStart)
            {
                CalendarMetrics tools = new GregorianCalendarMetrics(Weekday.values()[weekStart], minDaysInFirstWeek);
                testCal.setFirstDayOfWeek(weekStart + 1);
                for (int year = MIN_YEAR; year < MAX_YEAR; ++year)
                {
                    // set testCal to Feb 1st
                    testCal.set(year, 1, 1);

                    String errMsg = "";
                    // errMsg = "failed for year " + year + " weekstart " + weekStart + " minDays " + minDaysInFirstWeek;

                    assertEquals(errMsg, testCal.getActualMaximum(java.util.Calendar.MONTH), tools.getMonthsPerYear(year) - 1);
                }
            }
        }
    }


    @Test
    public void testGetDaysPerYear()
    {
        java.util.Calendar testCal = new GregorianCalendar(TimeZone.getTimeZone("UTC"));
        for (int minDaysInFirstWeek = 1; minDaysInFirstWeek < 8; ++minDaysInFirstWeek)
        {
            testCal.setMinimalDaysInFirstWeek(minDaysInFirstWeek);
            for (int weekStart = 0; weekStart < 7; ++weekStart)
            {
                CalendarMetrics tools = new GregorianCalendarMetrics(Weekday.values()[weekStart], minDaysInFirstWeek);
                testCal.setFirstDayOfWeek(weekStart + 1);
                for (int year = MIN_YEAR; year < MAX_YEAR; ++year)
                {
                    // set testCal to Feb 1st
                    testCal.set(year, 1, 1);

                    assertEquals("failed for year " + year + " weekstart " + weekStart + " minDays " + minDaysInFirstWeek,
                            testCal.getActualMaximum(java.util.Calendar.DAY_OF_YEAR), tools.getDaysPerYear(year));
                }
            }
        }
    }


    @Test
    public void testGetWeeksPerYear()
    {
        java.util.Calendar testCal = new GregorianCalendar(TimeZone.getTimeZone("UTC"));
        for (int minDaysInFirstWeek = 1; minDaysInFirstWeek < 8; ++minDaysInFirstWeek)
        {
            testCal.setMinimalDaysInFirstWeek(minDaysInFirstWeek);
            for (int weekStart = 0; weekStart < 7; ++weekStart)
            {
                CalendarMetrics tools = new GregorianCalendarMetrics(Weekday.values()[weekStart], minDaysInFirstWeek);
                testCal.setFirstDayOfWeek(weekStart + 1);
                for (int year = MIN_YEAR; year < MAX_YEAR; ++year)
                {
                    // set testCal to Feb 1st
                    testCal.set(year, 1, 1);

                    assertEquals("failed for year " + year + " weekstart " + weekStart + " minDays " + minDaysInFirstWeek,
                            testCal.getActualMaximum(java.util.Calendar.WEEK_OF_YEAR), tools.getWeeksPerYear(year));
                }
            }
        }
    }


    /**
     * Test getWeekOfYear for all days between 1700 and 3000.
     */
    @Test
    public void testGetWeekOfYear()
    {
        java.util.Calendar testCal = new GregorianCalendar(TimeZone.getTimeZone("UTC"), Locale.US);
        for (int minDaysInFirstWeek = 1; minDaysInFirstWeek < 8; ++minDaysInFirstWeek)
        {
            testCal.setMinimalDaysInFirstWeek(minDaysInFirstWeek);
            for (int weekStart = 0; weekStart < 7; ++weekStart)
            {
                CalendarMetrics tools = new GregorianCalendarMetrics(Weekday.values()[weekStart], minDaysInFirstWeek);
                testCal.setFirstDayOfWeek(weekStart + 1);
                for (int year = MIN_YEAR; year < MAX_YEAR; ++year)
                {
                    testCal.set(year, 0, 1);
                    for (int yearday = 1; yearday <= tools.getDaysPerYear(year); ++yearday)
                    {
                        testCal.set(java.util.Calendar.DAY_OF_YEAR, yearday);

                        String errMsg = "";
                        // errMsg = "failed for year " + year + " yearday " + yearday + " weekstart " + weekStart + " minDays " + minDaysInFirstWeek;
                        assertEquals(errMsg, testCal.get(java.util.Calendar.WEEK_OF_YEAR), tools.getWeekOfYear(year, yearday));
                    }
                }
            }
        }
    }


    /**
     * Test getWeekOfYear for all days between 1700 and 3000.
     */
    @Test
    public void testGetWeekOfYearIntInt()
    {
        java.util.Calendar testCal = new GregorianCalendar(TimeZone.getTimeZone("UTC"), Locale.US);
        for (int minDaysInFirstWeek = 1; minDaysInFirstWeek < 8; ++minDaysInFirstWeek)
        {
            testCal.setMinimalDaysInFirstWeek(minDaysInFirstWeek);
            for (int weekStart = 0; weekStart < 7; ++weekStart)
            {
                CalendarMetrics tools = new GregorianCalendarMetrics(Weekday.values()[weekStart], minDaysInFirstWeek);
                testCal.setFirstDayOfWeek(weekStart + 1);
                for (int year = MIN_YEAR; year < MAX_YEAR; ++year)
                {
                    for (int month = 0; month < tools.getMonthsPerYear(year); ++month)
                    {
                        for (int day = 1; day <= tools.getDaysPerPackedMonth(year, month); ++day)
                        {
                            testCal.set(year, month, day);

                            String errMsg = "";
                            // errMsg = "failed for year " + year + " yearday " + yearday + " weekstart " + weekStart + " minDays " + minDaysInFirstWeek;
                            assertEquals(errMsg, testCal.get(java.util.Calendar.WEEK_OF_YEAR), tools.getWeekOfYear(year, month, day));
                        }
                    }
                }
            }
        }
    }


    @Test
    public void testGetDayOfYear()
    {
        java.util.Calendar testCal = new GregorianCalendar(TimeZone.getTimeZone("UTC"), Locale.US);
        for (int minDaysInFirstWeek = 1; minDaysInFirstWeek < 8; ++minDaysInFirstWeek)
        {
            testCal.setMinimalDaysInFirstWeek(minDaysInFirstWeek);
            for (int weekStart = 0; weekStart < 7; ++weekStart)
            {
                CalendarMetrics tools = new GregorianCalendarMetrics(Weekday.values()[weekStart], minDaysInFirstWeek);
                testCal.setFirstDayOfWeek(weekStart + 1);
                for (int year = MIN_YEAR; year < MAX_YEAR; ++year)
                {
                    for (int month = 0; month < 12; ++month)
                    {
                        for (int monthday = 1; monthday <= tools.getDaysPerPackedMonth(year, month); ++monthday)
                        {
                            testCal.set(year, month, monthday);

                            String errMsg = "";
                            // errMsg = "failed for year " + year + " yearday " + monthday + " weekstart " + weekStart + " minDays " + minDaysInFirstWeek"
                            assertEquals(errMsg, testCal.get(java.util.Calendar.DAY_OF_YEAR), tools.getDayOfYear(year, month, monthday));
                        }
                    }
                }
            }
        }
    }


    @Test
    public void testGetDayOfJan1st()
    {
        java.util.Calendar testCal = new GregorianCalendar(TimeZone.getTimeZone("UTC"));
        for (int minDaysInFirstWeek = 1; minDaysInFirstWeek < 8; ++minDaysInFirstWeek)
        {
            testCal.setMinimalDaysInFirstWeek(minDaysInFirstWeek);
            for (int weekStart = 0; weekStart < 7; ++weekStart)
            {
                CalendarMetrics tools = new GregorianCalendarMetrics(Weekday.values()[weekStart], minDaysInFirstWeek);
                testCal.setFirstDayOfWeek(weekStart + 1);
                for (int year = MIN_YEAR; year < MAX_YEAR; ++year)
                {
                    // set testCal to Jan 1st
                    testCal.set(year, 0, 1);
                    testCal.get(java.util.Calendar.WEEK_OF_YEAR);

                    String errMsg = "";
                    // errMsg = "failed for year " + year + " weekstart " + weekStart + " minDays " + minDaysInFirstWeek;
                    assertEquals(errMsg, testCal.get(java.util.Calendar.DAY_OF_WEEK) - 1, tools.getWeekDayOfFirstYearDay(year));
                }
            }
        }
    }


    @Test
    public void testGetYearDayOf1stWeekStart()
    {
        java.util.Calendar testCal = new GregorianCalendar(TimeZone.getTimeZone("UTC"));
        for (int minDaysInFirstWeek = 1; minDaysInFirstWeek < 8; ++minDaysInFirstWeek)
        {
            testCal.setMinimalDaysInFirstWeek(minDaysInFirstWeek);
            for (int weekStart = 0; weekStart < 7; ++weekStart)
            {
                CalendarMetrics tools = new GregorianCalendarMetrics(Weekday.values()[weekStart], minDaysInFirstWeek);
                testCal.setFirstDayOfWeek(weekStart + 1);
                for (int year = MIN_YEAR; year < MAX_YEAR; ++year)
                {
                    // set testCal to start of the first week in that year
                    testCal.set(year, 0, 1);
                    testCal.set(java.util.Calendar.WEEK_OF_YEAR, 1);
                    testCal.set(java.util.Calendar.DAY_OF_WEEK, weekStart + 1);

                    int yd = tools.getYearDayOfFirstWeekStart(year);
                    if (yd < 1)
                    {
                        yd += tools.getDaysPerYear(year - 1);
                    }
                    String errMsg = "";
                    // errMsg = "failed for year " + year + " weekstart " + weekStart + " minDays " + minDaysInFirstWeek;

                    assertEquals(errMsg, testCal.get(java.util.Calendar.DAY_OF_YEAR), yd);
                }
            }
        }
    }


    @Test
    public void testGetYearDaysForMonth()
    {
        java.util.Calendar testCal = new GregorianCalendar(TimeZone.getTimeZone("UTC"));
        for (int minDaysInFirstWeek = 1; minDaysInFirstWeek < 8; ++minDaysInFirstWeek)
        {
            testCal.setMinimalDaysInFirstWeek(minDaysInFirstWeek);
            for (int weekStart = 0; weekStart < 7; ++weekStart)
            {
                CalendarMetrics tools = new GregorianCalendarMetrics(Weekday.values()[weekStart], minDaysInFirstWeek);
                testCal.setFirstDayOfWeek(weekStart + 1);
                for (int year = MIN_YEAR; year < MAX_YEAR; ++year)
                {
                    for (int month = 0; month < 12; ++month)
                    {
                        testCal.set(year, month, 1);

                        assertEquals("failed for year " + year + " weekstart " + weekStart + " minDays " + minDaysInFirstWeek,
                                testCal.get(java.util.Calendar.DAY_OF_YEAR) - 1, tools.getYearDaysForPackedMonth(year, month));
                    }
                }
            }
        }
    }


    @Test
    public void testGetMonthAndDayOfYearDay()
    {
        java.util.Calendar testCal = new GregorianCalendar(TimeZone.getTimeZone("UTC"), Locale.US);
        for (int minDaysInFirstWeek = 1; minDaysInFirstWeek < 8; ++minDaysInFirstWeek)
        {
            testCal.setMinimalDaysInFirstWeek(minDaysInFirstWeek);
            for (int weekStart = 0; weekStart < 7; ++weekStart)
            {
                CalendarMetrics tools = new GregorianCalendarMetrics(Weekday.values()[weekStart], minDaysInFirstWeek);
                testCal.setFirstDayOfWeek(weekStart + 1);
                for (int year = MIN_YEAR; year < MAX_YEAR; ++year)
                {
                    for (int yearday = -600; yearday <= tools.getDaysPerYear(year) + 600; ++yearday)
                    {
                        testCal.set(year, 0, 1);
                        testCal.set(java.util.Calendar.DAY_OF_YEAR, yearday);
                        // System.out.println("" + testCal);

                        int monthAndDay = tools.getMonthAndDayOfYearDay(year, yearday);
                        int month = CalendarMetrics.packedMonth(monthAndDay);
                        int day = CalendarMetrics.dayOfMonth(monthAndDay);
                        String errMsgMonth = "", errMsgDay = "";
                        // errMsgMonth = "month failed for year " + year + " yearday " + yearday + " weekstart " + weekStart + " minDays " + minDaysInFirstWeek;
                        // errMsgDay = "day failed for year " + year + " yearday " + yearday + " weekstart " + weekStart + " minDays " + minDaysInFirstWeek;
                        assertEquals(errMsgMonth, testCal.get(java.util.Calendar.MONTH), month);

                        assertEquals(errMsgDay, testCal.get(java.util.Calendar.DAY_OF_MONTH), day);
                    }
                }
            }
        }
    }


    @Test
    public void testGetDayOfWeekIntInt()
    {
        java.util.Calendar testCal = new GregorianCalendar(TimeZone.getTimeZone("UTC"), Locale.US);
        for (int minDaysInFirstWeek = 1; minDaysInFirstWeek < 8; ++minDaysInFirstWeek)
        {
            testCal.setMinimalDaysInFirstWeek(minDaysInFirstWeek);
            for (int weekStart = 0; weekStart < 7; ++weekStart)
            {
                CalendarMetrics tools = new GregorianCalendarMetrics(Weekday.values()[weekStart], minDaysInFirstWeek);
                testCal.setFirstDayOfWeek(weekStart + 1);
                for (int year = MIN_YEAR; year < MAX_YEAR; ++year)
                {
                    testCal.set(year, 0, 1);
                    for (int yearday = 1; yearday <= tools.getDaysPerYear(year); ++yearday)
                    {
                        testCal.set(java.util.Calendar.DAY_OF_YEAR, yearday);
                        // System.out.println("" + testCal);

                        String errMsg = "";
                        // errMsg = "day of week failed for year " + year + " yearday " + yearday + " weekstart " + weekStart + " minDays "+minDaysInFirstWeek;
                        assertEquals(errMsg, testCal.get(java.util.Calendar.DAY_OF_WEEK) - 1, tools.getDayOfWeek(year, yearday));
                    }
                }
            }
        }
    }


    @Test
    public void testGetDayOfWeekIntIntInt()
    {
        java.util.Calendar testCal = new GregorianCalendar(TimeZone.getTimeZone("UTC"), Locale.US);
        for (int minDaysInFirstWeek = 1; minDaysInFirstWeek < 8; ++minDaysInFirstWeek)
        {
            testCal.setMinimalDaysInFirstWeek(minDaysInFirstWeek);
            for (int weekStart = 0; weekStart < 7; ++weekStart)
            {
                CalendarMetrics tools = new GregorianCalendarMetrics(Weekday.values()[weekStart], minDaysInFirstWeek);
                testCal.setFirstDayOfWeek(weekStart + 1);
                for (int year = MIN_YEAR; year < MAX_YEAR; ++year)
                {
                    for (int month = 0; month < 12; ++month)
                    {
                        for (int monthday = 1; monthday <= tools.getDaysPerPackedMonth(year, month); ++monthday)
                        {
                            testCal.set(year, month, monthday);

                            String errMsg = "";
                            // errMsg = "day of week failed for year " + year + " yearday " + monthday + " weekstart " + weekStart + " minDays "
                            // + minDaysInFirstWeek;
                            assertEquals(errMsg, testCal.get(java.util.Calendar.DAY_OF_WEEK) - 1, tools.getDayOfWeek(year, month, monthday));
                        }
                    }
                }
            }
        }
    }


    @Test
    public void testGetMonthOfYearDay()
    {
        java.util.Calendar testCal = new GregorianCalendar(TimeZone.getTimeZone("UTC"), Locale.US);
        for (int minDaysInFirstWeek = 1; minDaysInFirstWeek < 8; ++minDaysInFirstWeek)
        {
            testCal.setMinimalDaysInFirstWeek(minDaysInFirstWeek);
            for (int weekStart = 0; weekStart < 7; ++weekStart)
            {
                CalendarMetrics tools = new GregorianCalendarMetrics(Weekday.values()[weekStart], minDaysInFirstWeek);
                testCal.setFirstDayOfWeek(weekStart + 1);
                for (int year = MIN_YEAR; year < MAX_YEAR; ++year)
                {
                    for (int yearDay = -600; yearDay <= tools.getDaysPerYear(year) + 600; ++yearDay)
                    {
                        testCal.set(year, 0, 1);
                        testCal.set(java.util.Calendar.DAY_OF_YEAR, yearDay);
                        String errMsg = "";
                        // errMsg = "getMonthOfYearDay failed for year=" + year + " yearDay=" + yearDay + " weekStart=" + weekStart + " minDays=" +
                        // minDaysInFirstWeek;

                        assertEquals(errMsg, testCal.get(Calendar.MONTH), tools.getPackedMonthOfYearDay(year, yearDay));

                    }

                }
            }
        }
    }


    @Test
    public void testGetDayOfMonthOfYearDay()
    {
        java.util.Calendar testCal = new GregorianCalendar(TimeZone.getTimeZone("UTC"), Locale.US);
        for (int minDaysInFirstWeek = 1; minDaysInFirstWeek < 8; ++minDaysInFirstWeek)
        {
            testCal.setMinimalDaysInFirstWeek(minDaysInFirstWeek);
            for (int weekStart = 0; weekStart < 7; ++weekStart)
            {
                CalendarMetrics tools = new GregorianCalendarMetrics(Weekday.values()[weekStart], minDaysInFirstWeek);
                testCal.setFirstDayOfWeek(weekStart + 1);
                for (int year = MIN_YEAR; year < MAX_YEAR; ++year)
                {
                    testCal.set(year, 0, 1);
                    for (int yearDay = 1; yearDay <= tools.getDaysPerYear(year); ++yearDay)
                    {
                        testCal.set(java.util.Calendar.DAY_OF_YEAR, yearDay);
                        String errMsg = "";
                        // errMsg = "getDayOfMonthOfYearDay failed for year=" + year + " yearDay=" + yearDay + " weekStart=" + " minDays=" + minDaysInFirstWeek;

                        assertEquals(errMsg, testCal.get(Calendar.DAY_OF_MONTH), tools.getDayOfMonthOfYearDay(year, yearDay));
                    }
                }
            }

        }
    }


    @Test
    public void testGetYearDayOfIsoYear()
    {
        java.util.Calendar testCal = new GregorianCalendar(TimeZone.getTimeZone("UTC"), Locale.US);
        for (int minDaysInFirstWeek = 1; minDaysInFirstWeek < 8; ++minDaysInFirstWeek)
        {
            testCal.setMinimalDaysInFirstWeek(minDaysInFirstWeek);
            for (int weekStart = 0; weekStart < 7; ++weekStart)
            {
                CalendarMetrics tools = new GregorianCalendarMetrics(Weekday.values()[weekStart], minDaysInFirstWeek);
                testCal.setFirstDayOfWeek(weekStart + 1);

                for (int year = MIN_YEAR; year < MAX_YEAR; ++year)
                {
                    for (int week = 1; week <= tools.getWeeksPerYear(year); ++week)
                    {
                        for (int dayOfWeek = 0; dayOfWeek < 7; ++dayOfWeek)
                        {
                            testCal.set(year, 0, 8);
                            testCal.set(java.util.Calendar.WEEK_OF_YEAR, week);
                            testCal.set(java.util.Calendar.DAY_OF_WEEK, dayOfWeek + 1);

                            String errMsg = "";
                            // errMsg = "getYearDayOfIsoYear failed for year=" + year + " week=" + week + " dayOfWeek=" + dayOfWeek + " weekStart=" + weekStart
                            // + " minDays=" + minDaysInFirstWeek;

                            int yd = tools.getYearDayOfIsoYear(year, week, dayOfWeek);
                            if (yd < 1)
                            {
                                yd += tools.getDaysPerYear(year - 1);
                            }
                            else if (yd > tools.getDaysPerYear(year))
                            {
                                yd -= tools.getDaysPerYear(year);
                            }

                            assertEquals(errMsg, testCal.get(Calendar.DAY_OF_YEAR), yd);
                        }
                    }
                }
            }

        }
    }


    @Test
    public void testGetYearDayOfWeekStart()
    {
        java.util.Calendar testCal = new GregorianCalendar(TimeZone.getTimeZone("UTC"));
        for (int minDaysInFirstWeek = 1; minDaysInFirstWeek < 8; ++minDaysInFirstWeek)
        {
            testCal.setMinimalDaysInFirstWeek(minDaysInFirstWeek);
            for (int weekStart = 0; weekStart < 7; ++weekStart)
            {
                CalendarMetrics tools = new GregorianCalendarMetrics(Weekday.values()[weekStart], minDaysInFirstWeek);
                testCal.setFirstDayOfWeek(weekStart + 1);
                for (int year = MIN_YEAR; year < MAX_YEAR; ++year)
                {
                    for (int week = 1; week <= tools.getWeeksPerYear(year); ++week)
                    {
                        // set testCal to start of the first week in that year
                        testCal.set(year, 0, 8);
                        testCal.set(java.util.Calendar.WEEK_OF_YEAR, week);
                        testCal.set(java.util.Calendar.DAY_OF_WEEK, weekStart + 1);

                        int yd = tools.getYearDayOfWeekStart(year, week);
                        if (yd < 1)
                        {
                            yd += tools.getDaysPerYear(year - 1);
                        }
                        else if (yd > tools.getDaysPerYear(year))
                        {
                            yd -= tools.getDaysPerYear(year);
                        }

                        String errMsg = "";
                        // errMsg = "failed for year " + year + " weekstart " + weekStart + " minDays " + minDaysInFirstWeek;

                        assertEquals(errMsg, testCal.get(java.util.Calendar.DAY_OF_YEAR), yd);
                    }
                }
            }
        }
    }


    @Test
    public void testGetUtcTimeStamp()
    {
        java.util.Calendar testCal = new GregorianCalendar(TimeZone.getTimeZone("UTC"), Locale.US);
        testCal.setTimeInMillis(0);
        for (int minDaysInFirstWeek = 1; minDaysInFirstWeek < 8; ++minDaysInFirstWeek)
        {
            testCal.setMinimalDaysInFirstWeek(minDaysInFirstWeek);
            for (int weekStart = 0; weekStart < 7; ++weekStart)
            {
                GregorianCalendarMetrics tools = new GregorianCalendarMetrics(Weekday.values()[weekStart], minDaysInFirstWeek);
                testCal.setFirstDayOfWeek(weekStart + 1);
                for (int year = MIN_YEAR; year < MAX_YEAR; ++year)
                {
                    testCal.set(year, 0, 1);
                    for (int yearDay = 1; yearDay <= tools.getDaysPerYear(year); ++yearDay)
                    {
                        testCal.set(java.util.Calendar.DAY_OF_YEAR, yearDay);
                        String errMsg = "";
                        // errMsg = "getUtcTimeStamp failed for year=" + year + " yearDay=" + yearDay + " weekStart=" + " minDays=" + minDaysInFirstWeek;

                        assertEquals(errMsg, testCal.getTimeInMillis(), tools.getTimeStamp(year, yearDay, 0, 0, 0, 0));
                    }
                }
            }

        }
    }


    @Test
    public void testGetTimeStamp()
    {
        for (String z : new String[] {
                "America/Bogota", "America/Los_Angeles", "America/New_York", "UTC", "GMT", "Europe/Berlin", "Asia/Peking", "Asia/Tokyo",
                "Australia/Sidney" })
        {
            TimeZone zone = TimeZone.getTimeZone(z);
            java.util.Calendar testCal = new GregorianCalendar(zone, Locale.US);
            testCal.setMinimalDaysInFirstWeek(4);
            testCal.setFirstDayOfWeek(Calendar.MONDAY);
            testCal.setTimeInMillis(0);

            CalendarMetrics tools = new GregorianCalendarMetrics(Weekday.MO, 4);

            for (int year = 1900; year < 2038; ++year)
            {
                String errMsg = "";
                for (int month = 0; month < tools.getMonthsPerYear(year); ++month)
                {
                    for (int day = 1; day <= tools.getDaysPerPackedMonth(year, month); ++day)
                    {
                        for (int hour = 0; hour < 24; ++hour)
                        {
                            for (int minute = 0; minute < 60; ++minute)
                            {
                                testCal.set(year, month, day, hour, minute, 0);
                                // errMsg = "getUtcTimeStamp failed for year=" + year + " month=" + month + " day=" + day + " weekStart=" + " minDays="
                                // + minDaysInFirstWeek;

                                assertEquals(errMsg, testCal.getTimeInMillis(), tools.toMillis(zone, year, month, day, hour, minute, 0, 0));
                            }
                        }
                    }
                }
            }
        }
    }


    @Test
    public void testToInstance()
    {
        for (String z : new String[] {
                "America/Bogota", "America/Los_Angeles", "America/New_York", "UTC", "GMT", "Europe/Berlin", "Asia/Peking", "Asia/Tokyo",
                "Australia/Sidney" })
        {
            TimeZone zone = TimeZone.getTimeZone(z);
            java.util.Calendar testCal = new GregorianCalendar(zone, Locale.US);
            testCal.setMinimalDaysInFirstWeek(4);
            testCal.setFirstDayOfWeek(Calendar.MONDAY);
            testCal.setTimeInMillis(0);

            CalendarMetrics tools = new GregorianCalendarMetrics(Weekday.MO, 4);

            for (int year = 1600; year < MAX_YEAR; ++year)
            {
                String errMsg = "";
                for (int month = 0; month < tools.getMonthsPerYear(year); ++month)
                {
                    for (int day = 1; day <= tools.getDaysPerPackedMonth(year, month); ++day)
                    {
                        for (int hour = 0; hour < 24; ++hour)
                        {
                            for (int minute = 0; minute < 60; ++minute)
                            {
                                testCal.set(year, month, day, hour, minute, 0);
                                // errMsg = "toInstance failed for year=" + year + " month=" + month + " day=" + day + " hour=" + hour + " minute=" + minute
                                // + "  tz=" + zone.getID();

                                long instance = tools.toInstance(testCal.getTimeInMillis(), zone);

                                assertEquals(errMsg, testCal.get(Calendar.YEAR), Instance.year(instance));
                                assertEquals(errMsg, testCal.get(Calendar.MONTH), Instance.month(instance));
                                assertEquals(errMsg, testCal.get(Calendar.DAY_OF_MONTH), Instance.dayOfMonth(instance));
                                assertEquals(errMsg, testCal.get(Calendar.HOUR_OF_DAY), Instance.hour(instance));
                                assertEquals(errMsg, testCal.get(Calendar.MINUTE), Instance.minute(instance));
                                assertEquals(errMsg, testCal.get(Calendar.SECOND), Instance.second(instance));
                            }
                        }
                    }
                }
            }
        }
    }


    @Test
    public void testStartOfWeek()
    {
        for (int minDaysInFirstWeek = 1; minDaysInFirstWeek < 8; ++minDaysInFirstWeek)
        {
            for (int weekStart = 0; weekStart < 7; ++weekStart)
            {
                CalendarMetrics tools = new GregorianCalendarMetrics(Weekday.values()[weekStart], minDaysInFirstWeek);

                for (int year = MIN_YEAR; year < MAX_YEAR; ++year)
                {
                    for (int month = 0; month < tools.getMonthsPerYear(year); ++month)
                    {
                        for (int day = 1; day <= tools.getDaysPerPackedMonth(year, month); ++day)
                        {
                            long instance = Instance.make(year, month, day, 0, 0, 0);
                            int week = tools.getWeekOfYear(year, month, day);

                            instance = tools.startOfWeek(instance);

                            String errMsg = "";
                            // errMsg = "getYearDayOfIsoYear failed for year=" + year + " week=" + week + " weekStart=" + weekStart + " minDays="
                            // + minDaysInFirstWeek;

                            assertEquals(errMsg, weekStart,
                                    tools.getDayOfWeek(Instance.year(instance), Instance.month(instance), Instance.dayOfMonth(instance)));
                            assertEquals(errMsg, week, tools.getWeekOfYear(Instance.year(instance), Instance.month(instance), Instance.dayOfMonth(instance)));
                        }
                    }
                }
            }
        }
    }


    @Test
    public void testSetDayOfWeek()
    {
        for (int minDaysInFirstWeek = 1; minDaysInFirstWeek < 8; ++minDaysInFirstWeek)
        {
            for (int weekStart = 0; weekStart < 7; ++weekStart)
            {
                CalendarMetrics tools = new GregorianCalendarMetrics(Weekday.values()[weekStart], minDaysInFirstWeek);

                for (int year = MIN_YEAR; year < MAX_YEAR; ++year)
                {
                    for (int month = 0; month < tools.getMonthsPerYear(year); ++month)
                    {
                        for (int day = 1; day <= tools.getDaysPerPackedMonth(year, month); ++day)
                        {
                            for (int dayOfWeek = 0; dayOfWeek < 7; ++dayOfWeek)
                            {
                                long instance = Instance.make(year, month, day, 0, 0, 0);
                                int week = tools.getWeekOfYear(year, month, day);

                                instance = tools.setDayOfWeek(instance, dayOfWeek);

                                String errMsg = "";
                                // errMsg = "getYearDayOfIsoYear failed for year=" + year + " week=" + week + " dayOfWeek=" + dayOfWeek + " weekStart="
                                // + weekStart + " minDays=" + minDaysInFirstWeek;

                                assertEquals(errMsg, dayOfWeek,
                                        tools.getDayOfWeek(Instance.year(instance), Instance.month(instance), Instance.dayOfMonth(instance)));
                                assertEquals(errMsg, week,
                                        tools.getWeekOfYear(Instance.year(instance), Instance.month(instance), Instance.dayOfMonth(instance)));
                            }
                        }
                    }
                }
            }
        }
    }

}
