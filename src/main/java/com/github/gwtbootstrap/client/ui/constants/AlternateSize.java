/*
 *  Copyright 2012 GWT-Bootstrap
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */
package com.github.gwtbootstrap.client.ui.constants;

import com.github.gwtbootstrap.client.ui.base.Style;


/**
 * Type of an input element's Alternate size
 * 
 * @since 2.0.4.0
 * 
 * @author ohashi keisuke
 */
public enum AlternateSize implements Style {
	MINI("input-mini"),
	SMALL("input-small"),
	MEDIUM("input-medium"),
	LARGE("input-large"),
	XLARGE("input-xlarge"),
	XXLARGE("input-xxlarge");

	private AlternateSize(String styleName) {
		this.styleName = styleName;
	}
	
	private final String styleName;
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public String get() {
		return styleName;
	}

}
