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


package org.apache.isis.extensions.wicket.view.calendarviews.components.collection;

import org.apache.isis.core.metamodel.spec.ObjectSpecification;
import org.apache.isis.core.runtime.system.context.IsisContext;
import org.apache.isis.extensions.wicket.view.calendarviews.applib.Calendarable;
import org.apache.isis.viewer.wicket.model.models.EntityCollectionModel;
import org.apache.isis.viewer.wicket.ui.ComponentFactoryAbstract;
import org.apache.isis.viewer.wicket.ui.ComponentType;
import org.apache.wicket.Component;
import org.apache.wicket.model.IModel;

public class CollectionOfEntitiesAsCalendarFactory extends ComponentFactoryAbstract {

	private static final long serialVersionUID = 1L;

	private static final String NAME = "calendar";

	public CollectionOfEntitiesAsCalendarFactory() {
		super(ComponentType.COLLECTION_CONTENTS, NAME);
	}

	@Override
	public ApplicationAdvice appliesTo(IModel<?> model) {
		if (!(model instanceof EntityCollectionModel)) {
			return ApplicationAdvice.DOES_NOT_APPLY;
		}
		
		EntityCollectionModel entityCollectionModel = (EntityCollectionModel) model;
		if (entityCollectionModel.hasSelectionHandler()) {
			return ApplicationAdvice.DOES_NOT_APPLY;
		}
		
		ObjectSpecification typeOfSpec = entityCollectionModel.getTypeOfSpecification();
		ObjectSpecification calendarableSpec = IsisContext.getSpecificationLoader().loadSpecification(Calendarable.class);
		return appliesIf(typeOfSpec.isOfType(calendarableSpec));
	}

	public Component createComponent(String id, IModel<?> model) {
		EntityCollectionModel collectionModel = (EntityCollectionModel) model;
		return new CollectionOfEntitiesAsCalendar(id, collectionModel);
	}

}
