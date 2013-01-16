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


package org.apache.isis.extensions.wicket.view.cooldatasoftmenu.components.appactions;

import com.cooldatasoft.common.MenuItem;
import com.cooldatasoft.horizontal.dropdown.chrome.ChromeDropDownMenu;
import java.util.ArrayList;
import java.util.List;
import org.apache.isis.core.commons.authentication.AuthenticationSession;
import org.apache.isis.core.metamodel.adapter.ObjectAdapter;
import org.apache.isis.core.metamodel.consent.Consent;
import org.apache.isis.core.metamodel.facets.named.NamedFacet;
import org.apache.isis.core.metamodel.spec.ObjectSpecification;
import org.apache.isis.core.metamodel.spec.feature.ObjectAction;
import org.apache.isis.core.runtime.system.context.IsisContext;
import org.apache.isis.core.runtime.system.persistence.PersistenceSession;
import org.apache.isis.extensions.wicket.viewer.WicketObjectsApplication;
import org.apache.isis.metamodel.adapter.oid.stringable.OidStringifier;
import org.apache.isis.metamodel.spec.feature.ObjectActionType;
import org.apache.isis.viewer.wicket.model.models.ActionModel;
import org.apache.isis.viewer.wicket.model.models.ApplicationActionsModel;
import org.apache.isis.viewer.wicket.model.models.PageType;
import org.apache.isis.viewer.wicket.viewer.integration.wicket.AuthenticatedWebSessionForIsis;
import org.apache.wicket.Page;
import org.apache.wicket.PageParameters;
import org.apache.wicket.markup.html.link.BookmarkablePageLink;
import org.apache.wicket.markup.html.link.Link;

public class ApplicationActionsJsCoolDataMenu extends ChromeDropDownMenu {

	private static final long serialVersionUID = 1L;

	private static final String ID_MENU_LINK = "menuLink";

	public ApplicationActionsJsCoolDataMenu(String id, ApplicationActionsModel model) {
		super(id, buildMenu(model));
	}

	private static List<MenuItem> buildMenu(
			ApplicationActionsModel applicationActionsModel) {

		List<ObjectAdapter> services = applicationActionsModel.getObject();

		List<MenuItem> serviceMenuItems = new ArrayList<MenuItem>();

		List<ObjectAdapter> serviceAdapters = services;
		for (ObjectAdapter serviceAdapter : serviceAdapters) {
			ObjectSpecification serviceSpec = serviceAdapter
					.getSpecification();
			String serviceName = serviceSpec.getFacet(NamedFacet.class).value();
			MenuItem serviceMenuItem = new MenuItem(serviceName);

			addActionSubMenuItems(serviceAdapter, serviceMenuItem);
			if (serviceMenuItem.getSubMenuItemList().size() > 0) {
				serviceMenuItems.add(serviceMenuItem);
			}
		}

		return serviceMenuItems;
	}

	private static void addActionSubMenuItems(final ObjectAdapter serviceAdapter,
			MenuItem serviceMenuItem) {

		ObjectSpecification serviceSpec = serviceAdapter
				.getSpecification();

		for (final ObjectAction noAction : serviceSpec
				.getObjectActions(ObjectActionType.USER)) {

			AuthenticationSession session = getAuthenticationSession();
			Consent visibility = noAction.isVisible(session, serviceAdapter);
			if (visibility.isVetoed()) {
				continue;
			}

			MenuItem actionSubMenuItem = buildSubMenuItem(serviceAdapter,
					noAction);
			serviceMenuItem.getSubMenuItemList().add(actionSubMenuItem);
		}
	}

	private static MenuItem buildSubMenuItem(final ObjectAdapter serviceAdapter,
			final ObjectAction noAction) {

		PageParameters pageParameters = ActionModel.createPageParameters(
				serviceAdapter, noAction, getOidStringifier(), null, ActionModel.SingleResultsMode.REDIRECT);
		String actionLabel = buildActionLabel(noAction);

		AuthenticationSession session = getAuthenticationSession();

		MenuItem menuItem;
		if (noAction.isUsable(session, serviceAdapter).isAllowed()) {
			Link<?> link = (Link<?>) buildLink(noAction, pageParameters);
			menuItem = new MenuItem(actionLabel, link);
		} else {
			menuItem = new MenuItem(actionLabel);
			menuItem.setEnabled(false);
		}
		menuItem.setMenuText(actionLabel);
		return menuItem;
	}

	private static String buildActionLabel(final ObjectAction noAction) {
		String actionName = noAction.getFacet(NamedFacet.class).value();
		String actionLabel = actionName;
		if (noAction.getParameterCount() > 0) {
			actionLabel += "...";
		}
		return actionLabel;
	}

	private static Link<?> buildLink(final ObjectAction noAction,
			PageParameters pageParameters) {

		Class<? extends Page> pageClass = WicketObjectsApplication.get().getPageClassRegistry().getPageClass(PageType.ACTION);
		return createLink(ID_MENU_LINK, pageParameters, pageClass);
	}

	private static <T extends Page> Link<T> createLink(final String linkId, PageParameters pageParameters,
			Class<T> pageClass) {
		return new BookmarkablePageLink<T>(linkId,
				pageClass, pageParameters);
	}
	
	
	///////////////////////////////////
	
	protected static PersistenceSession getPersistenceSession() {
		return IsisContext.getPersistenceSession();
	}

	protected static AuthenticationSession getAuthenticationSession() {
		return AuthenticatedWebSessionForIsis.get()
				.getAuthenticationSession();
	}

	protected static OidStringifier getOidStringifier() {
		return getPersistenceSession().getOidGenerator().getOidStringifier();
	}

}
