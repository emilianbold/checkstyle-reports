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

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;


/**
 * Generate chart using the keys the error type and counting each instance,
 * no matter the source file.
 *
 * @author Emilian Bold
 */
public class GeneralHandler extends AbstractGeneralHandler {
    
    /**
     * DefaultHandler callback, used to count each checkstyle error.
     */
    public void startElement(String name, String string0, String string1, Attributes attributes) throws SAXException {
        //System.out.println("Start "+name+" 2"+string0+" 3"+string1);
        if (string1.trim().equals("error")){
            String s = String.valueOf(
                    attributes.getValue("source"));
            
            //System.out.println("S:"+s);
            
            Integer i = map.get(s);
            if(i==null){
                i=1;
            }else{
                i=i+1;
            }
            map.put(s,i);
            
            
            if (def.get(s) == null){
                def.put(s, String.valueOf(attributes.getValue("message")));
            }
            
        }
    }
    
    public String getDisplayName() {
        return "General";
    }
}