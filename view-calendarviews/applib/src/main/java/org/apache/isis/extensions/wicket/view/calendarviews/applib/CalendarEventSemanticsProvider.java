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

import org.apache.isis.applib.adapters.DefaultsProvider;
import org.apache.isis.applib.adapters.EncoderDecoder;
import org.apache.isis.applib.adapters.Parser;
import org.apache.isis.applib.adapters.ValueSemanticsProvider;
import org.apache.isis.applib.profiles.Localization;

public class CalendarEventSemanticsProvider implements ValueSemanticsProvider<CalendarEvent> {

    @Override
    public DefaultsProvider<CalendarEvent> getDefaultsProvider() {
        return new DefaultsProvider<CalendarEvent>() {
            @Override
            public CalendarEvent getDefaultValue() {
                return CalendarEvent.newEvent();
            }
        };
    }

    @Override
    public EncoderDecoder<CalendarEvent> getEncoderDecoder() {
        return new EncoderDecoder<CalendarEvent>() {
            @Override
            public CalendarEvent fromEncodedString(String encodedString) {
                return CalendarEvent.fromString(encodedString);
            }

            @Override
            public String toEncodedString(CalendarEvent toEncode) {
                return toEncode.toString();
            }
        };
    }

    @Override
    public Parser<CalendarEvent> getParser() {
        return new Parser<CalendarEvent>() {
            @Override
            public String displayTitleOf(CalendarEvent t, Localization lclztn) {
                return t.toString();
            }

            @Override
            public String displayTitleOf(CalendarEvent object, String usingMask) {
                return object.toString();
            }

            @Override
            public CalendarEvent parseTextEntry(Object o, String string, Localization lclztn) {
                return CalendarEvent.fromString(string);
            }

            @Override
            public String parseableTitleOf(CalendarEvent existing) {
                return existing.toString();
            }

            @Override
            public int typicalLength() {
                return CalendarEvent.typicalLength();
            }
        };
    }

    @Override
    public boolean isEqualByContent() {
        return true;
    }

    @Override
    public boolean isImmutable() {
        return true;
    }
}
