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


package com.cooldatasoft.horizontal.dropdown.chrome;

import com.cooldatasoft.common.DestinationType;
import com.cooldatasoft.common.MenuItem;
import java.util.List;
import org.apache.wicket.AttributeModifier;
import org.apache.wicket.ResourceReference;
import org.apache.wicket.markup.html.IHeaderContributor;
import org.apache.wicket.markup.html.IHeaderResponse;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.markup.html.resources.CompressedResourceReference;
import org.apache.wicket.model.Model;


@SuppressWarnings("unchecked")
public class ChromeDropDownMenu extends Panel implements IHeaderContributor {

	private static final long serialVersionUID = 1L;

	private int numberOfMenu;

	private ResourceReference SHORTCUTS_JAVASCRIPT = null;
	private ResourceReference SHORTCUTS_CSS = null;
	private final class IdAttributeModifier extends ListView {
		private static final long serialVersionUID = 1L;
		int itemCount = 0;

		private IdAttributeModifier(String id, List list) {
			super(id, list);
		}

		@Override
		public void populateItem(final ListItem item) {
			MenuItem menuItem = (MenuItem) item.getModelObject();
			List<MenuItem> subMenuList = menuItem.getSubMenuItemList();
				
			
			WebMarkupContainer submenuDiv = new WebMarkupContainer("submenuDiv");
			submenuDiv.add(new AttributeModifier("id", true, new Model("dropmenu" + itemCount)));
			
			ListView submenuItem = new ListView("submenuItem",subMenuList) {
				private static final long serialVersionUID = 1L;

				public void populateItem(final ListItem item) {
					
					final MenuItem subMenuItem = (MenuItem) item.getModelObject();
					
					Link link;
					if (subMenuItem != null && subMenuItem.getLink() != null) {
						link = subMenuItem.getLink();
					} else {
						link = new Link("menuLink") {
							private static final long serialVersionUID = 1L;

							@Override
							public void onClick() {
								if(subMenuItem != null ){
									processResponse(subMenuItem);								
								}								
							}
						};
						Label linkText = new Label("linkText", subMenuItem.getMenuText());
						linkText.setRenderBodyOnly(true);
						link.add(linkText);
					}
					item.add(link);
					item.setRenderBodyOnly(true);

					Label disabledLinkText = new Label("disabledLinkText", subMenuItem.getMenuText());
					item.add(disabledLinkText);

					boolean menuEnabled = subMenuItem.isEnabled();
					link.setVisible(menuEnabled);
					disabledLinkText.setVisibilityAllowed(!menuEnabled);
				}
			};

			submenuDiv.add(submenuItem);
			itemCount++;
			item.add(submenuDiv);
			item.setRenderBodyOnly(true);
			
		}
	}

	public enum CSS{THEME1,THEME2,THEME3,THEME4};
	
	private void processResponse(MenuItem menuItem){
		if (menuItem.getDestinationType() == DestinationType.EXTERNAL_LINK) {									
			//
		} else if (menuItem.getDestinationType() == DestinationType.LINK) {									
			//
		} else if (menuItem.getDestinationType() == DestinationType.WEB_PAGE_CLASS) {
			setResponsePage(menuItem.getResponsePageClass());
		} else if (menuItem.getDestinationType() == DestinationType.WEB_PAGE_INSTANCE) {
			setResponsePage(menuItem.getResponsePage());
		} else if (menuItem.getDestinationType() == DestinationType.NONE) {
			//
		}
	}
	public ChromeDropDownMenu(String id, List<MenuItem> menuItemList ) {
		this(id, menuItemList, CSS.THEME1);
	}
	/**
	 * http://www.dynamicdrive.com/dynamicindex1/chrome/index.htm
	 * 
	 * First element of each list is assumed to be the top menu
	 * Use ChromeMenu.CSS.THEME1-4 for different css themes
	 * 
	 * @param id
	 * @param menuListOfLinkList
	 */
	public ChromeDropDownMenu(String id, List<MenuItem> menuItemList, CSS cssTheme ) {
		super(id);
		
		SHORTCUTS_JAVASCRIPT = new CompressedResourceReference(ChromeDropDownMenu.class,"js/chrome.js");
		
		if(cssTheme == CSS.THEME1){
			SHORTCUTS_CSS = new CompressedResourceReference(ChromeDropDownMenu.class,"css/chrome1.css");
		}else if(cssTheme == CSS.THEME2){
			SHORTCUTS_CSS = new CompressedResourceReference(ChromeDropDownMenu.class,"css/chrome2.css");
		}else if(cssTheme == CSS.THEME3){
			SHORTCUTS_CSS = new CompressedResourceReference(ChromeDropDownMenu.class,"css/chrome3.css");
		}else if(cssTheme == CSS.THEME4){
			SHORTCUTS_CSS = new CompressedResourceReference(ChromeDropDownMenu.class,"css/chrome4.css");
		}
		
		ListView chromePrimaryMenuListView = new ListView("primaryMenuList", menuItemList) {
			private static final long serialVersionUID = 1L;
			int itemCount = 0;

			public void populateItem(final ListItem item) {
				
				final MenuItem menuItem = ((MenuItem) item.getModelObject());
				Link link;
				if (menuItem != null && menuItem.getLink() != null) {
					link = menuItem.getLink();
				} else {
					link = new Link("menuLink") {
						private static final long serialVersionUID = 1L;

						@Override
						public void onClick() {
							if (menuItem!=null ) {
								processResponse(menuItem);
							}
						}
					};
				}

				// Adding submenu to menu item
				link.add(new AttributeModifier("rel", true, new Model("dropmenu" + itemCount)));
				setNumberOfMenu(itemCount++);

				Label linkText = new Label("linkText", menuItem.getMenuText());
				linkText.setRenderBodyOnly(true);
				link.add(linkText);
				item.add(link);
			}
		};
		chromePrimaryMenuListView.setReuseItems(true);
		add(chromePrimaryMenuListView);

		ListView submenuListView = new IdAttributeModifier("submenuList", menuItemList);
		submenuListView.setReuseItems(true);
		add(submenuListView);
		setRenderBodyOnly(true);
	}


	public int getNumberOfMenu() {
		return numberOfMenu;
	}


	public void setNumberOfMenu(int numberOfMenu) {
		this.numberOfMenu = numberOfMenu;
	}
	
	//@Override
	public void renderHead(IHeaderResponse response) {
        response.renderJavascriptReference(SHORTCUTS_JAVASCRIPT);
        response.renderCSSReference(SHORTCUTS_CSS);
	}

}
