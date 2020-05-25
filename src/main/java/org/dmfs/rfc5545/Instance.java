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

import org.dmfs.rfc5545.calendarmetrics.CalendarMetrics;

import java.io.IOException;
import java.io.Writer;
import java.util.TimeZone;


/**
 * Provides a set of static methods to manipulate date and time values stored in a single packed long value.
 * <p>
 * Storing the values of an instance in a single long allows to compare them quickly and it doesn't require any object instantiations.
 * <p>
 * Note that the instance value alone doesn't say much without the corresponding {@link CalendarMetrics} and {@link TimeZone}.
 * <p>
 * Also note: never persist packed instance values. The implementation and the format of the packed long may change.
 *
 * @author Marten Gajda
 */
public final class Instance
{
    private final static int YEAR_BITS = 18;
    private final static int MONTH_BITS = 8;
    private final static int DAY_BITS = 7;
    private final static int HOUR_BITS = 5;
    private final static int MINUTE_BITS = 6;
    private final static int SECOND_BITS = 6;
    private final static int WEEKDAY_BITS = 4;

    private final static int WEEKDAY_POS = 0;
    private final static int SECOND_POS = WEEKDAY_BITS + WEEKDAY_POS;
    private final static int MINUTE_POS = SECOND_BITS + SECOND_POS;
    private final static int HOUR_POS = MINUTE_BITS + MINUTE_POS;
    private final static int DAY_POS = HOUR_BITS + HOUR_POS;
    private final static int MONTH_POS = DAY_BITS + DAY_POS;
    private final static int YEAR_POS = MONTH_BITS + MONTH_POS;

    private final static long SECOND_MASK = ((1L << SECOND_BITS) - 1) << SECOND_POS;
    private final static long MINUTE_MASK = ((1L << MINUTE_BITS) - 1) << MINUTE_POS;
    private final static long HOUR_MASK = ((1L << HOUR_BITS) - 1) << HOUR_POS;
    private final static long WEEKDAY_MASK = ((1L << WEEKDAY_BITS) - 1) << WEEKDAY_POS;
    private final static long DAY_MASK = ((1L << DAY_BITS) - 1) << DAY_POS;
    private final static long MONTH_MASK = ((1L << MONTH_BITS) - 1) << MONTH_POS;
    private final static long YEAR_MASK = ((1L << YEAR_BITS) - 1) << YEAR_POS;

    private final static int YEAR_BIAS = 0;
    private final static int DAY_BIAS = 1 << (DAY_BITS - 1);


    /**
     * You shall not instantiate this class.
     */
    private Instance()
    {
    }


    /**
     * Return an instance value for the given date and time.
     *
     * @param year
     *         The year of the instance.
     * @param month
     *         The month of the instance.
     * @param dayOfMonth
     *         The day of the instance.
     * @param hour
     *         The hour of the instance.
     * @param minute
     *         The minutes of the instance.
     * @param second
     *         The seconds of the instance.
     * @param dayOfWeek
     *         The day of the week of the instance.
     *
     * @return The packed instance value.
     */
    public static long make(int year, int month, int dayOfMonth, int hour, int minute, int second, int dayOfWeek)
    {
        return (((long) year + YEAR_BIAS) << YEAR_POS) | ((long) month << MONTH_POS) | (((long) dayOfMonth + DAY_BIAS) << DAY_POS)
                | (((long) dayOfWeek) << WEEKDAY_POS) | ((long) hour << HOUR_POS) | ((long) minute << MINUTE_POS) | ((long) second << SECOND_POS);
    }


    /**
     * Return an instance value for the given date and time.
     *
     * @param year
     *         The year of the instance.
     * @param month
     *         The month of the instance.
     * @param dayOfMonth
     *         The day of the instance.
     * @param hour
     *         The hour of the instance.
     * @param minute
     *         The minutes of the instance.
     * @param second
     *         The seconds of the instance.
     *
     * @return The packed instance value.
     */
    public static long make(int year, int month, int dayOfMonth, int hour, int minute, int second)
    {
        return (((long) year + YEAR_BIAS) << YEAR_POS) | ((long) month << MONTH_POS) | (((long) dayOfMonth + DAY_BIAS) << DAY_POS) | ((long) hour << HOUR_POS)
                | ((long) minute << MINUTE_POS) | ((long) second << SECOND_POS);
    }


