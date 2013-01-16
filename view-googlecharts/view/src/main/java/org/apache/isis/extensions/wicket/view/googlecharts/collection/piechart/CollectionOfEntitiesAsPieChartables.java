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


package org.apache.isis.extensions.wicket.view.googlecharts.collection.piechart;

import java.awt.Dimension;
import java.util.List;
import org.apache.isis.core.metamodel.adapter.ObjectAdapter;

import org.apache.isis.extensions.wicket.view.googlecharts.applib.PieChartable;
import org.apache.isis.viewer.wicket.model.models.EntityCollectionModel;
import org.apache.isis.viewer.wicket.ui.panels.PanelAbstract;
import org.wicketstuff.googlecharts.AbstractChartData;
import org.wicketstuff.googlecharts.Chart;
import org.wicketstuff.googlecharts.ChartDataEncoding;
import org.wicketstuff.googlecharts.ChartProvider;
import org.wicketstuff.googlecharts.ChartType;
import org.wicketstuff.googlecharts.IChartData;

public class CollectionOfEntitiesAsPieChartables extends
		PanelAbstract<EntityCollectionModel> {

	private static final long serialVersionUID = 1L;
	
	private static final String ID_PIE_CHART = "pieChart";

	
	public CollectionOfEntitiesAsPieChartables(final String id,
			final EntityCollectionModel model) {
		super(id, model);
		buildGui();
	}

	private void buildGui() {
		final EntityCollectionModel model = getModel();
		final List<ObjectAdapter> adapterList = model.getObject();
		
        IChartData data;
        final int size = adapterList.size();
		final double[] values = new double[size];
        final String[] labels = new String[size];

        int i=0;
        double maxValue = Double.MIN_VALUE;
		for (ObjectAdapter adapter : adapterList) {
			final PieChartable pieChartable = (PieChartable) adapter.getObject();
			final double value = pieChartable.getPieChartValue();
			values[i] = value;
			labels[i] = pieChartable.getPieChartLabel();
			maxValue = Math.max(maxValue, value);
			i++;
		}
		data = new AbstractChartData(ChartDataEncoding.SIMPLE, maxValue) {

			private static final long serialVersionUID = 1L;

			public double[][] getData() {
				return new double[][]{values};
            }
        };

        ChartProvider provider = new ChartProvider(new Dimension(500, 200), ChartType.PIE_3D, data);
		provider.setPieLabels(labels);

        add(new Chart(ID_PIE_CHART, provider));
	}
}

