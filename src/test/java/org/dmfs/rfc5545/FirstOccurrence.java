/*
 * Copyright (C) 2026 Thorben Betten
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

import java.time.Instant;
import java.time.zone.ZoneOffsetTransition;
import java.util.TimeZone;


/**
 * {@link java.util.Calendar} resolves a local time that occurs twice to its second occurrence, RFC 5545, section 3.3.5 asks for the first one.
 */
public final class FirstOccurrence
{
    private FirstOccurrence()
    {
    }


    /**
     * Moves a timestamp from the second occurrence of a repeated local time to the first one.
     *
     * @param timestamp
     *         A timestamp as {@link java.util.Calendar} computes it for a local time.
     * @param timeZone
     *         The time zone of the local time.
     *
     * @return The timestamp of the first occurrence of the same local time.
     */
    public static long of(long timestamp, TimeZone timeZone)
    {
        ZoneOffsetTransition transition = timeZone.toZoneId().getRules().previousTransition(Instant.ofEpochMilli(timestamp + 1));
        if (transition != null && transition.isOverlap())
        {
            long overlap = -transition.getDuration().toMillis();
            if (timestamp - transition.getInstant().toEpochMilli() < overlap)
            {
                return timestamp - overlap;
            }
        }
        return timestamp;
    }
}