    /**
     * Update the year of the given instance.
     *
     * @param instance
     *         The instance to update.
     * @param year
     *         The new year.
     *
     * @return The updated instance.
     */
    public static long setYear(long instance, int year)
    {
        return (instance & ~YEAR_MASK) | (((long) year + YEAR_BIAS) << YEAR_POS);
    }


    /**
     * Update the month of the given instance.
     *
     * @param instance
     *         The instance to update.
     * @param month
     *         The new month.
     *
     * @return The updated instance.
     */
    public static long setMonth(long instance, int month)
    {
        return (instance & ~MONTH_MASK) | ((long) month << MONTH_POS);
    }


    /**
     * Update the day of month of the given instance.
     *
     * @param instance
     *         The instance to update.
     * @param dayOfMonth
     *         The new day of month value.
     *
     * @return The updated instance.
     */
    public static long setDayOfMonth(long instance, int dayOfMonth)
    {
        return (instance & ~DAY_MASK) | (((long) dayOfMonth + DAY_BIAS) << DAY_POS);
    }


    /**
     * Update the day of week of the given instance.
     *
     * @param instance
     *         The instance to update.
     * @param dayOfWeek
     *         The new day of week value.
     *
     * @return The updated instance.
     */
    public static long setDayOfWeek(long instance, int dayOfWeek)
    {
        return (instance & ~WEEKDAY_MASK) | (dayOfWeek << WEEKDAY_POS);
    }


    /**
     * Update the month and day of month of the given instance. This is basically like calling {@link #setMonth(long, int)} and {@link #setDayOfMonth(long,
     * int)}, just shorter.
     *
     * @param instance
     *         The instance to update.
     * @param month
     *         The new month value.
     * @param dayOfMonth
     *         The new day of month value.
     *
     * @return The updated instance.
     */
    public static long setMonthAndDayOfMonth(long instance, int month, int dayOfMonth)
    {
        return (instance & ~(MONTH_MASK | DAY_MASK)) | ((long) month << MONTH_POS) | (((long) dayOfMonth + DAY_BIAS) << DAY_POS);
    }


    /**
     * Update the hour of the given instance.
     *
     * @param instance
     *         The instance to update.
     * @param hour
     *         The new hour value.
     *
     * @return The updated instance.
     */
    public static long setHour(long instance, int hour)
    {
        return (instance & ~HOUR_MASK) | ((long) hour << HOUR_POS);
    }


    /**
     * Update the minute of the given instance.
     *
     * @param instance
     *         The instance to update.
     * @param minute
     *         The new minute value.
     *
     * @return The updated instance.
     */
    public static long setMinute(long instance, int minute)
    {
        return (instance & ~MINUTE_MASK) | ((long) minute << MINUTE_POS);
    }


    /**
     * Update the seconds of the given instance.
     *
     * @param instance
     *         The instance to update.
     * @param second
     *         The new seconds value.
     *
     * @return The updated instance.
     */
    public static long setSecond(long instance, int second)
    {
        return (instance & ~SECOND_MASK) | ((long) second << SECOND_POS);
    }


    /**
     * Masks the day of week value of the given instance. The returned value represents the same instance, but day of week will be set to <code>0</code>.
     *
     * @param instance
     *         The instance to mask.
     *
     * @return The instance value with masked day of week.
     */
    public static long maskWeekday(long instance)
    {
        return (instance & ~(WEEKDAY_MASK));
    }


    /**
     * Get the year of the given packed instance.
     *
     * @param instance
     *         The instance.
     *
     * @return The year.
     */
    public static int year(long instance)
    {
        return (int) ((instance & YEAR_MASK) >>> YEAR_POS) - YEAR_BIAS;
    }


