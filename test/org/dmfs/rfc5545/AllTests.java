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

import org.dmfs.rfc5545.calendarmetrics.CalendarMetricsTest;
import org.dmfs.rfc5545.calendarmetrics.GregorianCalendarMetricsTest;
import org.dmfs.rfc5545.calendarmetrics.JulianCalendarMetricsTest;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;


@RunWith(Suite.class)
@SuiteClasses({ CalendarMetricsTest.class, GregorianCalendarMetricsTest.class, JulianCalendarMetricsTest.class, InstanceTest.class, })
public class AllTests
{

}
