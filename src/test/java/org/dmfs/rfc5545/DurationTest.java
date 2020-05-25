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

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;


public class DurationTest
{

    private final static void testParse(String durationString, int sign, int weeks, int days, int hours, int minutes, int seconds)
    {
        Duration duration = Duration.parse(durationString);
        assertEquals(sign, duration.getSign());
        assertEquals(weeks, duration.getWeeks());
        assertEquals(days, duration.getDays());
        assertEquals(hours, duration.getHours());
        assertEquals(minutes, duration.getMinutes());
        assertEquals(seconds, duration.getSeconds());
    }


    private final static void testIllegal(String durationString)
    {
        try
        {
            Duration.parse(durationString);
            fail("parse is expected to throw when parsing: " + durationString);
        }
        catch (IllegalArgumentException e)
        {
        }
    }


    @Before
    public void setUp() throws Exception
    {
    }


    @Test
    public void testAddDuration()
    {
        assertEquals(Duration.parse("P3W"), Duration.parse("P1W").addDuration(Duration.parse("P2W")));
        assertEquals(Duration.parse("-P3W"), Duration.parse("-P1W").addDuration(Duration.parse("-P2W")));

        assertEquals(Duration.parse("P3D"), Duration.parse("P1D").addDuration(Duration.parse("P2D")));
        assertEquals(Duration.parse("-P3D"), Duration.parse("-P1D").addDuration(Duration.parse("-P2D")));

        assertEquals(Duration.parse("P3DT5H10M30S"), Duration.parse("P1DT2H7M18S").addDuration(Duration.parse("P2DT3H3M12S")));
        assertEquals(Duration.parse("-P3DT5H10M30S"), Duration.parse("-P1DT2H7M18S").addDuration(Duration.parse("-P2DT3H3M12S")));

        assertEquals(Duration.parse("PT4H"), Duration.parse("PT60M").addDuration(Duration.parse("PT180M")));
        assertEquals(Duration.parse("PT3H"), Duration.parse("PT60M").addDuration(Duration.parse("PT7200S")));

        assertEquals(Duration.parse("PT1H"), Duration.parse("PT120M").addDuration(Duration.parse("-PT3600S")));

        assertEquals(Duration.parse("PT20H"), Duration.parse("P1D").addDuration(Duration.parse("-PT4H")));

        assertEquals(Duration.parse("-PT20H"), Duration.parse("-P1D").addDuration(Duration.parse("PT4H")));
    }


