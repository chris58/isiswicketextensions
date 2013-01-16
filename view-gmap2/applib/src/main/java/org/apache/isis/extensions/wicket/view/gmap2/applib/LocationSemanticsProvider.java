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


package org.apache.isis.extensions.wicket.view.gmap2.applib;

import org.apache.isis.applib.adapters.DefaultsProvider;
import org.apache.isis.applib.adapters.EncoderDecoder;
import org.apache.isis.applib.adapters.Parser;
import org.apache.isis.applib.adapters.ValueSemanticsProvider;
import org.apache.isis.applib.profiles.Localization;

/**
 * For internal use; allows Isis to parse etc.
 */
public class LocationSemanticsProvider implements ValueSemanticsProvider<Location> {

	@Override
	public DefaultsProvider<Location> getDefaultsProvider() {
		return new DefaultsProvider<Location>() {

			@Override
			public Location getDefaultValue() {
				return Location.DEFAULT_VALUE;
			}
		};
	}

	@Override
	public EncoderDecoder<Location> getEncoderDecoder() {
		return new EncoderDecoder<Location>() {

			@Override
			public Location fromEncodedString(String encodedString) {
				return Location.fromString(encodedString);
			}

			@Override
			public String toEncodedString(Location locationToEncode) {
				return locationToEncode.toString();
			}
		};
	}

	@Override
	public Parser<Location> getParser() {
		return new Parser<Location>() {

			@Override
			public String displayTitleOf(Location location, String usingMask) {
				return location.toString();
			}
			@Override
			public String parseableTitleOf(Location existing) {
				return existing.toString();
			}

			@Override
			public int typicalLength() {
				return Location.typicalLength();
			}

                @Override
                public Location parseTextEntry(Object o, String string, Localization lclztn) {
                    return Location.fromString(string);
                }

                @Override
                public String displayTitleOf(Location t, Localization lclztn) {
                    return t.toString();
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
