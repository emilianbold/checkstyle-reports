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
 * Groups checkstyle erors by "category" like javadoc/programming error, etc.
 * Easy to point out if it's mostly a documentation issue or a programming one.
 *
 * <b>Note</b>: The current definition of "category" is the 2nd last string
 * in the package name of the checkstyle "check". It's quite raw...
 * 
 * @author Emilian Bold
 */
public class CategoryGeneralHandler extends AbstractGeneralHandler {
    
    /**
     * DefaultHandler callback. Groups the erros by the "category" (see definition
     * above).
     */
    public void startElement(String name, String string0, String string1, Attributes attributes) throws SAXException {
        //System.out.println("Start "+name+" 2"+string0+" 3"+string1);
        if (string1.trim().equals("error")){
            String source = String.valueOf(
                        attributes.getValue("source"));
            String[] pieces = source.split("\\.");
            if(pieces.length<2){
                return;
            }
            
            String s = pieces[pieces.length-2];
            
            //System.out.println("S:"+s);
            
            Integer i = map.get(s);
            if(i==null){
                i=1;
            }else{
                i++;
            }
            map.put(s,i);
            
            if (def.get(s) == null){
                def.put(s, s);
            }
        }
    }

    public String getDisplayName() {
        return "Per category";
    }
}