    /**
     * Get the month of the given packed instance.
     *
     * @param instance
     *         The instance.
     *
     * @return The month.
     */
    public static int month(long instance)
    {
        return (int) ((instance & MONTH_MASK) >>> MONTH_POS);
    }


    /**
     * Get the day of month of the given packed instance.
     *
     * @param instance
     *         The instance.
     *
     * @return The day of month.
     */
    public static int dayOfMonth(long instance)
    {
        return (int) ((instance & DAY_MASK) >>> DAY_POS) - DAY_BIAS;
    }


    /**
     * Get the hour of the given packed instance.
     *
     * @param instance
     *         The instance.
     *
     * @return The hour.
     */
    public static int hour(long instance)
    {
        return (int) ((instance & HOUR_MASK) >>> HOUR_POS);
    }


    /**
     * Get the minute of the given packed instance.
     *
     * @param instance
     *         The instance.
     *
     * @return The minute.
     */
    public static int minute(long instance)
    {
        return (int) ((instance & MINUTE_MASK) >>> MINUTE_POS);
    }


    /**
     * Get the seconds of the given packed instance.
     *
     * @param instance
     *         The instance.
     *
     * @return The seconds.
     */
    public static int second(long instance)
    {
        return (int) ((instance & SECOND_MASK) >>> SECOND_POS);
    }


    /**
     * Get the day of week stored in the packed instance. If the day of week has not been stored, this method returns an invalid value. You have to ensure
     * yourself that this value is valid. If in doubt, use {@link CalendarMetrics#getDayOfWeek(int, int, int)} to get the actual day of week.
     * <p>
     * Values are from 0-6 where 0 means Sunday and 6 means Saturday. Note that this is different from the values the {@link java.util.Calendar} class uses.
     * </p>
     *
     * @param instance
     *         The instance.
     *
     * @return The day of the week, if present.
     */
    public static int dayOfWeek(long instance)
    {
        return (int) ((instance & WEEKDAY_MASK) >>> WEEKDAY_POS);
    }


    /**
     * <p>
     * Convert the given instance to a String that conforms to <a href="https://tools.ietf.org/html/rfc5545#section-3.3.5">RFC 5545, Section 3.3.5</a>.
     * </p>
     * <h3>Example:</h3>
     *
     * <pre>
     * 20150304T203000
     * </pre>
     *
     * @param instance
     *         An instance value.
     *
     * @return The formatted date-time value.
     *
     * @see #toString(long, boolean)
     */
    public static String toString(long instance)
    {
        return toString(instance, false);
    }


    /**
     * <p>
     * Convert the given instance to a String that conforms to <a href="https://tools.ietf.org/html/rfc5545#section-3.3.5">RFC 5545, Section 3.3.5</a>. This
     * method has a parameter to tell whether the time part should be included or not.
     * </p>
     * <h3>Example:</h3>
     *
     * <pre>
     * dateOnly == false: 20150304T203000
     * dateOnly == true:  20150304
     * </pre>
     *
     * @param instance
     *         An instance value.
     * @param dateOnly
     *         <code>true</code> to return the date value only, <code>false</code> to also append the time.
     *
     * @return The formatted date-time value.
     *
     * @see #toString(long)
     */
    public static String toString(long instance, boolean dateOnly)
    {
        StringBuilder result = new StringBuilder(16);
        writeTo(result, instance, dateOnly);
        return result.toString();
    }


    /**
     * A helper to write the two least significant base-10 digits of an integer to a {@link StringBuilder}.
     *
     * @param sb
     *         The {@link StringBuilder} to write to.
     * @param num
     *         The value to write.
     */
    private static void writeDigits(StringBuilder sb, int num)
    {
        sb.append((char) ((num / 10) % 10 + '0'));
        sb.append((char) ((num % 10) + '0'));
    }


