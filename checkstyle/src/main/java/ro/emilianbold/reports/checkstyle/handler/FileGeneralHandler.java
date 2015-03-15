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

import java.util.HashMap;
import java.util.Map;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;

/**
 * Groups the checkstyle errors per-file, ie. each error is counted only once
 * per file. The point it to see what error shows up in most of the files,
 * no matter how many times per file.
 *
 * @author Emilian Bold
 */
public class FileGeneralHandler extends AbstractGeneralHandler {
    
    private String file;
    private Map<String,Integer> map2 = new HashMap<String, Integer>();
    
    /**
     * DefaultHandler callback. Gatheres each error once per file.
     */
    public void startElement(String name, String string0, String string1, Attributes attributes) throws SAXException {
        //System.out.println("Start "+name+" 2"+string0+" 3"+string1);
        
        if (string1.trim().equals("file")){
            file=attributes.getValue("name");
            map2 = new HashMap<String, Integer>();
        }
        
        if (string1.trim().equals("error")){
            String s = String.valueOf(
                    attributes.getValue("source"));
            
            //System.out.println("S:"+s);
            
            Integer i = map2.get(s);
            if(i==null){
                i=1;
                map2.put(s,i);
            }
            if (def.get(s) == null){
                def.put(s, String.valueOf(attributes.getValue("message")));
            }
        }
    }
    
    /**
     * DefaultHandler callback. Merges the gathered data in the big map.
     */
    public void endElement(String name, String string0, String string1) throws SAXException {
        //System.out.println("end "+name+" 2"+string0+" 3"+string1);
        
        if (string1.trim().equals("file")){
            for(String s : map2.keySet()){
                Integer i = map.get(s);
                if(i==null){
                    i=1;
                }else{
                    i=i+1;
                }
                //System.out.println("End file"+s+" "+i);
                map.put(s,i);
            }
        }
        
    }

    public String getDisplayName() {
        return "Per file";
    }
    
    
    
}