    @Test
    public void testParse()
    {
        testParse("P1W", 1 /* sign */, 1 /* weeks */, 0 /* days */, 0 /* hours */, 0 /* minutes */, 0 /* seconds */);
        testParse("+P1W", 1 /* sign */, 1 /* weeks */, 0 /* days */, 0 /* hours */, 0 /* minutes */, 0 /* seconds */);
        testParse("-P1W", -1 /* sign */, 1 /* weeks */, 0 /* days */, 0 /* hours */, 0 /* minutes */, 0 /* seconds */);

        testParse("P14W", 1 /* sign */, 14 /* weeks */, 0 /* days */, 0 /* hours */, 0 /* minutes */, 0 /* seconds */);
        testParse("+P14W", 1 /* sign */, 14 /* weeks */, 0 /* days */, 0 /* hours */, 0 /* minutes */, 0 /* seconds */);
        testParse("-P14W", -1 /* sign */, 14 /* weeks */, 0 /* days */, 0 /* hours */, 0 /* minutes */, 0 /* seconds */);

        testParse("P24W", 1 /* sign */, 24 /* weeks */, 0 /* days */, 0 /* hours */, 0 /* minutes */, 0 /* seconds */);
        testParse("+P24W", 1 /* sign */, 24 /* weeks */, 0 /* days */, 0 /* hours */, 0 /* minutes */, 0 /* seconds */);
        testParse("-P24W", -1 /* sign */, 24 /* weeks */, 0 /* days */, 0 /* hours */, 0 /* minutes */, 0 /* seconds */);

        testParse("P1D", 1 /* sign */, 0 /* weeks */, 1 /* days */, 0 /* hours */, 0 /* minutes */, 0 /* seconds */);
        testParse("+P1D", 1 /* sign */, 0 /* weeks */, 1 /* days */, 0 /* hours */, 0 /* minutes */, 0 /* seconds */);
        testParse("-P1D", -1 /* sign */, 0 /* weeks */, 1 /* days */, 0 /* hours */, 0 /* minutes */, 0 /* seconds */);

        testParse("P14D", 1 /* sign */, 2 /* weeks */, 0 /* days */, 0 /* hours */, 0 /* minutes */, 0 /* seconds */);
        testParse("+P14D", 1 /* sign */, 2 /* weeks */, 0 /* days */, 0 /* hours */, 0 /* minutes */, 0 /* seconds */);
        testParse("-P14D", -1 /* sign */, 2 /* weeks */, 0 /* days */, 0 /* hours */, 0 /* minutes */, 0 /* seconds */);

        testParse("P24D", 1 /* sign */, 0 /* weeks */, 24 /* days */, 0 /* hours */, 0 /* minutes */, 0 /* seconds */);
        testParse("+P24D", 1 /* sign */, 0 /* weeks */, 24 /* days */, 0 /* hours */, 0 /* minutes */, 0 /* seconds */);
        testParse("-P24D", -1 /* sign */, 0 /* weeks */, 24 /* days */, 0 /* hours */, 0 /* minutes */, 0 /* seconds */);

        testParse("P12DT34H56M78S", 1 /* sign */, 0 /* weeks */, 12 /* days */, 34 /* hours */, 57 /* minutes */, 18 /* seconds */);
        testParse("+P12DT34H56M78S", 1 /* sign */, 0 /* weeks */, 12 /* days */, 34 /* hours */, 57 /* minutes */, 18 /* seconds */);
        testParse("-P12DT34H56M78S", -1 /* sign */, 0 /* weeks */, 12 /* days */, 34 /* hours */, 57 /* minutes */, 18 /* seconds */);

        testParse("PT34H56M78S", 1 /* sign */, 0 /* weeks */, 0 /* days */, 34 /* hours */, 57 /* minutes */, 18 /* seconds */);
        testParse("+PT34H56M78S", 1 /* sign */, 0 /* weeks */, 0 /* days */, 34 /* hours */, 57 /* minutes */, 18 /* seconds */);
        testParse("-PT34H56M78S", -1 /* sign */, 0 /* weeks */, 0 /* days */, 34 /* hours */, 57 /* minutes */, 18 /* seconds */);

        testParse("PT56M78S", 1 /* sign */, 0 /* weeks */, 0 /* days */, 0 /* hours */, 57 /* minutes */, 18 /* seconds */);
        testParse("+PT56M78S", 1 /* sign */, 0 /* weeks */, 0 /* days */, 0 /* hours */, 57 /* minutes */, 18 /* seconds */);
        testParse("-PT56M78S", -1 /* sign */, 0 /* weeks */, 0 /* days */, 0 /* hours */, 57 /* minutes */, 18 /* seconds */);

        testParse("PT78S", 1 /* sign */, 0 /* weeks */, 0 /* days */, 0 /* hours */, 1 /* minutes */, 18 /* seconds */);
        testParse("+PT78S", 1 /* sign */, 0 /* weeks */, 0 /* days */, 0 /* hours */, 1 /* minutes */, 18 /* seconds */);
        testParse("-PT78S", -1 /* sign */, 0 /* weeks */, 0 /* days */, 0 /* hours */, 1 /* minutes */, 18 /* seconds */);

        testParse("PT56M", 1 /* sign */, 0 /* weeks */, 0 /* days */, 0 /* hours */, 56 /* minutes */, 0 /* seconds */);
        testParse("+PT56M", 1 /* sign */, 0 /* weeks */, 0 /* days */, 0 /* hours */, 56 /* minutes */, 0 /* seconds */);
        testParse("-PT56M", -1 /* sign */, 0 /* weeks */, 0 /* days */, 0 /* hours */, 56 /* minutes */, 0 /* seconds */);

        testParse("PT34H", 1 /* sign */, 0 /* weeks */, 0 /* days */, 34 /* hours */, 0 /* minutes */, 0 /* seconds */);
        testParse("+PT34H", 1 /* sign */, 0 /* weeks */, 0 /* days */, 34 /* hours */, 0 /* minutes */, 0 /* seconds */);
        testParse("-PT34H", -1 /* sign */, 0 /* weeks */, 0 /* days */, 34 /* hours */, 0 /* minutes */, 0 /* seconds */);

        testParse("PT34H56M", 1 /* sign */, 0 /* weeks */, 0 /* days */, 34 /* hours */, 56 /* minutes */, 0 /* seconds */);
        testParse("+PT34H56M", 1 /* sign */, 0 /* weeks */, 0 /* days */, 34 /* hours */, 56 /* minutes */, 0 /* seconds */);
        testParse("-PT34H56M", -1 /* sign */, 0 /* weeks */, 0 /* days */, 34 /* hours */, 56 /* minutes */, 0 /* seconds */);

        testParse("P12DT56M78S", 1 /* sign */, 0 /* weeks */, 12 /* days */, 0 /* hours */, 57 /* minutes */, 18 /* seconds */);
        testParse("+P12DT56M78S", 1 /* sign */, 0 /* weeks */, 12 /* days */, 0 /* hours */, 57 /* minutes */, 18 /* seconds */);
        testParse("-P12DT56M78S", -1 /* sign */, 0 /* weeks */, 12 /* days */, 0 /* hours */, 57 /* minutes */, 18 /* seconds */);

        testIllegal(null);
        testIllegal("");
        testIllegal("+");
        testIllegal("-");
        testIllegal("P");
        testIllegal("+P");
        testIllegal("-P");
        testIllegal("x");
        testIllegal("123");
        testIllegal("+PT");
        testIllegal("PT");
        testIllegal("P+1T");
        testIllegal("PT1");
        testIllegal("P1D2D");
        testIllegal("P1DT1S1H");
        testIllegal("P1D1H");
    }


    @Test
    public void testToString()
    {
        assertEquals("P0D", new Duration(-1, 0).toString());
        assertEquals("P0D", new Duration(1 /* sign */, 0 /* days */, 0 /* hours */, 0 /* minutes */, 0 /* seconds */).toString());
        assertEquals("P12W", new Duration(1, 12).toString());
        assertEquals("-P12W", new Duration(-1, 12).toString());
        assertEquals("P12D", new Duration(1 /* sign */, 12 /* days */, 0 /* hours */, 0 /* minutes */, 0 /* seconds */).toString());
        assertEquals("P12DT1S", new Duration(1 /* sign */, 12 /* days */, 0 /* hours */, 0 /* minutes */, 1 /* seconds */).toString());
        assertEquals("-P12DT1S", new Duration(-1 /* sign */, 12 /* days */, 0 /* hours */, 0 /* minutes */, 1 /* seconds */).toString());
        assertEquals("P12DT34H57M18S", new Duration(1 /* sign */, 12 /* days */, 34/* hours */, 56 /* minutes */, 78 /* seconds */).toString());
        assertEquals("-P12DT34H57M18S", new Duration(-1 /* sign */, 12 /* days */, 34/* hours */, 56 /* minutes */, 78 /* seconds */).toString());
    }

}
