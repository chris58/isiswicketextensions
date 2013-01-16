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


package com.cooldatasoft.common;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.link.Link;

public class MenuItem implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private DestinationType destinationType;
	private String menuText;
	private Class<? extends WebPage> responsePageClass;
	private WebPage responsePage;
	private String externalLink;
	
	private Link<?> link;
	
	private List<MenuItem> subMenuItemList = new ArrayList<MenuItem>();
	private boolean seperator = false;
	private boolean submenuTitle = false;
	private boolean enabled = true;
	
	public MenuItem(boolean seperator){
		setSeperator(true);
		setDestinationType(DestinationType.NONE);
	}
	public MenuItem(String submenuTitle){
		setSubmenuTitleTitle(true);
		setMenuText(submenuTitle);
		setDestinationType(DestinationType.NONE);
	}
	public <T extends WebPage>MenuItem(String menuText, T destinationPage) {
		setMenuText(menuText);
		setResponsePage(destinationPage);
		setSubMenuItemList(new ArrayList<MenuItem>());
		setDestinationType(DestinationType.WEB_PAGE_INSTANCE);
	}
	public MenuItem(String menuText, Class<? extends WebPage> destinationPageClass) {
		setMenuText(menuText);
		setResponsePageClass(destinationPageClass);
		setSubMenuItemList(new ArrayList<MenuItem>());
		setDestinationType(DestinationType.WEB_PAGE_CLASS);
	}
	public MenuItem(String menuText, Link<?> link) {
		setMenuText(menuText);
		setSubMenuItemList(new ArrayList<MenuItem>());
		setDestinationType(DestinationType.LINK);
		setLink(link);
		Label linkText = new Label("linkText", this.getMenuText());
		linkText.setRenderBodyOnly(true);
		link.add(linkText);
	}
	public MenuItem(String menuText, Class<? extends WebPage> destinationWebPage,List<MenuItem> subMenuItemList) throws InstantiationException, IllegalAccessException {
		this(menuText,destinationWebPage.newInstance(),subMenuItemList);
		setDestinationType(DestinationType.WEB_PAGE_CLASS);
	}
	public <T extends WebPage>MenuItem(String menuText, T destinationPage,List<MenuItem> subMenuItemList) {
		setMenuText(menuText);
		setResponsePage(destinationPage);
		setSubMenuItemList(subMenuItemList);
		setDestinationType(DestinationType.WEB_PAGE_INSTANCE);
	}
	
	
	public static MenuItem getMenuSeperator(){
		return new MenuItem(true);
	}
	
	public String getMenuText() {
		return menuText;
	}
	public void setMenuText(String text) {
		this.menuText = text;
	}
	public WebPage getResponsePage() {
		return responsePage;
	}
	public <T extends WebPage> void setResponsePage(T destinationPage) {
		this.responsePage = destinationPage;
	}
	public void addSubmenu(MenuItem subMenuItem){
		getSubMenuItemList().add(subMenuItem);
	}
	public List<MenuItem> getSubMenuItemList() {
		return subMenuItemList;
	}
	public void setSubMenuItemList(List<MenuItem> subMenuItemList) {
		this.subMenuItemList = subMenuItemList;
	}
	public boolean isSeperator() {
		return seperator;
	}
	public void setSeperator(boolean seperator) {
		this.seperator = seperator;
	}
	public boolean isSubmenuTitle() {
		return submenuTitle;
	}
	public void setSubmenuTitleTitle(boolean title) {
		this.submenuTitle = title;
	}
	public DestinationType getDestinationType() {
		return destinationType;
	}
	public void setDestinationType(DestinationType destinationType) {
		this.destinationType = destinationType;
	}
	public Class<? extends WebPage> getResponsePageClass() {
		return responsePageClass;
	}
	public void setResponsePageClass(
			Class<? extends WebPage> destinationPageClass) {
		this.responsePageClass = destinationPageClass;
	}
	public String getExternalLink() {
		return externalLink;
	}
	public void setExternalLink(String externalLink) {
		this.externalLink = externalLink;
	}
	public Link<?> getLink() {
		return link;
	}
	public void setLink(Link<?> link) {
		this.link = link;
	}

	public boolean isEnabled() {
		return enabled;
	}
	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}
}