    /**
     * <p>
     * Write a string representation of the given instance that conforms to <a href="https://tools.ietf.org/html/rfc5545#section-3.3.5">RFC 5545, Section
     * 3.3.5</a> to a {@link StringBuilder}. This method always writes a time value. Use {@link #writeTo(Writer, long, boolean)} to write only the date.
     * </p>
     * <h3>Example:</h3>
     *
     * <pre>
     * 20150304T203000
     * </pre>
     *
     * @param out
     *         The {@link StringBuilder} to write to.
     * @param instance
     *         An instance value.
     *
     * @see #writeTo(Writer, long, boolean)
     */
    public static void writeTo(StringBuilder out, long instance)
    {
        writeTo(out, instance, false);
    }


    /**
     * <p>
     * Write a string representation of the given instance that conforms to <a href="https://tools.ietf.org/html/rfc5545#section-3.3.5">RFC 5545, Section
     * 3.3.5</a> to a {@link StringBuilder}. This method has a parameter to tell whether the time part should be written or not.
     * </p>
     * <h3>Example:</h3>
     *
     * <pre>
     * dateOnly == false: 20150304T203000
     * dateOnly == true:  20150304
     * </pre>
     *
     * @param out
     *         The {@link StringBuilder} to write to.
     * @param instance
     *         An instance value.
     * @param dateOnly
     *         <code>true</code> to write the date value only, <code>false</code> to also write the time.
     *
     * @see #writeTo(StringBuilder, long)
     */
    public static void writeTo(StringBuilder out, long instance, boolean dateOnly)
    {
        int year = Instance.year(instance);
        writeDigits(out, year / 100);
        writeDigits(out, year % 100);
        writeDigits(out, Instance.month(instance) + 1);
        writeDigits(out, Instance.dayOfMonth(instance));
        if (!dateOnly)
        {
            out.append('T');
            writeDigits(out, Instance.hour(instance));
            writeDigits(out, Instance.minute(instance));
            writeDigits(out, Instance.second(instance));
        }
    }


    /**
     * <p>
     * Write a string representation of the given instance that conforms to <a href="https://tools.ietf.org/html/rfc5545#section-3.3.5">RFC 5545, Section
     * 3.3.5</a> to a {@link Writer}. This method always writes a time value. Use {@link #writeTo(Writer, long, boolean)} to write only the date.
     * </p>
     * <h3>Example:</h3>
     *
     * <pre>
     * 20150304T203000
     * </pre>
     *
     * @param out
     *         The {@link Writer} to write to.
     * @param instance
     *         An instance value.
     *
     * @see #writeTo(Writer, long, boolean)
     */
    public static void writeTo(Writer out, long instance) throws IOException
    {
        writeTo(out, instance, false);
    }


    /**
     * <p>
     * Write a string representation of the given instance that conforms to <a href="https://tools.ietf.org/html/rfc5545#section-3.3.5">RFC 5545, Section
     * 3.3.5</a> to a {@link Writer}. This method has a parameter to tell whether the time part should be written or not.
     * </p>
     * <h3>Example:</h3>
     *
     * <pre>
     * dateOnly == false: 20150304T203000
     * dateOnly == true:  20150304
     * </pre>
     *
     * @param out
     *         The {@link Writer} to write to.
     * @param instance
     *         An instance value.
     * @param dateOnly
     *         <code>true</code> to write the date value only, <code>false</code> to also write the time.
     *
     * @throws IOException
     * @see #writeTo(Writer, long)
     */
    public static void writeTo(Writer out, long instance, boolean dateOnly) throws IOException
    {
        int year = Instance.year(instance);
        writeDigits(out, year / 100);
        writeDigits(out, year % 100);
        writeDigits(out, Instance.month(instance) + 1);
        writeDigits(out, Instance.dayOfMonth(instance));
        if (!dateOnly)
        {
            out.append('T');
            writeDigits(out, Instance.hour(instance));
            writeDigits(out, Instance.minute(instance));
            writeDigits(out, Instance.second(instance));
        }
    }


    /**
     * A helper to write the two least significant base-10 digits of an integer to a {@link Writer}.
     *
     * @param out
     *         The {@link Writer} to write to.
     * @param num
     *         The value to write.
     *
     * @throws IOException
     */
    private static void writeDigits(Writer out, int num) throws IOException
    {
        out.write((num / 10) % 10 + '0');
        out.write((num % 10) + '0');
    }

}
