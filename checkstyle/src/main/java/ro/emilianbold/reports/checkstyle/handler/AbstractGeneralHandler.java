/** This file is part of Checkstyle Reports.
 *
 * Checkstyle Reports is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License.
 *
 * Checkstyle Reports is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with Foobar.  If not, see <http://www.gnu.org/licenses/>.
 */
package ro.emilianbold.reports.checkstyle.handler;

import java.awt.BorderLayout;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.data.DefaultKeyedValues;
import org.jfree.data.general.DefaultPieDataset;
import org.jfree.data.general.PieDataset;
import org.xml.sax.helpers.DefaultHandler;

/**
 * A SAX-handler that will generate a piechart. It groups errors based on one key
 * during the start/endElement callbacks.
 *
 * @author Emilian Bold
 */
public abstract class AbstractGeneralHandler extends DefaultHandler {
    // a map of keys and integer. The integer part defines how large it will be
    // in the chart
    protected Map<String, Integer> map = new HashMap<String, Integer>();
    // a map from key to display-name
    protected Map<String, String> def = new HashMap<String, String>();
    // ordered key list, depending on the size in the map.
    protected List<String> list = new ArrayList<String>();
    // jfreechart dataset, cached here so we can change it later via GUI
    // callbacks
    protected DefaultPieDataset dataSet;
    
    /**
     * @return The component that will display this chart with all the 
     * auxiliary items (like export button, scale, etc). So far only a slide
     * bar to limit the number of items.
     */
    public JComponent getJComponent(){
        JPanel p = new JPanel(new BorderLayout());
        p.add(new ChartPanel(ChartFactory.createPieChart(getDisplayName(), getDataSet(),false,false,false)),
                BorderLayout.CENTER);
        final JSlider slide = new JSlider(0,getSize()-1,getSize()-1);
        slide.addChangeListener(new ChangeListener() {
            public void stateChanged(ChangeEvent changeEvent) {                
                AbstractGeneralHandler.this.setSize(slide.getValue());
            }
        });
        p.add(slide, BorderLayout.SOUTH);
        return p;
    }
    
    /**
     * @return The number of categories in the piechart (ie. keys in the map)
     */
    public int getSize(){
        return map.size();
    }
    
    /**
     * Limits the categories in the piechart to a given number. This will
     * refresh the GUI chart. The chart keys are ordered based on size so all
     * the relevant info will show up first while the rest later (for a larger
     * size).
     *
     * @param s The new size (must not be larger than #getSize()).
     */
    public void setSize(int s){
        while (dataSet.getItemCount()>0){
            dataSet.remove((Comparable) dataSet.getKeys().get(0));
        }
        for (int i = 0; i<s; i++){
            dataSet.setValue(def.get(list.get(i)),map.get(list.get(i)));
        }
    }
    
    /**
     * @return The dataset that will drive the chart. This will be cached
     * locally in order to be able to refresh the chart (jfreechart listens
     * for changes).
     */
    protected PieDataset getDataSet(){
        DefaultKeyedValues val = new DefaultKeyedValues();
        for(String k : map.keySet()){
            list.add(k);
            val.addValue(def.get(k),map.get(k));
        }
        
        Collections.sort(list, new Comparator<String>(){
            public int compare(String a, String b) {
                return map.get(b).compareTo(map.get(a));
            }
            
        });
        for(String k : list){
            val.addValue(def.get(k),map.get(k));
        }
        
        dataSet = new DefaultPieDataset(val);
        return dataSet;
    }

    /**
     * @return The user-visible name for this report.
     */
    public abstract String getDisplayName();
    
}