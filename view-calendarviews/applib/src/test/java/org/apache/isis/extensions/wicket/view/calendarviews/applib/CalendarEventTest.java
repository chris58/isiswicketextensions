/*
 *  Licensed to the Apache Software Foundation (ASF) under one
 *  or more contributor license agreements.  See the NOTICE file
 *  distributed with this work for additional information
 *  regarding copyright ownership.  The ASF licenses this file
 *  to you under the Apache License, Version 2.0 (the
 *  "License"); you may not use this file except in compliance
 *  with the License.  You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing,
 *  software distributed under the License is distributed on an
 *  "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 *  KIND, either express or implied.  See the License for the
 *  specific language governing permissions and limitations
 *  under the License.
 */


package org.apache.isis.extensions.wicket.view.calendarviews.applib;


import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

import java.util.Date;

import org.apache.isis.extensions.wicket.view.calendarviews.applib.CalendarEvent;
import org.junit.Test;

public class CalendarEventTest {


	@SuppressWarnings("deprecation")
	@Test
	public void intraDayEvent() throws Exception {
		final CalendarEvent nextEvent = CalendarEvent.newEvent(new Date(110, 3, 5, 10, 24));
		assertThat(nextEvent.toString(), is("2010-04-05 10:15 (1 hour)"));
	}
	
	@SuppressWarnings("deprecation")
	@Test
	public void allDayEvent() throws Exception {
		final CalendarEvent nextEvent = CalendarEvent.newAllDayEvent(new Date(110, 3, 5), 2);
		assertThat(nextEvent.toString(), is("2010-04-05 (2 days)"));
	}
	
	@Test
	public void fromStringIntraDay() throws Exception {
		final CalendarEvent parsed = CalendarEvent.fromString("2010-05-04 16:00 (1 hour)");
		assertThat(parsed.toString(), is("2010-05-04 16:00 (1 hour)"));
	}
	
	@Test
	public void fromStringAllDay() throws Exception {
		final CalendarEvent parsed = CalendarEvent.fromString("2010-05-04 (3 days)");
		assertThat(parsed.toString(), is("2010-05-04 (3 days)"));
	}
	
	@SuppressWarnings("deprecation")
	@Test
	public void fromStringIntraDayEndTime() throws Exception {
		final CalendarEvent parsed = CalendarEvent.fromString("2010-05-04 16:00 (3 hour)");
		final Date endTime = parsed.getEndTime();
		assertThat(endTime.getHours(), is(19));
	}
	

}
