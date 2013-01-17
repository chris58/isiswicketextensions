package app;

import com.google.inject.Singleton;
import org.apache.isis.extensions.wicket.view.googlecharts.collection.piechart.CollectionOfEntitiesAsPieChartablesFactory;
import org.apache.isis.viewer.wicket.viewer.registries.components.ComponentFactoryRegistrarDefault;

@Singleton
public class ComponentFactoryRegistrarForQuickStart extends ComponentFactoryRegistrarDefault {

    @Override
    public void addComponentFactories(ComponentFactoryList componentFactories) {
        super.addComponentFactories(componentFactories);
        componentFactories.add(new CollectionOfEntitiesAsPieChartablesFactory());
        // currently no replacements
    }
}
