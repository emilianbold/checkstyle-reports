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

/*
 * ChartFactory.java
 *
 * Created on October 9, 2007, 12:50 AM
 *
 */
package ro.emilianbold.reports.checkstyle;

import java.io.File;
import java.io.IOException;
import javax.swing.JComponent;
import javax.swing.JTabbedPane;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParserFactory;
import org.xml.sax.SAXException;
import ro.emilianbold.reports.checkstyle.handler.AbstractGeneralHandler;
import ro.emilianbold.reports.checkstyle.handler.CategoryGeneralHandler;
import ro.emilianbold.reports.checkstyle.handler.FileGeneralHandler;
import ro.emilianbold.reports.checkstyle.handler.GeneralHandler;
import ro.emilianbold.reports.checkstyle.handler.ProxyHandler;

/**
 * Generate the charts based on the checkstyle-result file.
 *
 * @author Emilian Bold
 */
public final class ChartGenerator {
    
    public static JComponent makeChart(String s){
        final ProxyHandler h = new ProxyHandler(new GeneralHandler(),
                new FileGeneralHandler(), new CategoryGeneralHandler());
        try {
            SAXParserFactory.newInstance().newSAXParser().parse(new File(s), h);
        } catch (IOException ex) {
            ex.printStackTrace();
        } catch (ParserConfigurationException ex) {
            ex.printStackTrace();
        } catch (SAXException ex) {
            ex.printStackTrace();
        }
        
        JTabbedPane p=new JTabbedPane();
        for(AbstractGeneralHandler ag: h.getHandlers()){
            p.addTab(ag.getDisplayName(), ag.getJComponent());
        }
        return p;
    }
    
    
}
