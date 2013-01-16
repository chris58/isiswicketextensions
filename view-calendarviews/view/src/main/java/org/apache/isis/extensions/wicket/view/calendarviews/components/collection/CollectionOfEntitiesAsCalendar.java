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

import com.google.common.collect.Lists;
import com.google.inject.Inject;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import org.apache.isis.core.metamodel.adapter.ObjectAdapter;
import org.apache.isis.extensions.wicket.view.calendarviews.applib.CalendarEvent;
import org.apache.isis.extensions.wicket.view.calendarviews.applib.Calendarable;
import org.apache.isis.viewer.wicket.model.models.EntityCollectionModel;
import org.apache.isis.viewer.wicket.model.models.EntityModel;
import org.apache.isis.viewer.wicket.model.models.PageType;
import org.apache.isis.viewer.wicket.ui.pages.PageClassRegistry;
import org.apache.isis.viewer.wicket.ui.panels.PanelAbstract;
import org.apache.wicket.Page;
import org.apache.wicket.ResourceReference;
import org.apache.wicket.markup.html.CSSPackageResource;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.LoadableDetachableModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.joda.time.DateMidnight;
import org.joda.time.DateTime;
import org.wicketstuff.calendarviews.LargeView;
import org.wicketstuff.calendarviews.model.IEvent;
import org.wicketstuff.calendarviews.model.IEventProvider;
import org.wicketstuff.calendarviews.model.TimePeriod;
import org.apache.isis.core.metamodel.adapter.oid.OidMarshaller;
import org.apache.isis.core.metamodel.adapter.oid.RootOidDefault;
import org.apache.isis.viewer.wicket.model.util.Oids;

public class CollectionOfEntitiesAsCalendar extends PanelAbstract<EntityCollectionModel> {

    private static final long serialVersionUID = 1L;
    private static final String ID_CALENDAR_VIEW = "calendarView";
    public static final ResourceReference EXAMPLES_CSS_REFERENCE = new ResourceReference(CollectionOfEntitiesAsCalendar.class, "examples.css") {};
    /**
     * Injected by {@link #setPageClassRegistry(PageClassRegistry)}
     */
    private PageClassRegistry pageClassRegistry;

    public CollectionOfEntitiesAsCalendar(final String id,
            final EntityCollectionModel model) {
        super(id, model);
        buildGui();
    }

    static class CalendarableEvent implements IEvent, Comparable<CalendarableEvent> {

        private final CalendarEvent calendarEvent;
        private final String title;
        private PageParameters pageParameters;

        public CalendarableEvent(ObjectAdapter adapter) {
//        public CalendarableEvent(ObjectAdapter adapter, OidStringifier oidStringifier) {
            final Calendarable calendarable = (Calendarable) adapter.getObject();
            this.calendarEvent = calendarable.getCalendarEvent();
            title = adapter.titleString();
//            RootOidDefault.deStringEncoded(value);
//            pageParameters = EntityModel.createPageParameters(adapter, oidStringifier);
            pageParameters = EntityModel.createPageParameters(adapter);

        }

        @Override
        public String getTitle() {
            return title;
        }

        @Override
        public Date getStartTime() {
            return calendarEvent.getStartTime();
        }

        @Override
        public Date getEndTime() {
            return calendarEvent.getEndTime();
        }

        @Override
        public boolean isAllDayEvent() {
            return calendarEvent.isAllDayEvent();
        }

        @Override
        public int compareTo(CalendarableEvent o) {
            return calendarEvent.compareTo(o.calendarEvent);
        }

        public PageParameters getPageParameters() {
            return pageParameters;
        }
    }

    private void buildGui() {


        final List<CalendarableEvent> allEvents = Lists.newArrayList();
        EntityCollectionModel model = getModel();
        List<ObjectAdapter> adapterList = model.getObject();
        for (ObjectAdapter adapter : adapterList) {
//            allEvents.add(new CalendarableEvent(adapter, getOidStringifier()));
            allEvents.add(new CalendarableEvent(adapter));
        }
        Collections.sort(allEvents);

        class CalendarableEventProvider extends LoadableDetachableModel<Collection<? extends IEvent>> implements IEventProvider {

            private static final long serialVersionUID = 1L;

            @Override
            protected Collection<? extends IEvent> load() {
                return allEvents;
            }

            @Override
            public void initializeWithDateRange(final Date start, final Date end) {
                // nothing to do
            }
        }

        TimePeriod tp = determineTimePeriod(allEvents);
        final IEventProvider eventProvider = new CalendarableEventProvider();

        add(new CalendarView(ID_CALENDAR_VIEW, tp, eventProvider) {
            private static final long serialVersionUID = 1L;

            @Override
            protected WebMarkupContainer createEventLink(String id, final IModel<IEvent> model) {

                final CalendarableEvent calendarEvent = (CalendarableEvent) model.getObject();

                WebMarkupContainer wmc = new WebMarkupContainer(id);

                final Class<? extends Page> pageClass = getPageClassRegistry().getPageClass(PageType.ENTITY);
                final PageParameters pageParameters = calendarEvent.getPageParameters();

                final Link<?> link = new Link<String>("link") {
                    private static final long serialVersionUID = 1L;

                    @Override
                    public void onClick() {
                        setResponsePage(pageClass, pageParameters);
                    }
                };
                wmc.add(link);
                link.add(createStartTimeLabel("startTime", model));
                link.add(new Label("title", Model.of(calendarEvent.getTitle())));

                return wmc;
            }

            /**
             * Borrowed from LargeView...
             */
            private Label createStartTimeLabel(String id, final IModel<IEvent> model) {
                return new Label(id, new LoadableDetachableModel<String>() {
                    private static final long serialVersionUID = 1L;

                    @Override
                    protected String load() {
                        // TODO : make this implementation more internationalized... this one is too static
                        //			use dateformat or something
                        DateTime start = new DateTime(model.getObject().getStartTime());
                        StringBuffer sb = new StringBuffer();
                        int hr = start.getHourOfDay();
                        sb.append(hr > 12 ? hr - 12 : hr);
                        int min = start.getMinuteOfHour();
                        if (min != 0) {
                            sb.append(':');
                            if (min < 0) {
                                sb.append('0');
                            }
                            sb.append(min);
                        }
                        sb.append(hr > 12 ? 'p' : 'a');
                        return sb.toString();
                    }
                });
            }

            @Override
            protected Page createMoreDetailPage(IModel<DateMidnight> model, IModel<List<IEvent>> eventsModel) {
                Page page = super.createMoreDetailPage(model, eventsModel);
                page.add(CSSPackageResource.getHeaderContribution(EXAMPLES_CSS_REFERENCE));
                return page;
            }
        });
    }

    private TimePeriod determineTimePeriod(final List<CalendarableEvent> allEvents) {
        TimePeriod tp;
        switch (allEvents.size()) {
            case 0:
                tp = LargeView.createWeeksViewDates(4);
                break;
            default:
                Date endTime = allEvents.get(0).getEndTime();
                Date startTime = new DateTime(endTime).plusWeeks(-4).toDate();
                tp = new TimePeriod(startTime, endTime);
                break;
        }
        return tp;
    }

    //////////////////////////////////////////////////
    // Dependency Injection
    //////////////////////////////////////////////////
    protected PageClassRegistry getPageClassRegistry() {
        return pageClassRegistry;
    }

    @Inject
    public void setPageClassRegistry(PageClassRegistry pageClassRegistry) {
        this.pageClassRegistry = pageClassRegistry;
    }
}
