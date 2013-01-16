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

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.isis.applib.annotation.Value;

/**
 * Corresponds closely to the <tt>IEvent</tt> interface defined by the 
 * <tt>calendarviews</tt> component from wicket-stuff (used as the underlying
 * implementation). 
 */
@Value(semanticsProviderClass=CalendarEventSemanticsProvider.class)
public class CalendarEvent implements Serializable, Comparable<CalendarEvent> {

	private static final long serialVersionUID = 1L;
	
	private Date startTime;
	private Date endTime;
	private boolean allDayEvent;

	/**
	 * only populated for all day events.
	 */
	private int numDays;
	/**
	 * only populated for all day events.
	 */
	private int numHours;

	/**
	 * An event starting on the next hour, lasting for one hour.
	 */
	public static CalendarEvent newEvent() {
		return newEvent(nextHour());
	}

	/**
	 * An event starting at the specified time, lasting for one hour.
	 */
	public static CalendarEvent newEvent(Date startTime) {
		return newEvent(startTime, 1);
	}
	
	/**
	 * An event starting at the specified time, lasting for the specified number of hours.
	 */
	public static CalendarEvent newEvent(Date startTime, int hours) {
		Date adjustedStartTime = nearestQuarterHour(startTime);
		final Date endTime = add(adjustedStartTime, hours, Calendar.HOUR);
		final CalendarEvent calendarEvent = new CalendarEvent(adjustedStartTime, endTime, false);
		calendarEvent.numHours = hours;
		return calendarEvent;
	}

	private static Date nearestQuarterHour(Date date) {
		final Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.clear(Calendar.SECOND);
		cal.clear(Calendar.MILLISECOND);
		int minutes = cal.get(Calendar.MINUTE);
		minutes = minutes / 15 * 15; // round down
		cal.set(Calendar.MINUTE, minutes);
		return cal.getTime();
	}

	/**
	 * An all-day event, lasting one day
	 */
	public static CalendarEvent newAllDayEvent(Date startTime) {
		return newAllDayEvent(startTime, 1);
	}
	
	/**
	 * An all-day event, lasting the specified number of hours.
	 */
	public static CalendarEvent newAllDayEvent(Date startTime, int days) {
		final CalendarEvent calendarEvent = new CalendarEvent(startTime, add(startTime, days, Calendar.DATE), true);
		calendarEvent.numDays = days;
		return calendarEvent;
	}
	
	private CalendarEvent(Date startTime) {
		this(startTime, add(startTime, 1, Calendar.HOUR), false);
	}

	private CalendarEvent(Date startTime, Date endTime, boolean allDayEvent) {
		this.startTime = startTime;
		this.endTime = endTime;
		this.allDayEvent = allDayEvent;
	}
	
	private static Date nextHour() {
		final Date now = new Date();
		
		final Calendar cal = Calendar.getInstance();
		cal.setTime(now);
		cal.clear(Calendar.MINUTE);
		cal.clear(Calendar.SECOND);
		cal.clear(Calendar.MILLISECOND);
		cal.roll(Calendar.HOUR_OF_DAY, true);
		
		return cal.getTime();
	}

	private static Date add(Date time, int amt, int field) {
		final Calendar cal = Calendar.getInstance();
		cal.setTime(time);
		cal.roll(field, amt);
		return cal.getTime();
	}
	
	public Date getStartTime() {
		return startTime;
	}
	public Date getEndTime() {
		return endTime;
	}
	/**
	 * Is this event an all day event or does it have a specific start
	 * and end time throughout the day?  
	 * 
	 * @return true if the event lasts all day (has no hour / minute start time)
	 * 	always false if the event is a multi-day event
	 */
	public boolean isAllDayEvent() {
		return allDayEvent;
	}
	
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (allDayEvent ? 1231 : 1237);
		result = prime * result + ((endTime == null) ? 0 : endTime.hashCode());
		result = prime * result
		+ ((startTime == null) ? 0 : startTime.hashCode());
		return result;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		CalendarEvent other = (CalendarEvent) obj;
		if (allDayEvent != other.allDayEvent)
			return false;
		if (endTime == null) {
			if (other.endTime != null)
				return false;
		} else if (!endTime.equals(other.endTime))
			return false;
		if (startTime == null) {
			if (other.startTime != null)
				return false;
		} else if (!startTime.equals(other.startTime))
			return false;
		return true;
	}

	static final Pattern ALL_DAY_REGEX = Pattern.compile("(\\S*) \\(([0-9]+) day[s]?\\)");
	static final Pattern INTRA_DAY_REGEX = Pattern.compile("(\\S+ \\S+) \\(([0-9]+) hour[s]?\\)");
	static final SimpleDateFormat DATE_PARSER = new SimpleDateFormat("yyyy-MM-dd");
	static final SimpleDateFormat DATE_TIME_PARSER = new SimpleDateFormat("yyyy-MM-dd HH:mm");
	
	/**
	 * Factory method.
	 */
	public static CalendarEvent fromString(String str) {
		final Matcher allDayMatcher = ALL_DAY_REGEX.matcher(str);
		if (allDayMatcher.matches()) {
			final String startStr = allDayMatcher.group(1);
			final String numDaysStr = allDayMatcher.group(2);
			try {
				final Date start = DATE_PARSER.parse(startStr);
				final int numDays = Integer.parseInt(numDaysStr);
				return CalendarEvent.newAllDayEvent(start, numDays);
			} catch (ParseException e) {
				return null;
			}
		}
		final Matcher intraDayMatcher = INTRA_DAY_REGEX.matcher(str);
		if (intraDayMatcher.matches()) {
			final String startStr = intraDayMatcher.group(1);
			final String numHoursStr = intraDayMatcher.group(2);
			try {
				final Date start = DATE_TIME_PARSER.parse(startStr);
				final int numHours = Integer.parseInt(numHoursStr);
				return CalendarEvent.newEvent(start, numHours);
			} catch (ParseException e) {
				return null;
			}
		}
		return null;
	}
	
	@Override
	public String toString() {
		if (allDayEvent) {
			return String.format("%1$tY-%1$tm-%1$td (%2$d day%3$s)", startTime, numDays, (numDays==1?"":"s"));
		} else {
			return String.format("%1$tY-%1$tm-%1$td %1$tH:%1$tM (%2$d hour%3$s)", startTime, numHours, (numHours==1?"":"s"));
		}
	}

	public static int typicalLength() {
		return 18;
	}

	@Override
	public int compareTo(CalendarEvent o) {
		final int start = getStartTime().compareTo(o.getStartTime());
		if (start != 0) { return start; }
		final int end = getEndTime().compareTo(o.getEndTime());
		return end;
	}
}

