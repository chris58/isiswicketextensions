/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package dom.todo;

import org.apache.isis.applib.annotation.Title;
import org.apache.isis.extensions.wicket.view.googlecharts.applib.PieChartable;

public class Pie implements PieChartable {

    double value;
    String label;

    public Pie(String label, double value) {
        this.label = label;
        this.value = value;
    }

    @Override
    public double getPieChartValue() {
        return value;
    }

    @Override
    @Title
    public String getPieChartLabel() {
        return label;
    }
}
